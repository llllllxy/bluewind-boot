package com.bluewind.boot.configuration.security;

import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.common.utils.spring.SpringUtil;
import com.bluewind.boot.common.utils.web.CookieUtils;
import com.bluewind.boot.common.utils.web.ServletUtils;
import com.bluewind.boot.sys.sysuserinfo.entity.SysUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuxingyu01
 * @date 2021-08-07-16:22
 * @description 用户会话工具类
 **/
public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    /**
     * 禁止UserTokenUtil实例化
     */
    private SecurityUtil() {
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
     *
     * @return
     */
    public static String getUserKey() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = "";
        if (request != null) {
            logger.info("SecurityUtil -- getToken -- start");
            // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
            token = request.getHeader(SystemConst.SYSTEM_USER_TOKEN);
            if (StringUtils.isBlank(token)) {
                token = CookieUtils.getCookie(request, SystemConst.SYSTEM_USER_TOKEN);
            }
            if (StringUtils.isNotBlank(token) && token.startsWith(SystemConst.TOKEN_PREFIX)) {
                token = token.replace(SystemConst.TOKEN_PREFIX, "");
                token = JwtTokenUtil.parseJWT(token);
            }
            logger.info("SecurityUtil -- getToken -- userKey = {}", token);
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
            String userKey = getUserKey();
            return (SysUserInfo) getRedisUtil().get(SystemConst.SYSTEM_USER_KEY + ":" + userKey);
        } catch (Exception e) {
            logger.error("SecurityUtil -- getSysUserInfo:{e}", e);
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
            String userKey = getUserKey();
            SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(SystemConst.SYSTEM_USER_KEY + ":" + userKey);
            if (null != userInfo) {
                return userInfo.getId();
            }
        } catch (Exception e) {
            logger.error("SecurityUtil -- getSysUserId:{e}", e);
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
            String userKey = getUserKey();
            SysUserInfo userInfo = (SysUserInfo) getRedisUtil().get(SystemConst.SYSTEM_USER_KEY + ":" + userKey);
            if (null != userInfo) {
                return userInfo.getAccount();
            }
        } catch (Exception e) {
            logger.error("SecurityUtil -- getSysUserAccount:{e}", e);
        }
        return null;
    }

}
