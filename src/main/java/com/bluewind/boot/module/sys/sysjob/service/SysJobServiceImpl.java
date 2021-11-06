package com.bluewind.boot.module.sys.sysjob.service;

import com.bluewind.boot.common.consts.ScheduleConst;
import com.bluewind.boot.common.exception.TaskException;
import com.bluewind.boot.common.config.quartz.ScheduleUtils;
import com.bluewind.boot.module.sys.sysjob.entity.SysJob;
import com.bluewind.boot.module.sys.sysjob.mapper.SysJobMapper;
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
public class SysJobServiceImpl implements SysJobService {
    final static Logger logger = LoggerFactory.getLogger(SysJobServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper sysJobMapper;

    /**
     * 查询定时任务列表
     *
     * @param paraMap
     * @return
     */
    @Override
    public List<SysJob> list(Map<String, String> paraMap) {
        return sysJobMapper.list(paraMap);
    }


    /**
     * 获取一个定时任务实例
     *
     * @param jobId
     * @return
     */
    @Override
    public SysJob getOne(String jobId) {
        return sysJobMapper.getOne(jobId);
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
        SysJob sysJob = sysJobMapper.getOne(jobId);
        ScheduleUtils.executeonceScheduler(scheduler, sysJob);
    }


    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(SysJob job) throws SchedulerException, TaskException {
        job.setStatus(ScheduleConst.Status.PAUSE.getValue());
        int rows = sysJobMapper.insertJob(job);
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
        SysJob sysJob = sysJobMapper.getOne(jobId);
        sysJob.setStatus(ScheduleConst.Status.NORMAL.getValue());
        int rows = sysJobMapper.changeStatus(sysJob);
        if (rows > 0) {
            ScheduleUtils.resumeSchedulerJob(scheduler, sysJob);
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
        SysJob sysJob = sysJobMapper.getOne(jobId);
        sysJob.setStatus(ScheduleConst.Status.PAUSE.getValue());
        int rows = sysJobMapper.changeStatus(sysJob);
        if (rows > 0) {
            ScheduleUtils.pauseSchedulerJob(scheduler, sysJob);
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
        SysJob sysJob = sysJobMapper.getOne(jobId);
        int rows = sysJobMapper.deleteJobById(jobId);
        if (rows > 0) {
            ScheduleUtils.deleteSchedulerJob(scheduler, sysJob);
        }
        return rows;
    }


    /**
     * 更新任务
     *
     * @param sysJob
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    @Override
    @Transactional
    public int updateJob(SysJob sysJob) throws SchedulerException, TaskException {
        int rows = sysJobMapper.updateJob(sysJob);
        if (rows > 0) {
            ScheduleUtils.updateSchedulerJob(scheduler, sysJob);
        }
        return rows;
    }


}
