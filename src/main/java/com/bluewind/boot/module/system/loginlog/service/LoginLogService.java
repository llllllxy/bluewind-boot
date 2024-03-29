package com.bluewind.boot.module.system.loginlog.service;

import com.bluewind.boot.module.system.loginlog.entity.LoginLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-18-12:53
 **/
public interface LoginLogService {

    /**
     * 分页查询
     */
    List<LoginLog> list(Map map);

    /**
     * 保存登录日志
     */
    void saveLoginlog(HttpServletRequest request, String account, String status, String descript, String redisKey);


    /**
     * 获取当前登陆用户记录
     */
    List<Map> onlineList(Map paraMap);


    /**
     * 批量删除登陆日志
     * @param logIdList
     * @return
     */
    int batchDelete(List<String> logIdList);

    /**
     * 清空登陆日志
     */
    void emptyLog();
}
