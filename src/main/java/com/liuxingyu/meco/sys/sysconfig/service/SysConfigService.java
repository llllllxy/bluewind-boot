package com.liuxingyu.meco.sys.sysconfig.service;

import com.liuxingyu.meco.sys.sysconfig.entity.SysConfig;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:45
 **/
public interface SysConfigService {
    SysConfig getSysConfig();

    int updateSysConfig(SysConfig sysConfig);
}
