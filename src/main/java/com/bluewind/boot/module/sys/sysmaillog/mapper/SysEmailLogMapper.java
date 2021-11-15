package com.bluewind.boot.module.sys.sysmaillog.mapper;

import com.bluewind.boot.module.sys.sysmail.entity.SysEmailLog;
import org.springframework.stereotype.Repository;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:56
 **/
@Repository
public interface SysEmailLogMapper {

    int saveSysEmailLog(SysEmailLog sysEmailLog);

}
