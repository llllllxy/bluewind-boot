package com.liuxingyu.meco.sys.sysconfig.mapper;

import com.liuxingyu.meco.sys.sysconfig.entity.SysConfig;
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
