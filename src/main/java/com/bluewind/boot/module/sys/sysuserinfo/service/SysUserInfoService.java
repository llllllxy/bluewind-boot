package com.bluewind.boot.module.sys.sysuserinfo.service;

import com.bluewind.boot.module.sys.sysuserinfo.entity.SysUserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:22
 **/
public interface SysUserInfoService {
    /**
     * 获取一个用户的全部信息
     * @param account
     * @return
     */
    SysUserInfo getOne(String account);

    /**
     * 获取用户列表
     * @param map
     * @return
     */
    List<SysUserInfo> getSysUserInfoList(Map map);

    /**
     * 删除一个系统用户
     * @param userId
     * @return
     */
    int delete(String userId);


    /**
     * 批量删除系统用户
     * @param idList
     * @return
     */
    int batchDelete(List<String> idList);

    /**
     * 新增用户
     * @param sysUserInfo
     * @return
     */
    int doAdd(SysUserInfo sysUserInfo);

    /**
     * 通过id获取一条记录
     * @param userId
     * @return
     */
    SysUserInfo getOneById(String userId);


    /**
     * 用户密码重置
     * @param sysUserInfo
     * @return
     */
    int resetPass(SysUserInfo sysUserInfo);


    /**
     * 用户修改
     *
     * @param sysUserInfo
     * @return
     */
    int doUpdate(SysUserInfo sysUserInfo);


    /**
     * 校验用户账号是否唯一
     *
     * @param account 用户账号
     * @return
     */
    int checkUserNameUnique(String account);


}
