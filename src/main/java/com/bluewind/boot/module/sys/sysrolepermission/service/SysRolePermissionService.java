package com.bluewind.boot.module.sys.sysrolepermission.service;

import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:49
 **/
public interface SysRolePermissionService {

    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    Set<String> listRolePermissionByUserId(Integer userId);


    /**
     * 角色权限更新
     *
     * @return
     */
    int doAuthorize(String roleId, String permIds);
}
