package com.liuxingyu.meco.sys.sysuserinfo.service;

import com.liuxingyu.meco.sys.sysuserinfo.mapper.SysUserInfoMapper;
import com.liuxingyu.meco.sys.sysuserinfo.entity.SysUserInfo;
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
public class SysUserInfoServiceImpl implements SysUserInfoService {
    final static Logger logger = LoggerFactory.getLogger(SysUserInfoServiceImpl.class);

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    @Override
    public SysUserInfo getOne(String userName) {
        return sysUserInfoMapper.getOne(userName);
    }


    /**
     * 获取用户列表
     * @param map
     * @return
     */
    @Override
    public List<SysUserInfo> getSysUserInfoList(Map map) {
        return sysUserInfoMapper.getSysUserInfoList(map);
    }


    /**
     * 删除一个系统用户
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {
        return sysUserInfoMapper.delete(id);
    }


    /**
     * 批量删除系统用户
     * @param idList
     * @return
     */
    @Override
    public int batchDelete(List<Integer> idList) {
        return sysUserInfoMapper.batchDelete(idList);
    }


    /**
     * 新增用户
     * @param sysUserInfo
     * @return
     */
    @Override
    public int doAdd(SysUserInfo sysUserInfo) {
        return sysUserInfoMapper.doAdd(sysUserInfo);
    }


    /**
     * 通过id获取一条记录
     * @param id
     * @return
     */
    @Override
    public SysUserInfo getOneById(int id) {
        return sysUserInfoMapper.getOneById(id);
    }

    /**
     * 用户密码重置
     * @param sysUserInfo
     * @return
     */
    @Override
    public int resetPass(SysUserInfo sysUserInfo) {
        return sysUserInfoMapper.resetPass(sysUserInfo);
    }


    /**
     * 用户修改
     *
     * @param sysUserInfo
     * @return
     */
    @Override
    public int doUpdate(SysUserInfo sysUserInfo) {
        return sysUserInfoMapper.doUpdate(sysUserInfo);
    }

    /**
     * 校验用户账号是否唯一
     *
     * @param account 用户账号
     * @return
     */
    @Override
    public int checkUserNameUnique(String account) {
        return sysUserInfoMapper.checkUserNameUnique(account);
    }

}
