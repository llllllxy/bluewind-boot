package com.bluewind.boot.module.system.userinfo.service;

import com.bluewind.boot.module.system.userinfo.entity.UserInfo;
import com.bluewind.boot.module.system.userinfo.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:22
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {
    final static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getOne(String account) {
        return userInfoMapper.getOne(account);
    }


    /**
     * 获取用户列表
     * @param map
     * @return
     */
    @Override
    public List<UserInfo> getSysUserInfoList(Map map) {
        return userInfoMapper.getSysUserInfoList(map);
    }


    /**
     * 删除一个系统用户
     * @param userId
     * @return
     */
    @Override
    public int delete(String userId) {
        return userInfoMapper.delete(userId);
    }


    /**
     * 批量删除系统用户
     * @param idList
     * @return
     */
    @Override
    public int batchDelete(List<String> idList) {
        return userInfoMapper.batchDelete(idList);
    }


    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    @Override
    public int doAdd(UserInfo userInfo) {
        return userInfoMapper.doAdd(userInfo);
    }


    /**
     * 通过id获取一条记录
     * @param userId
     * @return
     */
    @Override
    public UserInfo getOneById(String userId) {
        return userInfoMapper.getOneById(userId);
    }

    /**
     * 用户密码重置
     * @param userInfo
     * @return
     */
    @Override
    public int resetPass(UserInfo userInfo) {
        return userInfoMapper.resetPass(userInfo);
    }


    /**
     * 用户修改
     *
     * @param userInfo
     * @return
     */
    @Override
    public int doUpdate(UserInfo userInfo) {
        return userInfoMapper.doUpdate(userInfo);
    }

    /**
     * 校验用户账号是否唯一
     *
     * @param account 用户账号
     * @return
     */
    @Override
    public int checkUserNameUnique(String account) {
        return userInfoMapper.checkUserNameUnique(account);
    }

}
