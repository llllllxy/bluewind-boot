package com.bluewind.boot.module.system.roleinfo.service;

import com.bluewind.boot.module.system.roleinfo.entity.RoleInfo;
import com.bluewind.boot.module.system.roleinfo.entity.XmSelect;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.module.system.roleinfo.mapper.RoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:30
 **/
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    RoleInfoMapper roleInfoMapper;


    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    @Override
    public String listXmSelectPojo(String userId) {
        List<XmSelect> list = roleInfoMapper.listXmSelectPojo(userId);
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("data", list);
        return JsonTool.toJsonString(selectMap);
    }

    /**
     * 获取角色列表
     * @param map
     * @return
     */
    @Override
    public List<RoleInfo> getSysRoleInfoList(Map map) {
        return roleInfoMapper.getSysRoleInfoList(map);
    }


    /**
     * 角色新增
     * @param roleInfo
     * @return
     */
    @Override
    public int doAdd(RoleInfo roleInfo) {
        return roleInfoMapper.doAdd(roleInfo);
    }


    /**
     * 删除一个系统角色（这里用逻辑删除）
     * @param roleId
     * @return
     */
    @Override
    public int delete(String roleId) {
        return roleInfoMapper.delete(roleId);
    }



    /**
     * 禁用一个系统角色
     * @param roleId
     * @return
     */
    @Override
    public int forbid(String roleId) {
        return roleInfoMapper.forbid(roleId);
    }


    /**
     * 启用一个系统角色
     * @param roleId
     * @return
     */
    @Override
    public int enable(String roleId) {
        return roleInfoMapper.enable(roleId);
    }


    /**
     * 通过角色id获取一个角色
     * @param roleId
     * @return
     */
    @Override
    public RoleInfo getOneRoleById(String roleId) {
        return roleInfoMapper.getOneRoleById(roleId);
    }


    /**
     * 角色更新
     * @param roleInfo
     * @return
     */
    @Override
    public int doUpdate(RoleInfo roleInfo) {
        return roleInfoMapper.doUpdate(roleInfo);
    }
}
