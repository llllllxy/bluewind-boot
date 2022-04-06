package com.bluewind.boot.module.system.userrole.service;

import com.bluewind.boot.common.base.BaseResult;

import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:47
 **/
public interface UserRoleService {
    /**
     * 登录时，根据用户id查询全部角色标识
     */
    Set<String> listUserRoleByUserId(String userId);

    /**
     * 用户授权
     * @param userId
     * @param roles
     * @return
     */
    BaseResult doAuthorize(String userId, String roles);
}
