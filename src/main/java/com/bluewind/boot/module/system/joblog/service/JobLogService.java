package com.bluewind.boot.module.system.joblog.service;

import com.bluewind.boot.module.system.joblog.entity.JobLog;

import java.util.List;

/**
 * 定时任务调度日志信息信息 服务层
 *
 * @author liuxingyu01
 * @date 2021-08-27-13:15
 **/
public interface JobLogService {
    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    List<JobLog> selectJobLogList(JobLog jobLog);

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    JobLog selectJobLogById(String jobLogId);

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    void addJobLog(JobLog jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的日志ID
     * @return 结果
     */
    int deleteJobLogByIds(String[] logIds);

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     * @return 结果
     */
    int deleteJobLogById(String jobId);

    /**
     * 清空任务日志
     */
    void cleanJobLog();

    /**
     * 根据jobId获取执行日志
     * @param jobId
     * @return
     */
    List<JobLog> getLogByJobId(String jobId);
}
