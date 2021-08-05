package com.liuxingyu.meco.sys.sysquartzlog.service;

import com.liuxingyu.meco.sys.sysquartzlog.entity.SysQuartzLog;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-03-11-17:24
 **/
public interface SysQuartzLogService {

    List<SysQuartzLog> getAllLogByQuartzId(Integer quartzId);
}
