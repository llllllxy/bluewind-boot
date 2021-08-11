package com.liuxingyu.meco.sys.sysoperlog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxingyu.meco.common.utils.lang.StringUtils;
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
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    private SysOperLogMapper sysOperLogMapper;

    @Autowired
    public SysOperLogServiceImpl(SysOperLogMapper sysOperLogMapper) {
        this.sysOperLogMapper = sysOperLogMapper;
    }

    @Override
    public Page<SysOperLog> list(Map map) {
        QueryWrapper<SysOperLog> queryWrapper = Wrappers.query();
        String model = map.get("model") == null ? "" : (String) map.get("model");
        String type = map.get("type") == null ? "" : (String) map.get("type");
        String sortName = map.get("sortName") == null ? "" : (String) map.get("sortName");
        String sortOrder = map.get("sortOrder") == null ? "" : (String) map.get("sortOrder");
        String createTime = map.get("createTime") == null ? "" : (String) map.get("createTime");
        int pageSize = map.get("pageSize") == null ? 10 : (Integer) map.get("pageSize");
        int pageNum = map.get("pageNum") == null ? 1 : (Integer) map.get("pageNum");

        if (StringUtils.isNotBlank(model)) {
            queryWrapper.like("model", model);
        }
        if (StringUtils.isNotBlank(type)) {
            queryWrapper.like("type", type);
        }
        if (StringUtils.isNotBlank(createTime)) {
            queryWrapper.like("create_time", createTime);
        }
        if (StringUtils.isNotBlank(sortName)) {
            if (StringUtils.isNotBlank(sortOrder) && "desc".equals(sortOrder)) {
                queryWrapper.orderByDesc(sortName);
            } else {
                queryWrapper.orderByAsc(sortName);
            }
        } else {
            queryWrapper.orderByDesc("id");
        }
        return this.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    /**
     * 保存操作日志
     *
     * @param sysOperLog
     * @return
     */
    @Override
    public int saveOperLog(SysOperLog sysOperLog) {
        return sysOperLogMapper.insert(sysOperLog);
    }
}
