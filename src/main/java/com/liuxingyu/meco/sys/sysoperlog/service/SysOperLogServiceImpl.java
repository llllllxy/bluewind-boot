package com.liuxingyu.meco.sys.sysoperlog.service;

import com.liuxingyu.meco.sys.sysoperlog.entity.SysOperLog;
import com.liuxingyu.meco.sys.sysoperlog.mapper.SysOperLogMapper;
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

    private SysOperLogMapper sysOperLogMapper;

    @Autowired
    public SysOperLogServiceImpl(SysOperLogMapper sysOperLogMapper) {
        this.sysOperLogMapper = sysOperLogMapper;
    }

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
}
