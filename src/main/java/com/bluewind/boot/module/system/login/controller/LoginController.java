package com.bluewind.boot.module.system.login.controller;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.annotation.RequestLimit;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.config.kaptcha.KaptchaUtil;
import com.bluewind.boot.common.config.security.JwtTokenUtil;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.encrypt.SHA256Utils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.common.utils.mail.EmailUtils;
import com.bluewind.boot.common.utils.web.CookieUtils;
import com.bluewind.boot.module.system.login.service.LoginService;
import com.bluewind.boot.module.system.loginlog.service.LoginLogService;
import com.bluewind.boot.module.system.mail.entity.EmailLogVO;
import com.bluewind.boot.module.system.maillog.service.EmailLogService;
import com.bluewind.boot.module.system.userinfo.entity.UserInfo;
import com.bluewind.boot.module.system.userinfo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-03-18-13:45
 * @description 系统登录控制器
 **/
@Controller
@RequestMapping("/admin")
@Api(value = "系统登录控制器", description = "登陆管理")
public class LoginController {
    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private EmailLogService emailLogService;

    /**
     * 加密盐值
     */
    @Value("${hash.salt}")
    private String salt;

    /**
     * 会话失效时间(单位秒)
     */
    @Value("${bluewind.session-timeout}")
    private int sessionTimeout;


