package com.bluewind.boot.module.system.userpost.service;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:10
 **/
public interface UserPostService {


    /**
     * 用户岗位信息绑定
     * @param userId
     * @param postStrs
     * @return
     */
    int userPostBind(String userId, String postStrs);
}
