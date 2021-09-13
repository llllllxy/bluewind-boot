package com.bluewind.boot.module.sys.sysrolepermission.service;

import com.bluewind.boot.common.configuration.security.SecurityUtil;
import com.bluewind.boot.module.sys.sysrolepermission.entity.SysRolePermission;
import com.bluewind.boot.module.sys.sysrolepermission.mapper.SysRolePermissionMapper;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:49
 **/
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    final static Logger logger = LoggerFactory.getLogger(SysRolePermissionServiceImpl.class);

    @Autowired
    private SysRolePermissionMapper permissionMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Set<String> listRolePermissionByUserId(Integer userId) {
        logger.info("SysRolePermissionServiceImpl -- listRolePermissionByUserId -- userId = {}", userId);
        String token = SecurityUtil.getUserKey();

        Object object = redisUtil.get(SystemConst.SYSTEM_USER_PERMISSION + ":" + token);
        if (null != object) {
            logger.info("SysRolePermissionServiceImpl -- listRolePermissionByUserId -- getCacheSuccess = {}", object);
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
            List<SysRolePermission> list = new ArrayList<>();
            SysRolePermission sysRolePermission = null;
            for (String item : permArr) {
                sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setPermissionId(item);
                list.add(sysRolePermission);
            }
            int numm = permissionMapper.batchInsertRolePermission(list);
            return numm;
        } else {
            return 0;
        }
    }


}
