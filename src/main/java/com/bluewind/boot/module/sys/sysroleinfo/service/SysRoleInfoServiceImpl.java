package com.bluewind.boot.module.sys.sysroleinfo.service;

import com.bluewind.boot.module.sys.sysroleinfo.entity.SysRoleInfo;
import com.bluewind.boot.module.sys.sysroleinfo.entity.XmSelect;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.module.sys.sysroleinfo.mapper.SysRoleInfoMapper;
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
public class SysRoleInfoServiceImpl implements SysRoleInfoService {

    @Autowired
    SysRoleInfoMapper sysRoleInfoMapper;


    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    @Override
    public String listXmSelectPojo(String userId) {
        List<XmSelect> list = sysRoleInfoMapper.listXmSelectPojo(userId);
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
    public List<SysRoleInfo> getSysRoleInfoList(Map map) {
        return sysRoleInfoMapper.getSysRoleInfoList(map);
    }


    /**
     * 角色新增
     * @param sysRoleInfo
     * @return
     */
    @Override
    public int doAdd(SysRoleInfo sysRoleInfo) {
        return sysRoleInfoMapper.doAdd(sysRoleInfo);
    }


    /**
     * 删除一个系统角色（这里用逻辑删除）
     * @param roleId
     * @return
     */
    @Override
    public int delete(String roleId) {
        return sysRoleInfoMapper.delete(roleId);
    }



    /**
     * 禁用一个系统角色
     * @param roleId
     * @return
     */
    @Override
    public int forbid(String roleId) {
        return sysRoleInfoMapper.forbid(roleId);
    }


    /**
     * 启用一个系统角色
     * @param roleId
     * @return
     */
    @Override
    public int enable(String roleId) {
        return sysRoleInfoMapper.enable(roleId);
    }


    /**
     * 通过角色id获取一个角色
     * @param roleId
     * @return
     */
    @Override
    public SysRoleInfo getOneRoleById(String roleId) {
        return sysRoleInfoMapper.getOneRoleById(roleId);
    }


    /**
     * 角色更新
     * @param sysRoleInfo
     * @return
     */
    @Override
    public int doUpdate(SysRoleInfo sysRoleInfo) {
        return sysRoleInfoMapper.doUpdate(sysRoleInfo);
    }
}
