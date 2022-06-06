package com.bluewind.boot.module.system.userrole.service;

import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.module.system.userrole.entity.UserRole;
import com.bluewind.boot.module.system.userrole.mapper.UserRoleMapper;
import com.bluewind.boot.module.system.userrole.vo.XmSelectVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:47
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {
    final static Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Set<String> listUserRoleByUserId(String userId) {
        String token = SecurityUtil.getUserKey();

        Object object = redisUtil.get(SystemConst.SYSTEM_USER_ROLE + ":" + token);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = userRoleMapper.listUserRoleByUserId(userId);
        redisUtil.set(SystemConst.SYSTEM_USER_ROLE + ":" + token, set, 1800);

        return set;
    }


    /**
     * 用户授权
     * @param userId
     * @param roles
     * @return
     */
    @Override
    public BaseResult doAuthorize(String userId, String roles) {
        // 先全部删除用户旧的角色
        int num = userRoleMapper.deleteUserRoleByUserId(userId);
        // 保存用户新赋予的角色
        if (StringUtils.isNotBlank(roles)) {
            String[] roleArr = roles.split(",");
            List<UserRole> list = new ArrayList<>();
            UserRole userRole = null;
            for (String item : roleArr) {
                userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(item);
                list.add(userRole);
            }
            int numm = userRoleMapper.batchSaveUserRole(list);
            if (numm > 0) {
                return BaseResult.success("用户角色更新成功！");
            } else {
                return BaseResult.failure("用户角色更新失败！");
            }
        } else {
            return BaseResult.failure("角色信息为空！");
        }
    }


    /**
     * 根据用户id查询角色，给xmselect赋值
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> listRoleByUserId(String userId) {
        List<XmSelectVo> list = userRoleMapper.listRoleByUserId(userId);
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("data", list);
        return selectMap;
    }

}
