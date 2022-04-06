package com.bluewind.boot.module.system.postinfo.mapper;

import com.bluewind.boot.module.system.postinfo.entity.PostInfo;
import com.bluewind.boot.module.system.postinfo.entity.PostXmSelect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-12-01-14:11
 * @description 岗位信息管理
 **/
@Repository
public interface PostInfoMapper {

    List<PostInfo> getSysPostInfoList(Map<String, String> paraMap);

    int addPostInfo(PostInfo postInfo);

    PostInfo getOne(String postId);

    int updatePostInfo(PostInfo postInfo);

    int deleteOne(String postId);

    List<PostXmSelect> listUserPostForSelect(@Param("userId") String userId);

    List<PostXmSelect> listAllPostForSelect();

}
