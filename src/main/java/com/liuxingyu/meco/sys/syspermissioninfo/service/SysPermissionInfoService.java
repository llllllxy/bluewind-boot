package com.liuxingyu.meco.sys.syspermissioninfo.service;

import com.liuxingyu.meco.sys.syspermissioninfo.entity.SysPermissionInfo;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-02-05-22:27
 * @description 菜单权限表
 **/
public interface SysPermissionInfoService {

    /**
     * 角色赋权，根据角色id获取权限树tree组件
     * @param roleId 角色id
     * @return
     */
    String listPermissionForTree(String roleId);

    /**
     * 查询菜单列表
     */
    List<SysPermissionInfo> list();

    /**
     * 禁用一个菜单
     * @param permissionId 权限id
     * @return
     */
    int forbid(String permissionId);

    /**
     * 启用一个菜单
     * @param permissionId 权限id
     * @return
     */
    int enable(String permissionId);


    /**
     * 删除一个菜单
     * @param permissionId 权限id
     * @return
     */
    int delete(String permissionId);

    /**
     * 根据权限类型，获取权限列表
     * @param type
     * @return
     */
    String listPermissionByType(String type);

    /**
     * 修改菜单顺序
     * @param sysPermissionInfo
     * @return
     */
    int updateSort(SysPermissionInfo sysPermissionInfo);

    /**
     * 新增菜单
     * @param sysPermissionInfo
     * @return
     */
    int addPermission(SysPermissionInfo sysPermissionInfo);

    /**
     * 根据permissionId获取一个
     * @param permissionId
     * @return
     */
    SysPermissionInfo getOnePermission(String permissionId);

    /**
     * 修改菜单
     * @param s
     * @return
     */
    int updatePermission(SysPermissionInfo s);
}
