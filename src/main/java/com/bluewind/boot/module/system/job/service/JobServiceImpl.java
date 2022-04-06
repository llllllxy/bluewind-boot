package com.bluewind.boot.module.system.job.service;

import com.bluewind.boot.common.consts.ScheduleConst;
import com.bluewind.boot.common.exception.TaskException;
import com.bluewind.boot.common.config.quartz.ScheduleUtils;
import com.bluewind.boot.module.system.job.entity.Job;
import com.bluewind.boot.module.system.job.mapper.JobMapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2021-08-27-16:39
 **/
@Service
public class JobServiceImpl implements JobService {
    final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    /**
     * 查询定时任务列表
     *
     * @param paraMap
     * @return
     */
    @Override
    public List<Job> list(Map<String, String> paraMap) {
        return jobMapper.list(paraMap);
    }


    /**
     * 获取一个定时任务实例
     *
     * @param jobId
     * @return
     */
    @Override
    public Job getOne(String jobId) {
        return jobMapper.getOne(jobId);
    }


    /**
     * 执行一次
     *
     * @param jobId
     * @return
     */
    @Override
    @Transactional
    public void executeonce(String jobId) throws SchedulerException, TaskException {
        Job job = jobMapper.getOne(jobId);
        ScheduleUtils.executeonceScheduler(scheduler, job);
    }


    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(Job job) throws SchedulerException, TaskException {
        job.setStatus(ScheduleConst.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }


    /**
     * 恢复任务
     *
     * @param jobId
     * @return
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public int start(String jobId) throws SchedulerException, TaskException {
        Job job = jobMapper.getOne(jobId);
        job.setStatus(ScheduleConst.Status.NORMAL.getValue());
        int rows = jobMapper.changeStatus(job);
        if (rows > 0) {
            ScheduleUtils.resumeSchedulerJob(scheduler, job);
        }
        return rows;
    }


    /**
     * 停止任务
     *
     * @param jobId
     * @return
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public int stop(String jobId) throws SchedulerException, TaskException {
        Job job = jobMapper.getOne(jobId);
        job.setStatus(ScheduleConst.Status.PAUSE.getValue());
        int rows = jobMapper.changeStatus(job);
        if (rows > 0) {
            ScheduleUtils.pauseSchedulerJob(scheduler, job);
        }
        return rows;
    }


    /**
     * 删除任务
     *
     * @param jobId
     * @return
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public int delete(String jobId) throws SchedulerException {
        Job job = jobMapper.getOne(jobId);
        int rows = jobMapper.deleteJobById(jobId);
        if (rows > 0) {
            ScheduleUtils.deleteSchedulerJob(scheduler, job);
        }
        return rows;
    }


    /**
     * 更新任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    @Override
    @Transactional
    public int updateJob(Job job) throws SchedulerException, TaskException {
        int rows = jobMapper.updateJob(job);
        if (rows > 0) {
            ScheduleUtils.updateSchedulerJob(scheduler, job);
        }
        return rows;
    }


}
