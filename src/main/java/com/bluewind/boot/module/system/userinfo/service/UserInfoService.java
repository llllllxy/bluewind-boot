package com.bluewind.boot.module.system.userinfo.service;

import com.bluewind.boot.module.system.userinfo.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:22
 **/
public interface UserInfoService {
    /**
     * 获取一个用户的全部信息
     * @param account
     * @return
     */
    UserInfo getOne(String account);

    /**
     * 获取用户列表
     * @param map
     * @return
     */
    List<UserInfo> getSysUserInfoList(Map map);

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
     * @param userInfo
     * @return
     */
    int doAdd(UserInfo userInfo);

    /**
     * 通过id获取一条记录
     * @param userId
     * @return
     */
    UserInfo getOneById(String userId);


    /**
     * 用户密码重置
     * @param userInfo
     * @return
     */
    int resetPass(UserInfo userInfo);


    /**
     * 用户修改
     *
     * @param userInfo
     * @return
     */
    int doUpdate(UserInfo userInfo);


    /**
     * 校验用户账号是否唯一
     *
     * @param account 用户账号
     * @return
     */
    int checkUserNameUnique(String account);


}
