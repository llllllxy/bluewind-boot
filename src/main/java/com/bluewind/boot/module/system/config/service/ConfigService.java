package com.bluewind.boot.module.system.config.service;

import com.bluewind.boot.module.system.config.entity.Config;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:45
 **/
public interface ConfigService {
    Config getSysConfig();

    int updateSysConfig(Config config);
}
