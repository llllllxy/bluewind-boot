package com.liuxingyu.meco.sys.sysjob.service;

import com.liuxingyu.meco.common.exception.TaskException;
import com.liuxingyu.meco.sys.sysjob.entity.SysJob;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2021-08-27-16:39
 **/
public interface SysJobService {

    List<SysJob> list(Map<String, String> paraMap);

    SysJob getOne(String jobId);

    void executeonce(String jobId) throws SchedulerException;

    int insertJob(SysJob job) throws SchedulerException, TaskException;

    int start(String jobId) throws SchedulerException;

    int stop(String jobId) throws SchedulerException;

    int delete(String jobId) throws SchedulerException;

    int updateJob(SysJob sysJob) throws SchedulerException, TaskException;
}
