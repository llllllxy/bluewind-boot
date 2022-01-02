package com.bluewind.boot.module.sys.syspostinfo.service;

import com.bluewind.boot.module.sys.syspostinfo.entity.SysPostInfo;
import com.bluewind.boot.module.sys.syspostinfo.mapper.SysPostInfoMapper;
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
public class SysPostInfoService {

    @Autowired
    private SysPostInfoMapper sysPostInfoMapper;

    /**
     * 岗位数据分页查询
     * @param paraMap
     * @return
     */
    public List<SysPostInfo> getSysPostInfoList(Map<String, String> paraMap) {
        return sysPostInfoMapper.getSysPostInfoList(paraMap);
    }

    /**
     * 岗位信息新增
     * @param sysPostInfo
     * @return
     */
    public int add(SysPostInfo sysPostInfo) {
        return sysPostInfoMapper.addPostInfo(sysPostInfo);
    }

    /**
     * 岗位信息明细
     * @param postId
     * @return
     */
    public SysPostInfo getOne(String postId) {
        return sysPostInfoMapper.getOne(postId);
    }

    /**
     * 岗位信息修改
     * @param sysPostInfo
     * @return
     */
    public int update(SysPostInfo sysPostInfo) {
        return sysPostInfoMapper.updatePostInfo(sysPostInfo);
    }


    /**
     * 岗位信息删除
     * @param postId
     * @return
     */
    public int delete(String postId) {
        return sysPostInfoMapper.deleteOne(postId);
    }


}
