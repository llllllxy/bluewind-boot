package com.bluewind.boot.module.sys.sysoperlog.service;

import com.bluewind.boot.module.sys.sysoperlog.entity.SysOperLog;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-15:20
 **/
public interface SysOperLogService {
    /**
     * 获取操作日志列表
     *
     * @param map
     * @return
     */
    List<SysOperLog> list(Map map);

    /**
     * 保存操作日志
     *
     * @param sysOperLog
     * @return
     */
    int saveOperLog(SysOperLog sysOperLog);
}
