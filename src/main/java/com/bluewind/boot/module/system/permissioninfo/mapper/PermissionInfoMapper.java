package com.bluewind.boot.module.system.permissioninfo.mapper;

import com.bluewind.boot.module.system.permissioninfo.entity.PermissionInfo;
import com.bluewind.boot.module.system.rolepermission.vo.LayuiTree;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-05-22:27
 **/
@Repository
public interface PermissionInfoMapper {

    /**
     * 查询菜单列表
     * @return
     */
    List<PermissionInfo> list();

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
    List<Map<String, Object>> listPermission();



    /**
     * 修改菜单顺序
     * @param permissionInfo
     * @return
     */
    int updateSort(PermissionInfo permissionInfo);


    /**
     * 新增菜单
     * @param permissionInfo
     * @return
     */
    int addPermission(PermissionInfo permissionInfo);


    /**
     * 根据permissionId获取一个
     * @param permissionId
     * @return
     */
    PermissionInfo getOnePermission(String permissionId);


    /**
     * 修改菜单
     * @param
     * @return
     */
    int updatePermission(PermissionInfo permissionInfo);

}
