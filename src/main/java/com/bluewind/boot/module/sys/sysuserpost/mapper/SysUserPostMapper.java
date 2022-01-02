package com.bluewind.boot.module.sys.sysuserpost.mapper;

import com.bluewind.boot.module.sys.sysuserpost.entity.SysUserPost;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:10
 **/
@Repository
public interface SysUserPostMapper {

    int deleteUserPostByUserId(String userId);

    int batchSaveUserPost(List<SysUserPost> list);
}
