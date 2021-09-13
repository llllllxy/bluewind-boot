package com.bluewind.boot.module.sys.sysconfig.mapper;

import com.bluewind.boot.module.sys.sysconfig.entity.SysConfig;
import org.springframework.stereotype.Repository;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:45
 **/
@Repository
public interface SysConfigMapper {

    SysConfig getSysConfig();

    int updateSysConfig(SysConfig sysConfig);
}
