package com.bluewind.boot.module.sys.sysjoblog.mapper;

import com.bluewind.boot.module.sys.sysjoblog.entity.SysJobLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 调度任务日志信息 数据层
 *
 * @author liuxingyu01
 * @date 2021-08-27-16:24
 **/
@Repository
public interface SysJobLogMapper {
    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    /**
     * 查询所有调度任务日志
     *
     * @return 调度任务日志列表
     */
    public List<SysJobLog> selectJobLogAll();

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    public SysJobLog selectJobLogById(String jobLogId);

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     * @return 结果
     */
    public int insertJobLog(SysJobLog jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobLogByIds(String[] logIds);

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     * @return 结果
     */
    public int deleteJobLogById(String jobId);

    /**
     * 清空任务日志
     */
    public void cleanJobLog();

    /**
     * 根据jobId获取执行日志
     * @param jobId
     * @return
     */
    List<SysJobLog> getLogByJobId(String jobId);
}
