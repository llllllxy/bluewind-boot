package com.liuxingyu.meco.sys.sysquartzlog.service;

import com.liuxingyu.meco.common.utils.DateTool;
import com.liuxingyu.meco.sys.sysquartzlog.entity.SysQuartzLog;
import com.liuxingyu.meco.sys.sysquartzlog.mapper.SysQuartzLogMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-03-11-17:25
 **/
@Service
public class SysQuartzLogServiceImpl implements SysQuartzLogService {

    @Autowired
    SysQuartzLogMapper sysQuartzLogMapper;

    @Override
    public List<SysQuartzLog> getAllLogByQuartzId(Integer quartzId) {
        List<SysQuartzLog> list = sysQuartzLogMapper.getAllLogByQuartzId(quartzId);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                String startTime = item.getStartTime();
                String endTime = item.getEndTime();
                item.setStartTime(DateTool.timeFormat(startTime, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
                item.setEndTime(DateTool.timeFormat(endTime, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            });
        }
        return list;
    }
}
