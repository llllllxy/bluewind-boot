package com.liuxingyu.meco.sys.sysuserrole.service;

import com.liuxingyu.meco.common.consts.SystemConst;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.configuration.security.UserTokenUtil;
import com.liuxingyu.meco.sys.sysuserrole.entity.SysUserRole;
import com.liuxingyu.meco.sys.sysuserrole.mapper.SysUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:47
 **/
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    final static Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Set<String> listUserRoleByUserId(Integer userId) {
        logger.info("SysUserRoleServiceImpl -- listUserRoleByUserId -- userId = {}", userId);
        String sessionId = UserTokenUtil.getToken();

        Object object = redisUtil.get(SystemConst.SYSTEM_USER_ROLE + ":" + sessionId);
        if (null != object) {
            logger.info("SysUserRoleServiceImpl -- listUserRoleByUserId -- object = {}", object);
            return (Set<String>) object;
        }
        Set<String> set = sysUserRoleMapper.listUserRoleByUserId(userId);
        redisUtil.set(SystemConst.SYSTEM_USER_ROLE + ":" + sessionId, set, 1800);

        return set;
    }



    /**
     * 用户授权
     * @param id
     * @param roles
     * @return
     */
    @Override
    public BaseResult doAuthorize(Integer id, String roles) {
        // 先删除用户旧的角色
        int num = sysUserRoleMapper.deleteUserRoleByUserId(id);
        // 保存用户刚赋予的角色
        if (StringUtils.isNotBlank(roles)) {
            String[] roleArr = roles.split(",");
            List<SysUserRole> list = new ArrayList<>();
            SysUserRole sysUserRole = null;
            for (String item : roleArr) {
                sysUserRole = new SysUserRole();
                sysUserRole.setUserId(id);
                sysUserRole.setRoleId(item);
                list.add(sysUserRole);
            }
            int numm = sysUserRoleMapper.batchSaveUserRole(list);
            if (numm > 0) {
                return BaseResult.success("用户角色绑定成功！");
            } else {
                return BaseResult.failure("用户角色绑定失败！");
            }
        } else {
            return BaseResult.failure("角色信息为空！");
        }
    }

}
