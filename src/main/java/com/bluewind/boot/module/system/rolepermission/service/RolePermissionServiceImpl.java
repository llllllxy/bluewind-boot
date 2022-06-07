package com.bluewind.boot.module.system.rolepermission.service;

import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.module.system.rolepermission.entity.RolePermission;
import com.bluewind.boot.module.system.rolepermission.mapper.RolePermissionMapper;
import com.bluewind.boot.module.system.rolepermission.vo.LayuiTree;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:49
 **/
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    final static Logger logger = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

    @Autowired
    private RolePermissionMapper permissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Set<String> listRolePermissionByUserId(String userId) {
        String token = SecurityUtil.getUserKey();

        Object object = redisUtil.get(SystemConst.SYSTEM_USER_PERMISSION + ":" + token);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = permissionMapper.listRolePermissionByUserId(userId);
        redisUtil.set(SystemConst.SYSTEM_USER_PERMISSION + ":" + token, set, 1800);

        return set;
    }


    /**
     * 角色权限更新，先删后插
     *
     * @return
     */
    @Override
    @Transactional
    public int doAuthorize(String roleId, String permIds) {
        // 先删除sys_role_permission表里之前存的
        int num = permissionMapper.deleteRolePermissionByRoleId(roleId);

        // 再进行重新插入角色权限
        if (StringUtils.isNotBlank(permIds)) {
            String[] permArr = permIds.split(",");
            List<RolePermission> list = new ArrayList<>();
            RolePermission rolePermission = null;
            for (String item : permArr) {
                rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(item);
                list.add(rolePermission);
            }
            return permissionMapper.batchInsertRolePermission(list);
        } else {
            return 0;
        }
    }


    /**
     * 角色赋权，根据角色id获取权限树tree组件
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> listPermissionByRoleId(String roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<LayuiTree> list = permissionMapper.listPermissionByRoleId(roleId);
        if (null == list || list.isEmpty()) {
            resultMap.put("data", null);
            resultMap.put("code", 0);
            resultMap.put("msg", "");
            return resultMap;
        }

        List<LayuiTree> resultList = toTree(list, "0");
        resultMap.put("data", resultList);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        return resultMap;
    }


    /**
     * 获取菜单树(list转tree)
     *
     * @param treeList
     * @param pid
     * @return
     */
    public static List<LayuiTree> toTree(List<LayuiTree> treeList, String pid) {
        List<LayuiTree> retList = new ArrayList<>();
        for (LayuiTree parent : treeList) {
            if (pid.equals(parent.getParentId())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    /**
     * 递归获取子节点
     *
     * @param parent
     * @param treeList
     * @return
     */
    private static LayuiTree findChildren(LayuiTree parent, List<LayuiTree> treeList) {
        for (LayuiTree child : treeList) {
            if (parent.getPermissionId().equals(child.getParentId())) {
                // 这里是为了将父节点的选中状态置为false，为了适配前端eleTree
                // parent.setChecked(false); // eleTree设置了isDefaultChangePstatus属性，好像就不需要这么干了
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
