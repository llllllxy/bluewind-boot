package com.liuxingyu.meco.sys.sysuserrole.service;

import com.liuxingyu.meco.common.base.BaseResult;

import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:47
 **/
public interface SysUserRoleService {
    /**
     * 登录时，根据用户id查询全部角色标识
     */
    Set<String> listUserRoleByUserId(Integer userId);



    /**
     * 用户授权
     * @param id
     * @param roles
     * @return
     */
    BaseResult doAuthorize(Integer id, String roles);
}
