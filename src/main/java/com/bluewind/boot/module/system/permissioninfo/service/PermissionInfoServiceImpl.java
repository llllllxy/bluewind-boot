package com.bluewind.boot.module.system.permissioninfo.service;

import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.module.system.permissioninfo.entity.LayuiTree;
import com.bluewind.boot.module.system.permissioninfo.entity.PermissionInfo;
import com.bluewind.boot.module.system.permissioninfo.mapper.PermissionInfoMapper;
import com.bluewind.boot.module.system.permissioninfo.util.PermissionTreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-05-22:27
 **/
@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {
    final static Logger logger = LoggerFactory.getLogger(PermissionInfoServiceImpl.class);

    @Autowired
    PermissionInfoMapper permissionInfoMapper;

    /**
     * 角色赋权，根据角色id获取权限树tree组件
     * @param roleId
     * @return
     */
    @Override
    public String listPermissionForTree(String roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<LayuiTree> list = permissionInfoMapper.listPermissionForTree(roleId);
        if (null == list || list.isEmpty()) {
            resultMap.put("data", null);
            resultMap.put("code", 0);
            resultMap.put("msg", "");
            return JsonTool.toJsonString(resultMap);
        }

        List<LayuiTree> resultList = PermissionTreeUtil.toTree(list, "0");
        resultMap.put("data", resultList);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        return JsonTool.toJsonString(resultMap);
    }


    /**
     * 查询菜单列表
     */
    @Override
    public List<PermissionInfo> list() {
        return permissionInfoMapper.list();
    }


    /**
     * 禁用一个菜单
     * @param permissionId
     * @return
     */
    @Override
    public int forbid(String permissionId) {
        List<LayuiTree> list = permissionInfoMapper.listTree();
        List<LayuiTree> childList = PermissionTreeUtil.findChildList(list, permissionId);

        List<String> perIdStr = new ArrayList<>();
        perIdStr.add(permissionId);
        if (CollectionUtils.isNotEmpty(childList)) {
            childList.forEach(item->{
                perIdStr.add(item.getPermissionId());
            });
        }
        // 这样就拿到了自己和子节点的permissionId，再去数据库里做禁用就可以了
        if (logger.isInfoEnabled()) {
            logger.info("SysPermissionInfoServiceImpl - forbid - perIdStr={}", perIdStr);
        }
        int num = permissionInfoMapper.forbid(perIdStr);
        return num;
    }


    /**
     * 启用一个菜单
     * @param permissionId
     * @return
     */
    @Override
    public int enable(String permissionId) {
        List<LayuiTree> list = permissionInfoMapper.listTree();
        List<LayuiTree> childList = PermissionTreeUtil.findChildList(list, permissionId);

        List<String> perIdStr = new ArrayList<>();
        perIdStr.add(permissionId);
        if (CollectionUtils.isNotEmpty(childList)) {
            childList.forEach(item->{
                perIdStr.add(item.getPermissionId());
            });
        }
        // 这样就拿到了自己和子节点的permissionId，再去数据库里做禁用就可以了
        if (logger.isInfoEnabled()) {
            logger.info("SysPermissionInfoServiceImpl - enable - perIdStr={}", perIdStr);
        }
        int num = permissionInfoMapper.enable(perIdStr);
        return num;
    }


    /**
     * 启用一个菜单
     * @param permissionId
     * @return
     */
    @Override
    public int delete(String permissionId) {
        List<LayuiTree> list = permissionInfoMapper.listTree();
        List<LayuiTree> childList = PermissionTreeUtil.findChildList(list, permissionId);

        List<String> perIdStr = new ArrayList<>();
        perIdStr.add(permissionId);
        if (CollectionUtils.isNotEmpty(childList)) {
            childList.forEach(item->{
                perIdStr.add(item.getPermissionId());
            });
        }
        // 这样就拿到了自己和子节点的permissionId，再去数据库里做禁用就可以了
        if (logger.isInfoEnabled()) {
            logger.info("SysPermissionInfoServiceImpl - delete - perIdStr={}", perIdStr);
        }
        int num = permissionInfoMapper.delete(perIdStr);
        return num;
    }


    /**
     * 获取权限列表
     * @param type
     * @return
     */
    @Override
    public List<Map<String, Object>> listPermission() {
        List<Map<String, Object>> list = permissionInfoMapper.listPermission();
        return list;
    }



    /**
     * 修改菜单顺序
     * @param permissionInfo
     * @return
     */
    @Override
    public int updateSort(PermissionInfo permissionInfo) {
        return permissionInfoMapper.updateSort(permissionInfo);
    }


    /**
     * 新增菜单
     * @param permissionInfo
     * @return
     */
    @Override
    public int addPermission(PermissionInfo permissionInfo) {
        return permissionInfoMapper.addPermission(permissionInfo);
    }


    /**
     * 根据permissionId获取一个
     * @param permissionId
     * @return
     */
    @Override
    public PermissionInfo getOnePermission(String permissionId) {
        return permissionInfoMapper.getOnePermission(permissionId);
    }


    /**
     * 修改菜单
     * @param s
     * @return
     */
    @Override
    public int updatePermission(PermissionInfo s) {
        return permissionInfoMapper.updatePermission(s);
    }

}


