package com.bluewind.boot.module.sys.sysuserpost.service;

import com.bluewind.boot.common.base.BaseResult;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:10
 **/
public interface SysUserPostService {


    /**
     * 用户岗位信息绑定
     * @param userId
     * @param postStrs
     * @return
     */
    int userPostBind(String userId, String postStrs);
}
