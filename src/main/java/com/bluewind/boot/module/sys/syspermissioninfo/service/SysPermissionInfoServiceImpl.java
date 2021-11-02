package com.bluewind.boot.module.sys.syspermissioninfo.service;

import com.bluewind.boot.module.sys.syspermissioninfo.entity.LayuiTree;
import com.bluewind.boot.module.sys.syspermissioninfo.entity.SysPermissionInfo;
import com.bluewind.boot.module.sys.syspermissioninfo.mapper.SysPermissionInfoMapper;
import com.bluewind.boot.module.sys.syspermissioninfo.util.TreeUtil;
import com.bluewind.boot.common.utils.JsonTool;
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
public class SysPermissionInfoServiceImpl implements SysPermissionInfoService {
    final static Logger logger = LoggerFactory.getLogger(SysPermissionInfoServiceImpl.class);

    @Autowired
    SysPermissionInfoMapper sysPermissionInfoMapper;

    /**
     * 角色赋权，根据角色id获取权限树tree组件
     * @param roleId
     * @return
     */
    @Override
    public String listPermissionForTree(String roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<LayuiTree> list = sysPermissionInfoMapper.listPermissionForTree(roleId);
        if (null == list || list.isEmpty()) {
            resultMap.put("data", null);
            resultMap.put("code", 0);
            resultMap.put("msg", "");
            return JsonTool.mapToJsonString(resultMap);
        }

        List<LayuiTree> resultList = TreeUtil.toTree(list, "0");
        resultMap.put("data", resultList);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        return JsonTool.mapToJsonString(resultMap);
    }


    /**
     * 查询菜单列表
     */
    @Override
    public List<SysPermissionInfo> list() {
        return sysPermissionInfoMapper.list();
    }


    /**
     * 禁用一个菜单
     * @param permissionId
     * @return
     */
    @Override
    public int forbid(String permissionId) {
        List<LayuiTree> list = sysPermissionInfoMapper.listTree();
        List<LayuiTree> childList = TreeUtil.findChildMenu(list, permissionId);

        List<String> perIdStr = new ArrayList<>();
        perIdStr.add(permissionId);
        if (CollectionUtils.isNotEmpty(childList)) {
            childList.forEach(item->{
                perIdStr.add(item.getPermissionId());
            });
        }
        // 这样就拿到了自己和子节点的permissionId，再去数据库里做禁用就可以了
        if (logger.isInfoEnabled()) {
            logger.info("SysPermissionInfoServiceImpl - forbid - perIdStr={}", JsonTool.listToJsonString(perIdStr));
        }
        int num = sysPermissionInfoMapper.forbid(perIdStr);
        return num;
    }


    /**
     * 启用一个菜单
     * @param permissionId
     * @return
     */
    @Override
    public int enable(String permissionId) {
        List<LayuiTree> list = sysPermissionInfoMapper.listTree();
        List<LayuiTree> childList = TreeUtil.findChildMenu(list, permissionId);

        List<String> perIdStr = new ArrayList<>();
        perIdStr.add(permissionId);
        if (CollectionUtils.isNotEmpty(childList)) {
            childList.forEach(item->{
                perIdStr.add(item.getPermissionId());
            });
        }
        // 这样就拿到了自己和子节点的permissionId，再去数据库里做禁用就可以了
        if (logger.isInfoEnabled()) {
            logger.info("SysPermissionInfoServiceImpl - enable - perIdStr={}", JsonTool.listToJsonString(perIdStr));
        }
        int num = sysPermissionInfoMapper.enable(perIdStr);
        return num;
    }


    /**
     * 启用一个菜单
     * @param permissionId
     * @return
     */
    @Override
    public int delete(String permissionId) {
        List<LayuiTree> list = sysPermissionInfoMapper.listTree();
        List<LayuiTree> childList = TreeUtil.findChildMenu(list, permissionId);

        List<String> perIdStr = new ArrayList<>();
        perIdStr.add(permissionId);
        if (CollectionUtils.isNotEmpty(childList)) {
            childList.forEach(item->{
                perIdStr.add(item.getPermissionId());
            });
        }
        // 这样就拿到了自己和子节点的permissionId，再去数据库里做禁用就可以了
        if (logger.isInfoEnabled()) {
            logger.info("SysPermissionInfoServiceImpl - delete - perIdStr={}", JsonTool.listToJsonString(perIdStr));
        }
        int num = sysPermissionInfoMapper.delete(perIdStr);
        return num;
    }


    /**
     * 根据权限类型，获取权限列表
     * @param type
     * @return
     */
    @Override
    public String listPermissionByType(String type) {
        Map<String, Object> resultMap = new HashMap<>();
        List<LayuiTree> list = null;
        if ("1".equals(type)) {
            list = sysPermissionInfoMapper.listPermissionByType1();
            list.forEach(item -> {
                if ("2".equals(item.getType())  || "3".equals(item.getType())) {
                    item.setDisabled(true);
                }
            });
        } else { // 如果是新增按钮权限
            list = sysPermissionInfoMapper.listPermissionByType2();
            list.forEach(item -> {
                if ("0".equals(item.getType()) || "1".equals(item.getType()) || "3".equals(item.getType())) {
                    item.setDisabled(true);
                }
            });
        }

        if (null == list || list.isEmpty()) {
            resultMap.put("data", null);
            resultMap.put("code", 0);
            resultMap.put("msg", "");
            return JsonTool.mapToJsonString(resultMap);
        }

        List<LayuiTree> resultList = TreeUtil.toTree(list, "0");
        resultMap.put("data", resultList);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        return JsonTool.mapToJsonString(resultMap);
    }



    /**
     * 修改菜单顺序
     * @param sysPermissionInfo
     * @return
     */
    @Override
    public int updateSort(SysPermissionInfo sysPermissionInfo) {
        return sysPermissionInfoMapper.updateSort(sysPermissionInfo);
    }


    /**
     * 新增菜单
     * @param sysPermissionInfo
     * @return
     */
    @Override
    public int addPermission(SysPermissionInfo sysPermissionInfo) {
        return sysPermissionInfoMapper.addPermission(sysPermissionInfo);
    }


    /**
     * 根据permissionId获取一个
     * @param permissionId
     * @return
     */
    @Override
    public SysPermissionInfo getOnePermission(String permissionId) {
        return sysPermissionInfoMapper.getOnePermission(permissionId);
    }


    /**
     * 修改菜单
     * @param s
     * @return
     */
    @Override
    public int updatePermission(SysPermissionInfo s) {
        return sysPermissionInfoMapper.updatePermission(s);
    }

}


