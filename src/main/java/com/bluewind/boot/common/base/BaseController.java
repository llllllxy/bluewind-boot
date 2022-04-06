package com.bluewind.boot.common.base;

import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.module.system.userinfo.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuxingyu01
 * @date 2021-01-14-13:29
 **/
public abstract class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    public static final String RESULT_ROWS = "rows";
    public static final String RESULT_TOTLAL = "total";

    public BaseController() {
    }


    /**
     * 获取登录用户
     *
     * @return
     */
    public UserInfo getSysUserInfo() {
        return SecurityUtil.getSysUserInfo();
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public String getSysUserId() {
        return SecurityUtil.getSysUserId();
    }

    /**
     * 获取登录用户账户account
     *
     * @return
     */
    public String getSysUserAccount() {
        return SecurityUtil.getSysUserAccount();
    }

    /**
     * 获取用户token
     * @return
     */
    public String getUserKey() {
        return SecurityUtil.getUserKey();
    }

}
