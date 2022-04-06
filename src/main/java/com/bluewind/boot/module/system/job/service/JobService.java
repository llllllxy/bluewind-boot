package com.bluewind.boot.module.system.job.service;

import com.bluewind.boot.common.exception.TaskException;
import com.bluewind.boot.module.system.job.entity.Job;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2021-08-27-16:39
 **/
public interface JobService {

    List<Job> list(Map<String, String> paraMap);

    Job getOne(String jobId);

    void executeonce(String jobId) throws SchedulerException, TaskException;

    int insertJob(Job job) throws SchedulerException, TaskException;

    int start(String jobId) throws SchedulerException, TaskException;

    int stop(String jobId) throws SchedulerException, TaskException;

    int delete(String jobId) throws SchedulerException;

    int updateJob(Job job) throws SchedulerException, TaskException;
}
