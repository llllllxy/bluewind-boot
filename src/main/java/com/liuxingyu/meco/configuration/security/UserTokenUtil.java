package com.liuxingyu.meco.configuration.security;

import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.lang.StringUtils;
import com.liuxingyu.meco.common.utils.spring.SpringUtil;
import com.liuxingyu.meco.common.utils.web.CookieUtils;
import com.liuxingyu.meco.common.utils.web.ServletUtils;
import com.liuxingyu.meco.sys.sysuserinfo.entity.SysUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class UserTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserTokenUtil.class);

    private UserTokenUtil() {
        throw new AssertionError();
    }

    private static RedisUtil redisUtil;

    private static RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            Object bean = SpringUtil.getBean("redisUtil");
            if (bean == null) {
                logger.error("redisUtil bean is null!");
            }
            redisUtil = (RedisUtil) bean;
        }
        return redisUtil;
    }

    public static String getToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = CookieUtils.getCookie(request, "token");
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
           SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(token);
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
            SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(token);
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
            SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(token);
            if (null != userInfo) {
                return userInfo.getAccount();
            }

        } catch (Exception e) {
            logger.error("UserTokenUtil -- getSysUserId:{e}", e);
        }
        return null;
    }

}
