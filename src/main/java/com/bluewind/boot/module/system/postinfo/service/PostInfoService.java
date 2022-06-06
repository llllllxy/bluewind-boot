package com.bluewind.boot.module.system.postinfo.service;

import com.bluewind.boot.module.system.postinfo.entity.PostInfo;
import com.bluewind.boot.module.system.postinfo.mapper.PostInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-12-01-14:11
 * @description 岗位信息管理
 **/
@Service
public class PostInfoService {

    @Autowired
    private PostInfoMapper postInfoMapper;

    /**
     * 岗位数据分页查询
     * @param paraMap
     * @return
     */
    public List<PostInfo> getSysPostInfoList(Map<String, String> paraMap) {
        return postInfoMapper.getSysPostInfoList(paraMap);
    }

    /**
     * 岗位信息新增
     * @param postInfo
     * @return
     */
    public int add(PostInfo postInfo) {
        return postInfoMapper.addPostInfo(postInfo);
    }

    /**
     * 岗位信息明细
     * @param postId
     * @return
     */
    public PostInfo getOne(String postId) {
        return postInfoMapper.getOne(postId);
    }

    /**
     * 岗位信息修改
     * @param postInfo
     * @return
     */
    public int update(PostInfo postInfo) {
        return postInfoMapper.updatePostInfo(postInfo);
    }


    /**
     * 岗位信息删除
     * @param postId
     * @return
     */
    public int delete(String postId) {
        return postInfoMapper.deleteOne(postId);
    }

}
