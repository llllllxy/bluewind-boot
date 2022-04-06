package com.bluewind.boot.module.system.userpost.mapper;

import com.bluewind.boot.module.system.userpost.entity.UserPost;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:10
 **/
@Repository
public interface UserPostMapper {

    int deleteUserPostByUserId(String userId);

    int batchSaveUserPost(List<UserPost> list);
}
