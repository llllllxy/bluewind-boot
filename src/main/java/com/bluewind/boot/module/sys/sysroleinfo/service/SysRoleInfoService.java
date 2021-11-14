package com.bluewind.boot.module.sys.sysroleinfo.service;

import com.bluewind.boot.module.sys.sysroleinfo.entity.SysRoleInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:29
 **/
public interface SysRoleInfoService {

    /**
     * 根据用户id查询用户所含角色，给xmselect赋值
     */
    String listXmSelectPojo(Integer userId);

    /**
     * 获取角色列表
     * @param map
     * @return
     */
    List<SysRoleInfo> getSysRoleInfoList(Map map);


    /**
     * 角色新增
     * @param sysRoleInfo
     * @return
     */
    int doAdd(SysRoleInfo sysRoleInfo);


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
    SysRoleInfo getOneRoleById(String roleId);


    /**
     * 角色更新
     * @param sysRoleInfo
     * @return
     */
    int doUpdate(SysRoleInfo sysRoleInfo);

}
