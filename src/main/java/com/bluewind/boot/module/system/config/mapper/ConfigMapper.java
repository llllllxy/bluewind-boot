package com.bluewind.boot.module.system.config.mapper;

import com.bluewind.boot.module.system.config.entity.Config;
import org.springframework.stereotype.Repository;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:45
 **/
@Repository
public interface ConfigMapper {

    Config getSysConfig();

    int updateSysConfig(Config config);
}
