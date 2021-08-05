package com.liuxingyu.meco.sys.sysquartzlog.mapper;

import com.liuxingyu.meco.sys.sysquartzlog.entity.SysQuartzLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-03-11-17:21
 **/
@Repository
public interface SysQuartzLogMapper {

    int insertQuartzLog(SysQuartzLog sysQuartzLog);


    List<SysQuartzLog> getAllLogByQuartzId(Integer quartzId);

}
