package com.liuxingyu.meco.sys.sysroleinfo.service;

import com.liuxingyu.meco.common.utils.JsonTool;
import com.liuxingyu.meco.sys.sysroleinfo.entity.SysRoleInfo;
import com.liuxingyu.meco.sys.sysroleinfo.entity.XmSelect;
import com.liuxingyu.meco.sys.sysroleinfo.mapper.SysRoleInfoMapper;
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
    public String listXmSelectPojo(Integer userId) {
        List<XmSelect> list = sysRoleInfoMapper.listXmSelectPojo(userId);
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("data", list);
        return JsonTool.mapToJsonString(selectMap);
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
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {
        return sysRoleInfoMapper.delete(id);
    }



    /**
     * 禁用一个系统角色
     * @param id
     * @return
     */
    @Override
    public int forbid(int id) {
        return sysRoleInfoMapper.forbid(id);
    }


    /**
     * 启用一个系统角色
     * @param id
     * @return
     */
    @Override
    public int enable(int id) {
        return sysRoleInfoMapper.enable(id);
    }


    /**
     * 通过角色id获取一个角色
     * @param id
     * @return
     */
    @Override
    public SysRoleInfo getOneRoleById(int id) {
        return sysRoleInfoMapper.getOneRoleById(id);
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
