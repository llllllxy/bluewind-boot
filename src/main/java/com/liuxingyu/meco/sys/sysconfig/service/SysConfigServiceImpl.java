package com.liuxingyu.meco.sys.sysconfig.service;

import com.liuxingyu.meco.sys.sysconfig.entity.SysConfig;
import com.liuxingyu.meco.sys.sysconfig.mapper.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:45
 **/
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public SysConfig getSysConfig() {
        return sysConfigMapper.getSysConfig();
    }


    public int updateSysConfig(SysConfig sysConfig) {
        return sysConfigMapper.updateSysConfig(sysConfig);
    }
}
