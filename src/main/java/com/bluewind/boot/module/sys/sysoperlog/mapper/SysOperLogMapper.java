package com.bluewind.boot.module.sys.sysoperlog.mapper;

import com.bluewind.boot.module.sys.sysoperlog.entity.SysOperLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-14:02
 **/
@Repository
public interface SysOperLogMapper {

    /**
     * 保存操作日志
     *
     * @param sysOperLog
     * @return
     */
    int saveOperLog(SysOperLog sysOperLog);

    /**
     * 获取操作日志列表
     *
     * @param map
     * @return
     */
    List<SysOperLog> list(Map map);
}
