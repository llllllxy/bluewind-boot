package com.liuxingyu.meco.sys.sysjob.service;

import com.liuxingyu.meco.common.consts.ScheduleConstants;
import com.liuxingyu.meco.common.exception.TaskException;
import com.liuxingyu.meco.configuration.quartz.ScheduleUtils;
import com.liuxingyu.meco.sys.sysjob.entity.SysJob;
import com.liuxingyu.meco.sys.sysjob.mapper.SysJobMapper;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
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
    public void executeonce(String jobId) throws SchedulerException {
        SysJob sysJob = sysJobMapper.getOne(jobId);

        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, sysJob);

        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, sysJob.getJobGroup()), dataMap);
    }


    /**
     * 新增任务
     *
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(SysJob job) throws SchedulerException, TaskException {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
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
    public int start(String jobId) throws SchedulerException {
        SysJob sysJob = sysJobMapper.getOne(jobId);
        sysJob.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = sysJobMapper.changeStatus(sysJob);
        if (rows > 0) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, sysJob.getJobGroup()));
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
    public int stop(String jobId) throws SchedulerException {
        SysJob sysJob = sysJobMapper.getOne(jobId);
        sysJob.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = sysJobMapper.changeStatus(sysJob);
        if (rows > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, sysJob.getJobGroup()));
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
        SysJob job = sysJobMapper.getOne(jobId);
        String jobGroup = job.getJobGroup();
        int rows = sysJobMapper.deleteJobById(jobId);
        if (rows > 0) {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }


    /**
     * 更新任务
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
            updateSchedulerJob(sysJob, sysJob.getJobGroup());
        }
        return rows;
    }


    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
        String jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }


}
