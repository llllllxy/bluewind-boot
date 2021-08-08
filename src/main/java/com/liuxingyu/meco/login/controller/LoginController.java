package com.liuxingyu.meco.login.controller;

import com.liuxingyu.meco.common.annotation.LogAround;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.encrypt.SHA256Utils;
import com.liuxingyu.meco.common.utils.idgen.IdGenerate;
import com.liuxingyu.meco.common.utils.web.CookieUtils;
import com.liuxingyu.meco.configuration.kaptcha.KaptchaUtil;
import com.liuxingyu.meco.common.consts.SystemConst;
import com.liuxingyu.meco.login.service.LoginService;
import com.liuxingyu.meco.sys.sysloginlog.service.SysLoginLogService;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.sys.sysuserinfo.entity.SysUserInfo;
import com.liuxingyu.meco.sys.sysuserinfo.service.SysUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2020-03-18-13:45
 * @description 系统登录控制器
 **/
@Controller
@RequestMapping("/admin")
@Api(value = "LoginController", description = "登陆管理")
public class LoginController {
    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 盐
     */
    @Value("${hash.salt}")
    private String salt;

    @LogAround(value = "跳转到登陆页面")
    @ApiOperation(value = "跳转到登陆页面")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        logger.info("LoginController -- loginPage -- start");
        model.addAttribute("kaptcha_key", IdGenerate.uuid());
        return "login/login";
    }

    @LogAround(value = "执行登陆操作")
    @ApiOperation(value = "登录验证", notes = "登录验证")
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("captcha") String captcha,
                              @RequestParam("kaptchaKey") String kaptchaKey,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        // 先验证验证码
        if (!KaptchaUtil.validate(captcha, kaptchaKey)) {
            return BaseResult.failure("验证码错误或已过期，请重新输入!");
        }

        // 根据用户名查找到用户信息
        SysUserInfo userInfo = sysUserInfoService.getOne(username);
        // 没找到帐号(用户不存在)
        if (userInfo == null) {
            sysLoginLogService.saveLoginlog(request, username, 1, "账户不存在！");
            return BaseResult.failure("账户不存在！");
        }
        // 校验用户状态(用户已失效)
        // Integer包装类型在和基本数据类型比较时,jvm会自动把包装数据类型拆箱为基本数据类型int,所以额可以直接比较
        if (1 == userInfo.getStatus()) {
            sysLoginLogService.saveLoginlog(request, username, 1, "该账户已被冻结！");
            return BaseResult.failure("该账户已被冻结！");
        }

        // 密码错误五次，则锁定账号1800s
        int errorTimes = redisUtil.get(SystemConst.SYSTEM_LOGIN_TIMES + ":" + userInfo.getAccount()) == null ? 0
                : Integer.parseInt((String) redisUtil.get(SystemConst.SYSTEM_LOGIN_TIMES + ":" + userInfo.getAccount()));
        if (errorTimes >= 5) {
            sysLoginLogService.saveLoginlog(request, username, 1, "密码连续输入错误超过5次，账号将被锁定半小时！");
            redisUtil.expire(SystemConst.SYSTEM_LOGIN_TIMES + ":" + username, 1800);
            return BaseResult.failure("密码连续输入错误超过5次，账号将被锁定半小时！");
        }

        String localPassword = userInfo.getPassword();
        password = SHA256Utils.SHA256Encode(salt + password);

        if (localPassword.equals(password)) {
            logger.info("LoginController - doLogin - {}登陆成功！", username);
            Map<String, Object> resultMap = new HashMap<>();
            String token = IdGenerate.uuid();
            logger.info("LoginController - doLogin - token = {}", token);
            resultMap.put(SystemConst.SYSTEM_USER_COOKIE, token);
            // 将token放在cookie中
            CookieUtils.setCookie(response, SystemConst.SYSTEM_USER_COOKIE, token);
            // 存储用户信息到redis
            redisUtil.set(SystemConst.SYSTEM_USER_TOKEN + ":" + token, userInfo, 1800);
            // 保存登录日志
            sysLoginLogService.saveLoginlog(request, username, 0, "用户登录成功！");

            return BaseResult.success("登录成功，欢迎回来！", resultMap);
        } else {
            sysLoginLogService.saveLoginlog(request, username, 1, "密码错误，请重新输入！");
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

}
