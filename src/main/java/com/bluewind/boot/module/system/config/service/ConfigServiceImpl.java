package com.bluewind.boot.module.system.config.service;

import com.bluewind.boot.module.system.config.entity.Config;
import com.bluewind.boot.module.system.config.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:45
 **/
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    public Config getSysConfig() {
        return configMapper.getSysConfig();
    }


    public int updateSysConfig(Config config) {
        return configMapper.updateSysConfig(config);
    }
}
