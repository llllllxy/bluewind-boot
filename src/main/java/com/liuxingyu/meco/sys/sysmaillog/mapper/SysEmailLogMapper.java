package com.liuxingyu.meco.sys.sysmaillog.mapper;

import com.liuxingyu.meco.sys.sysmail.entity.SysEmailLogVO;
import org.springframework.stereotype.Repository;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:56
 **/
@Repository
public interface SysEmailLogMapper {

    int saveSysEmailLog(SysEmailLogVO sysEmailLogVO);

}
