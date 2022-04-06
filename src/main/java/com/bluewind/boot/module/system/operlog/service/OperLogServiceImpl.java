package com.bluewind.boot.module.system.operlog.service;

import com.bluewind.boot.module.system.operlog.entity.OperLog;
import com.bluewind.boot.module.system.operlog.mapper.OperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-15:20
 **/
@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;


    @Override
    public List<OperLog> list(Map map) {
        return operLogMapper.list(map);
    }

    /**
     * 保存操作日志
     *
     * @param operLog
     * @return
     */
    @Override
    public int saveOperLog(OperLog operLog) {
        return operLogMapper.saveOperLog(operLog);
    }

    /**
     * 批量删除操作日志
     * @param logIdList
     * @return
     */
    @Override
    public int batchDelete(List<String> logIdList) {
        return operLogMapper.batchDelete(logIdList);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void emptyLog() {
        operLogMapper.emptyLog();
    }
}
