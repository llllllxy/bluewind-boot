package com.bluewind.boot.module.system.index.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.encrypt.SHA256Utils;
import com.bluewind.boot.common.utils.storage.api.StorageService;
import com.bluewind.boot.module.system.index.service.IndexService;
import com.bluewind.boot.module.system.userinfo.entity.UserInfo;
import com.bluewind.boot.module.system.userinfo.service.UserInfoService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-03-18-16:22
 * @description 系统index控制器
 **/
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StorageService storageService;

    /**
     * 盐
     */
    @Value("${hash.salt}")
    private String salt;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * index页面
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        String account = getSysUserAccount();
        UserInfo userInfo = userInfoService.getOne(account);
        userInfo.setAvatarPreUrl(storageService.getExpiryUrlById(userInfo.getAvatar(), 24));
        model.addAttribute("userInfo", userInfo);
        return "system/index/index";
    }

    /**
     * 初始化菜单
     *
     * @return
     */
    @RequestMapping(value = "/menuInit", method = RequestMethod.GET)
    @ResponseBody
    public Object menuInit() {
        // 这里使用的是用户的token做的缓存，所以修改菜单后，需要用户重新登录
        String userKey = SecurityUtil.getUserKey();

        // 先从redis里面拿出菜单信息，拿不到的话，再去手动查询
        Object object = redisUtil.get(SystemConst.SYSTEM_USER_MENU + ":" + userKey);
        if (object != null) {
            logger.info("IndexController - menuInit - 从redis获取菜单信息成功！");
            return object;
        } else {
            Map<String, Object> map = indexService.menuInit();
            // 将用户菜单信息缓存到redis中
            redisUtil.set(SystemConst.SYSTEM_USER_MENU + ":" + userKey, map, 1800);
            return map;
        }
    }


    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/outLogin",method = RequestMethod.GET)
    public String outLogin() {
        logger.info("IndexController - outLogin - start");
        String token = getUserKey();
        // 删除用户的会话信息，即强制退出登录
        redisUtil.del(SystemConst.SYSTEM_USER_KEY + ":" + token);
        //回到登陆页面
        return "redirect:" + contextPath + "admin/login";
    }


    /**
     * 修改密码页面
     *
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public String updatePassword() {
        return "system/index/user_password";
    }


    /**
     * 修改密码操作
     * @param old_password
     * @param new_password
     * @return
     */
    @RequestMapping(value="/doUpdatePassword",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doUpdatePassword(@RequestParam("old_password") String old_password,
                                       @RequestParam("new_password") String new_password) {
        logger.info("IndexController - doUpdatePassword - start - old_password = " + old_password);
        logger.info("IndexController - doUpdatePassword - start - new_password = " + new_password);
        String userId = getSysUserId();
        Map resultMap = indexService.findAccountByUserId(userId);
        // 获取数据库库里的加密后的密码
        String md5Password = (String) resultMap.get("password");
        // 再进行加密两次，才是正确密码
        old_password = SHA256Utils.SHA256Encode(salt + old_password);

        // 旧密码验证成功
        if (md5Password.equals(old_password)) {
            new_password = SHA256Utils.SHA256Encode(salt + new_password);
            Map paraMap = new HashMap();
            paraMap.put("userId", userId);
            paraMap.put("passwordnew", new_password);
            try {
                int i = indexService.doUpdatePassword(paraMap);
            } catch (Exception e) {
                return BaseResult.failure(e.getMessage());
            }
            return BaseResult.success();
        } else {
            return BaseResult.failure("原密码验证失败");
        }
    }


    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult unlock(@RequestParam("account") String account,
                             @RequestParam("password") String password) {
        password = SHA256Utils.SHA256Encode(salt + password);
        UserInfo userInfo = userInfoService.getOne(account);
        if (password.equals(userInfo.getPassword())) {
            return BaseResult.success();
        } else {
            return BaseResult.failure("密码错误！");
        }
    }


    /**
     * user_info页面
     *
     * @return
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model) {
        String account = getSysUserAccount();
        UserInfo userInfo = userInfoService.getOne(account);
        userInfo.setAvatarPreUrl(storageService.getExpiryUrlById(userInfo.getAvatar(), 24));

        model.addAttribute("userInfo", userInfo);
        return "system/index/user_info";
    }


    /**
     * welcome页面
     *
     * @return
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(Model model) {
        return "system/index/welcome_1";
    }

}
