package com.bluewind.boot.module.system.userpost.service;

import com.bluewind.boot.module.system.userpost.entity.UserPost;
import com.bluewind.boot.module.system.userpost.mapper.UserPostMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:10
 **/
@Service
public class UserPostServiceImpl implements UserPostService {

    @Autowired
    private UserPostMapper userPostMapper;

    /**
     * 用户岗位信息绑定
     * @param userId
     * @param postStrs
     * @return
     */
    public int userPostBind(String userId, String postStrs) {
        // 先全部删除用户旧的岗位
        int num = userPostMapper.deleteUserPostByUserId(userId);

        // 保存用户新赋予的岗位
        if (StringUtils.isNotBlank(postStrs)) {
            String[] postArr = postStrs.split(",");
            List<UserPost> list = new ArrayList<>();
            UserPost userPost = null;
            for (String item : postArr) {
                userPost = new UserPost();
                userPost.setUserId(userId);
                userPost.setPostId(item);
                list.add(userPost);
            }
            int numm = userPostMapper.batchSaveUserPost(list);
            return numm;
        } else {
            return 0;
        }

    }

}
