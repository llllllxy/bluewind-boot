package com.bluewind.boot.module.system.permissioninfo.service;

import com.bluewind.boot.module.system.permissioninfo.entity.PermissionInfo;
import com.bluewind.boot.module.system.permissioninfo.mapper.PermissionInfoMapper;
import com.bluewind.boot.module.system.rolepermission.vo.LayuiTree;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<LayuiTree> childList = findChildList(list, permissionId);

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
        List<LayuiTree> childList = findChildList(list, permissionId);

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
        List<LayuiTree> childList = findChildList(list, permissionId);

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
     * 获取某个父节点下的所有子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid 父节点
     * @return
     */
    public static List<LayuiTree> findChildList(List<LayuiTree> menuList, String pid) {
        // 子节点
        List<LayuiTree> childMenu = new ArrayList<>();
        return recursionChildList(menuList, pid, childMenu);
    }


    /**
     * 获取子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid 父节点
     * @return
     */
    private static List<LayuiTree> recursionChildList(List<LayuiTree> menuList, String pid, List<LayuiTree> childMenu) {
        for (LayuiTree mu : menuList) {
            // 遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId().equals(pid)) {
                // 递归遍历下一级
                recursionChildList(menuList, mu.getPermissionId(), childMenu);
                childMenu.add(mu);
            }
        }
        return childMenu;
    }


    /**
     * 获取权限列表
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


