package com.bluewind.boot.module.system.loginlog.mapper;

import com.bluewind.boot.module.system.loginlog.entity.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2021-02-18-12:58
 **/
@Repository
public interface LoginLogMapper {

    int saveLoginlog(LoginLog loginLog);

    List<LoginLog> getLoginLoglist(Map map);

    List<Map> onlineList(Map paraMap);

    int batchDelete(@Param("logIdList") List<String> logIdList);

    void emptyLog();
}