    @LogAround(value = "跳转到登陆页面")
    @ApiOperation(value = "跳转到登陆页面")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        logger.debug("LoginController -- login -- start");
        model.addAttribute("kaptcha_key", IdGenerate.uuid());
        return "system/login/login_v3";
    }


    @LogAround(value = "执行登陆操作")
    @ApiOperation(value = "登录验证", notes = "登录验证")
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("captchaVerification") String captchaVerification,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        // 二次验证滑动验证码-集成AJ-Captcha
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        if (!captchaService.verification(captchaVO).isSuccess()) {
            return BaseResult.failure("行为验证失败，请重试!");
        }

        // 根据用户名查找到用户信息
        UserInfo userInfo = userInfoService.getOne(username);
        // 没找到帐号(用户不存在)
        if (userInfo == null) {
            loginLogService.saveLoginlog(request, username, "1", "账户不存在！", "");
            return BaseResult.failure("账户不存在！");
        }
        logger.info("LoginController - doLogin - userInfo = " + userInfo.toString());

        // 校验用户状态(用户已失效)
        if ("1".equals(userInfo.getStatus())) {
            loginLogService.saveLoginlog(request, username, "1", "该账户已被冻结！", "");
            return BaseResult.failure("该账户已被冻结！");
        }

        // 密码错误五次，则锁定账号1800s
        int errorTimes = redisUtil.get(SystemConst.SYSTEM_LOGIN_TIMES + ":" + userInfo.getAccount()) == null ? 0
                : Integer.parseInt((String) redisUtil.get(SystemConst.SYSTEM_LOGIN_TIMES + ":" + userInfo.getAccount()));
        if (errorTimes >= 5) {
            loginLogService.saveLoginlog(request, username, "1", "密码连续输入错误超过5次，账号将被锁定半小时！", "");
            redisUtil.expire(SystemConst.SYSTEM_LOGIN_TIMES + ":" + username, 1800);
            return BaseResult.failure("密码连续输入错误超过5次，账号将被锁定半小时！");
        }

        String localPassword = userInfo.getPassword();
        password = SHA256Utils.SHA256Encode(salt + password);

        if (localPassword.equals(password)) {
            logger.info("LoginController - doLogin - {}登陆成功！", username);
            Map<String, Object> resultMap = new HashMap<>();
            String redisKey = IdGenerate.uuid();
            logger.info("LoginController - doLogin - redisKey = {}", redisKey);
            // 存储用户会话信息到redis
            redisUtil.set(SystemConst.SYSTEM_USER_KEY + ":" + redisKey, userInfo, sessionTimeout);

            String token = SystemConst.TOKEN_PREFIX + JwtTokenUtil.createJWT(redisKey);
            resultMap.put(SystemConst.SYSTEM_USER_TOKEN, token);
            // 将token放在cookie中
            CookieUtils.setCookie(response, SystemConst.SYSTEM_USER_TOKEN, token);
            // 保存登录日志
            loginLogService.saveLoginlog(request, username, "0", "用户登录成功！", redisKey);

            return BaseResult.success("登录成功，欢迎回来！", resultMap);
        } else {
            loginLogService.saveLoginlog(request, username, "1", "密码错误，请重新输入！", "");
            recordLoginTimes(username);
            return BaseResult.failure("密码错误，请重新输入！");
        }
    }

    /**
     * 当密码错误时，做记录
     *
     * @param username
     */
    private void recordLoginTimes(String username) {
        // 获取当前用户的密码错误次数
        int errorTimes = redisUtil.get(SystemConst.SYSTEM_LOGIN_TIMES + ":" + username) == null ? 0
                : Integer.parseInt((String) redisUtil.get(SystemConst.SYSTEM_LOGIN_TIMES + ":" + username));
        redisUtil.set(SystemConst.SYSTEM_LOGIN_TIMES + ":" + username, String.valueOf(errorTimes + 1), 1800);
    }


    @LogAround(value = "跳转到注册页面")
    @ApiOperation(value = "跳转到注册页面", notes="跳转到注册页面")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        logger.info("LoginController -- register -- start");
        model.addAttribute("kaptcha_key", IdGenerate.uuid());
        return "system/login/register";
    }


    @RequestLimit(time = 60, count = 5, waits = 300)
    @LogAround(value = "获取邮箱验证码")
    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult sendEmail(@RequestParam("kaptchaCode") String kaptchaCode,
                                @RequestParam("kaptchaKey") String kaptchaKey,
                                @RequestParam("email") String email) {
        // 先验证图形验证码，图形验证码错了的话直接返回
        if (!KaptchaUtil.validate(kaptchaCode, kaptchaKey)) {
            return BaseResult.failure("图形验证码输入错误或已过期，请重新输入!");
        }

        String email_key = IdGenerate.uuid();
        String emailCode = StringUtils.getRandomStr(6);

        EmailLogVO sysEmailLogVO = new EmailLogVO();
        sysEmailLogVO.setAddress(email);
        sysEmailLogVO.setSubject("注册邮件，请查收验证码");
        sysEmailLogVO.setContent("您的验证码为：" + emailCode + "，有效期为30分钟");

        BaseResult responseResult = emailUtils.sendTextMail(sysEmailLogVO.getAddress().trim(),
                sysEmailLogVO.getSubject().trim(), sysEmailLogVO.getContent().trim());

        sysEmailLogVO.setType("0");
        sysEmailLogVO.setStatus(0 == responseResult.getCode() ? "0" : "1");
        // 保存邮件日志
        emailLogService.saveSysEmailLog(sysEmailLogVO);
        // 如果是0，说明邮件发送成功了
        if (0 == responseResult.getCode()) {
            // 邮件验证码存进redis
            redisUtil.set( SystemConst.SYSTEM_REGISTER_EMAILCODE + ":" + email_key, emailCode, 1800);

            Map<String, String> result = new HashMap<>();
            result.put("email_key", email_key);
            return BaseResult.success("邮箱验证码发送成功", result);
        } else {
            return BaseResult.failure("邮箱验证码发送失败，请联系系统管理页");
        }
    }


    @RequestLimit(time = 60, count = 1, waits = 300)
    @LogAround(value = "注册")
    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doRegister(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email,
                                 @RequestParam("emailCode") String emailCode,
                                 @RequestParam("emailKey") String emailKey,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 先验证邮箱验证码，邮箱验证码错了的话直接返回
        if (!validateEmailCode(emailCode, emailKey)) {
            return BaseResult.failure("邮箱验证码输入错误或已过期，请重新输入!");
        }
        if (userInfoService.checkUserNameUnique(username) > 0) {
            return BaseResult.failure("注册'" + username + "'失败，登录账号已存在!");
        }

        UserInfo userInfo = new UserInfo();
        String userId = IdGenerate.nextId();
        userInfo.setUserId(userId);
        userInfo.setAccount(username);
        userInfo.setPassword(SHA256Utils.SHA256Encode(salt + password));
        userInfo.setEmail(email);
        userInfo.setDelFlag("0");
        userInfo.setStatus("0");
        userInfo.setCreateUser(userId);

        int num = userInfoService.doAdd(userInfo);

        if (num > 0) {
            logger.info("LoginController - doRegister - {} 注册成功！", username);
            Map<String, Object> resultMap = new HashMap<>();
            String redisKey = IdGenerate.uuid();
            logger.info("LoginController - doRegister - redisKey = {}", redisKey);
            // 存储用户会话信息到redis
            redisUtil.set(SystemConst.SYSTEM_USER_KEY + ":" + redisKey, userInfo, sessionTimeout);

            String token = SystemConst.TOKEN_PREFIX + JwtTokenUtil.createJWT(redisKey);
            resultMap.put(SystemConst.SYSTEM_USER_TOKEN, token);
            // 将token放在cookie中
            CookieUtils.setCookie(response, SystemConst.SYSTEM_USER_TOKEN, token);
            // 保存登录日志
            loginLogService.saveLoginlog(request, username, "0", "用户注册登录成功！", redisKey);

            return BaseResult.success("注册成功，即将跳往首页！", resultMap);
        } else {
            return BaseResult.failure("注册失败，请联系系统管理页");
        }
    }


    /**
     * 邮箱注册验证码校验
     *
     * @param
     * @return 正确:true/错误:false
     */
    private boolean validateEmailCode(String emailCode, String emailKey) {
        if (StringUtils.isEmpty(emailCode)) {
            return false;
        }
        if (StringUtils.isEmpty(emailKey)) {
            return false;
        }
        try {
            String code = redisUtil.get(SystemConst.SYSTEM_REGISTER_EMAILCODE + ":" + emailKey).toString();
            boolean result = emailCode.equalsIgnoreCase(code);
            if (result) {
                redisUtil.del(SystemConst.SYSTEM_REGISTER_EMAILCODE + ":" + emailKey);
            }
            return result;
        } catch (Exception e) {  // redis里找不到这个key
            logger.error("LoginController -- validateEmailCode Exception = {e}", e);
            return false;
        }
    }

}
