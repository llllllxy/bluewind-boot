package com.bluewind.boot.module.sys.syspostinfo.mapper;

import com.bluewind.boot.module.sys.syspostinfo.entity.SysPostInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-12-01-14:11
 * @description 岗位信息管理
 **/
@Repository
public interface SysPostInfoMapper {

    List<SysPostInfo> getSysPostInfoList(Map<String, String> paraMap);

    int addPostInfo(SysPostInfo sysPostInfo);

    SysPostInfo getOne(String postId);

    int updatePostInfo(SysPostInfo sysPostInfo);

    int deleteOne(String postId);

}
