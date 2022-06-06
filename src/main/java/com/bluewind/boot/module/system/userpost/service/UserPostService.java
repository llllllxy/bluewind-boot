package com.bluewind.boot.module.system.userpost.service;

import java.util.Map;

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

    /**
     * 根据userId获取用户岗位信息
     * @param userId
     * @return
     */
    Map<String, Object> listPostByUserId(String userId);
}
