package com.liuxingyu.meco.configuration.security;

import com.liuxingyu.meco.common.consts.SystemConst;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.lang.StringUtils;
import com.liuxingyu.meco.common.utils.spring.SpringUtil;
import com.liuxingyu.meco.common.utils.web.CookieUtils;
import com.liuxingyu.meco.common.utils.web.ServletUtils;
import com.liuxingyu.meco.sys.sysuserinfo.entity.SysUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuxingyu01
 * @date 2021-08-07-16:22
 * @description 用户token会话工具类你
 **/
public class UserTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserTokenUtil.class);

    /**
     * 禁止UserTokenUtil实例化
     */
    private UserTokenUtil() {
        throw new AssertionError();
    }

    private static RedisUtil redisUtil;
    private static RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            redisUtil = SpringUtil.getBean("redisUtil");
        }
        return redisUtil;
    }

    /**
     * 获取用户token
     * @return
     */
    public static String getToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = "";
        if (request != null) {
            logger.info("UserTokenUtil -- getToken -- start");
            token = request.getHeader(SystemConst.SYSTEM_USER_COOKIE);
            if (StringUtils.isBlank(token)) {
                token = CookieUtils.getCookie(request, SystemConst.SYSTEM_USER_COOKIE);
            }
            logger.info("getToken -- start -- token = {}", token);
        }
        return token;
    }


    /**
     * 获取登录用户
     *
     * @return
     */
    public static SysUserInfo getSysUserInfo() {
        try {
           String token = getToken();
           SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(SystemConst.SYSTEM_USER_TOKEN + ":" + token);
           return userInfo;
        } catch (Exception e) {
            logger.error("UserTokenUtil -- getSysUserInfo:{e}", e);
        }
        return null;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static Integer getSysUserId() {
        try {
            String token = getToken();
            SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(SystemConst.SYSTEM_USER_TOKEN + ":" + token);
            if (null != userInfo) {
                return userInfo.getId();
            }
        } catch (Exception e) {
            logger.error("UserTokenUtil -- getSysUserId:{e}", e);
        }
        return null;
    }

    /**
     * 获取登录用户账户account
     *
     * @return
     */
    public static String getSysUserAccount() {
        try {
            String token = getToken();
            SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(SystemConst.SYSTEM_USER_TOKEN + ":" + token);
            if (null != userInfo) {
                return userInfo.getAccount();
            }
        } catch (Exception e) {
            logger.error("UserTokenUtil -- getSysUserId:{e}", e);
        }
        return null;
    }

}
