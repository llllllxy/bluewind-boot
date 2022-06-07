package com.bluewind.boot.module.system.rolepermission.service;

import java.util.Map;
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


    /**
     * 角色赋权，根据角色id获取权限树tree组件
     * @param roleId 角色id
     * @return
     */
    Map<String, Object> listPermissionByRoleId(String roleId);
}
