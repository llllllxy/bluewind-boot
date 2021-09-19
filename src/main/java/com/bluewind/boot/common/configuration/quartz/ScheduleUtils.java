package com.bluewind.boot.common.configuration.quartz;

import com.bluewind.boot.common.consts.ScheduleConstants;
import com.bluewind.boot.common.exception.TaskException;
import com.bluewind.boot.module.sys.sysjob.entity.SysJob;
import org.quartz.*;

/**
 * @author liuxingyu01
 * @date 2021-08-27-13:25
 * @description 定时任务工具类
 **/
public class ScheduleUtils {

    /**
     * 得到quartz任务类
     *
     * @param sysJob 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
        boolean isConcurrent = "0".equals(sysJob.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(String jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(String jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // 构建job信息
        String jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup)).withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }


    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }


    /**
     * 执行一次
     */
    public static void executeonceScheduler(Scheduler scheduler, SysJob sysJob) throws SchedulerException, TaskException {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleConstants.TASK_PROPERTIES, sysJob);
            JobKey jobKey = getJobKey(sysJob.getJobId(), sysJob.getJobGroup());
            // 如果不存在，则新建一个quartz实例
            if (!scheduler.checkExists(jobKey)) {
                createScheduleJob(scheduler, sysJob);
                scheduler.triggerJob(jobKey, dataMap);
            } else {
                scheduler.triggerJob(jobKey, dataMap);
            }
        } catch (SchedulerException e) {
            throw new SchedulerException("执行一次定时任务失败", e);
        }
    }


    /**
     * 删除定时任务
     */
    public static void deleteSchedulerJob(Scheduler scheduler, SysJob sysJob) throws SchedulerException {
        try {
            JobKey jobKey = getJobKey(sysJob.getJobId(), sysJob.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                return;
            }
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("删除定时任务失败", e);
        }
    }


    /**
     * 暂停任务
     */
    public static void pauseSchedulerJob(Scheduler scheduler, SysJob sysJob) throws SchedulerException, TaskException {
        try {
            JobKey jobKey = getJobKey(sysJob.getJobId(), sysJob.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                // 任务之前不存在，则新建，新建的同时，createQuartzTask方法内会暂停任务，所以这里无需再次操作
                createScheduleJob(scheduler, sysJob);
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("暂停定时任务失败", e);
        }
    }


    /**
     * 恢复任务
     */
    public static void resumeSchedulerJob(Scheduler scheduler, SysJob sysJob) throws SchedulerException, TaskException {
        try {
            JobKey jobKey = getJobKey(sysJob.getJobId(), sysJob.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                // 任务之前不存在，则新建，新建的同时，createQuartzTask方法内会自动开始任务，所以这里无需再次操作
                createScheduleJob(scheduler, sysJob);
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("恢复定时任务失败", e);
        }
    }


    /**
     * 更新任务（先删除，再新增）
     *
     * @param job       任务对象
     * @param scheduler 任务调度器
     */
    public static void updateSchedulerJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
        // 判断是否存在
        JobKey jobKey = getJobKey(job.getJobId(), job.getJobGroup());
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题，所以先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        createScheduleJob(scheduler, job);
    }

}
