package com.bluewind.boot.module.system.rolepermission.service;

import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:49
 **/
public interface RolePermissionService {

    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    Set<String> listRolePermissionByUserId(String userId);


    /**
     * 角色权限更新
     *
     * @return
     */
    int doAuthorize(String roleId, String permIds);
}