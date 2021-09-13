package com.bluewind.boot.sys.sysloginlog.mapper;

import com.bluewind.boot.sys.sysloginlog.entity.SysLoginLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2021-02-18-12:58
 **/
@Repository
public interface SysLoginLogMapper {

    int saveLoginlog(SysLoginLog sysLoginLog);

    List<SysLoginLog> getLoginLoglist(Map map);

    List<Map> onlineList(Map paraMap);

}
