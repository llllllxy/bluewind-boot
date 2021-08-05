package com.liuxingyu.meco.sys.sysuserinfo.mapper;

import com.liuxingyu.meco.sys.sysuserinfo.entity.SysUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:28
 **/
@Repository
public interface SysUserInfoMapper {
    SysUserInfo getOne(String userName);

    /**
     * 获取用户列表
     * @param map
     * @return
     */
    List<SysUserInfo> getSysUserInfoList(Map map);


    /**
     * 删除一个系统用户
     * @param id
     * @return
     */
    int delete(int id);


    /**
     * 批量删除系统用户
     * @param idList
     * @return
     */
    int batchDelete(@Param("idList")List<Integer> idList);


    /**
     * 新增用户
     * @param sysUserInfo
     * @return
     */
    int doAdd(SysUserInfo sysUserInfo);


    /**
     * 通过id获取一条记录
     * @param id
     * @return
     */
    SysUserInfo getOneById(int id);


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
    int checkUserNameUnique(@Param("account") String account);
}
