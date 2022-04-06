package com.bluewind.boot.common.config.quartz;

import com.bluewind.boot.common.consts.ScheduleConst;
import com.bluewind.boot.common.utils.ExceptionUtil;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.spring.SpringUtil;
import com.bluewind.boot.module.system.job.entity.Job;
import com.bluewind.boot.module.system.joblog.entity.JobLog;
import com.bluewind.boot.module.system.joblog.service.JobLogService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author liuxingyu01
 * @date 2021-08-27-12:45
 **/
public abstract class AbstractQuartzJob implements org.quartz.Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Job job = new Job();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConst.TASK_PROPERTIES), job);
        try {
            before(context, job);
            if (job != null) {
                doExecute(context, job);
            }
            after(context, job, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, job, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param job  系统计划任务
     */
    protected void before(JobExecutionContext context, Job job) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param job  系统计划任务
     */
    protected void after(JobExecutionContext context, Job job, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final JobLog jobLog = new JobLog();
        jobLog.setJobLogId(IdGenerate.nextId());
        jobLog.setJobId(job.getJobId());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        jobLog.setStartTime(startTime);
        jobLog.setStopTime(new Date());
        long runMs = jobLog.getStopTime().getTime() - jobLog.getStartTime().getTime();
        jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLog.setStatus("1"); // 失败

            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus("0"); // 成功
        }

        // 写入数据库当中
        SpringUtil.getBean(JobLogService.class).addJobLog(jobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param job  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, Job job) throws Exception;
}
