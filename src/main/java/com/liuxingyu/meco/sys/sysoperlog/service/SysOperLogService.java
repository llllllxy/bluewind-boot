package com.liuxingyu.meco.sys.sysoperlog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuxingyu.meco.sys.sysoperlog.entity.SysOperLog;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-15:20
 **/
public interface SysOperLogService extends IService<SysOperLog> {
    /**
     * 获取操作日志列表
     *
     * @param map
     * @return
     */
    Page<SysOperLog> list(Map map);

    /**
     * 保存操作日志
     *
     * @param sysOperLog
     * @return
     */
    int saveOperLog(SysOperLog sysOperLog);
}
