package com.bluewind.boot.module.system.roleinfo.service;

import com.bluewind.boot.module.system.roleinfo.entity.RoleInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:29
 **/
public interface RoleInfoService {

    /**
     * 获取角色列表
     * @param map
     * @return
     */
    List<RoleInfo> getSysRoleInfoList(Map map);


    /**
     * 角色新增
     * @param roleInfo
     * @return
     */
    int doAdd(RoleInfo roleInfo);


    /**
     * 删除一个系统角色（这里用逻辑删除）
     * @param roleId
     * @return
     */
    int delete(String roleId);


    /**
     * 禁用一个系统角色
     * @param roleId
     * @return
     */
    int forbid(String roleId);


    /**
     * 启用一个系统角色
     * @param roleId
     * @return
     */
    int enable(String roleId);


    /**
     * 通过角色id获取一个角色
     * @param roleId
     * @return
     */
    RoleInfo getOneRoleById(String roleId);


    /**
     * 角色更新
     * @param roleInfo
     * @return
     */
    int doUpdate(RoleInfo roleInfo);

}
