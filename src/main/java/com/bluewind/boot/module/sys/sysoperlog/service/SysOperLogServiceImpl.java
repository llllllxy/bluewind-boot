package com.bluewind.boot.module.sys.sysoperlog.service;

import com.bluewind.boot.module.sys.sysoperlog.entity.SysOperLog;
import com.bluewind.boot.module.sys.sysoperlog.mapper.SysOperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-15:20
 **/
@Service
public class SysOperLogServiceImpl implements SysOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;


    @Override
    public List<SysOperLog> list(Map map) {
        return sysOperLogMapper.list(map);
    }

    /**
     * 保存操作日志
     *
     * @param sysOperLog
     * @return
     */
    @Override
    public int saveOperLog(SysOperLog sysOperLog) {
        return sysOperLogMapper.saveOperLog(sysOperLog);
    }

    /**
     * 批量删除操作日志
     * @param logIdList
     * @return
     */
    @Override
    public int batchDelete(List<String> logIdList) {
        return sysOperLogMapper.batchDelete(logIdList);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void emptyLog() {
        sysOperLogMapper.emptyLog();
    }
}
