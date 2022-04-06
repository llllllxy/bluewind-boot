package com.bluewind.boot.module.system.operlog.mapper;

import com.bluewind.boot.module.system.operlog.entity.OperLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-14:02
 **/
@Repository
public interface OperLogMapper {

    /**
     * 保存操作日志
     *
     * @param operLog
     * @return
     */
    int saveOperLog(OperLog operLog);

    /**
     * 获取操作日志列表
     *
     * @param map
     * @return
     */
    List<OperLog> list(Map map);

    /**
     * 批量删除操作日志
     * @param logIdList
     * @return
     */
    int batchDelete(List<String> logIdList);

    /**
     * 清空操作日志
     */
    void emptyLog();
}
