package com.bluewind.boot.sys.syspermissioninfo.mapper;

import com.bluewind.boot.sys.syspermissioninfo.entity.LayuiTree;
import com.bluewind.boot.sys.syspermissioninfo.entity.SysPermissionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-02-05-22:27
 **/
@Repository
public interface SysPermissionInfoMapper {

    /**
     * 角色赋权，根据角色id获取权限树tree组件
     * @param roleId 角色id
     * @return
     */
    List<LayuiTree> listPermissionForTree(String roleId);


    /**
     * 查询菜单列表
     * @return
     */
    List<SysPermissionInfo> list();

    /**
     * 获取菜单列表(转化为LayuiTree对象)
     * @return
     */
    List<LayuiTree> listTree();

    /**
     * 禁用一个菜单
     * @param list
     * @return
     */
    int forbid(List<String> list);


    /**
     * 启用一个菜单
     * @param list
     * @return
     */
    int enable(List<String> list);


    /**
     * 删除一个菜单
     * @param list
     * @return
     */
    int delete(List<String> list);


    /**
     * 根据权限类型，获取权限列表
     * @return
     */
    List<LayuiTree> listPermissionByType1();

    /**
     * 根据权限类型，获取权限列表
     * @return
     */
    List<LayuiTree> listPermissionByType2();


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
    int updatePermission(SysPermissionInfo sysPermissionInfo);

}
