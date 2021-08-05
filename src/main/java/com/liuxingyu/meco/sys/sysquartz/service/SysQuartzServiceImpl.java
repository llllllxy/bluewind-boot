package com.liuxingyu.meco.sys.sysquartz.service;

import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.sys.sysquartz.entity.SysQuartz;
import com.liuxingyu.meco.sys.sysquartz.mapper.SysQuartzMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-17-22:38
 **/
@Service
public class SysQuartzServiceImpl implements SysQuartzService {

    final static Logger log = LoggerFactory.getLogger(SysQuartzServiceImpl.class);

    @Autowired
    SysQuartzMapper sysQuartzMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 获取需要默认执行的定时任务
     * @param sysQuartz
     * @return
     */
    @Override
    public List<SysQuartz> getDefaultList(SysQuartz sysQuartz) {
        return sysQuartzMapper.getDefaultList(sysQuartz);
    }


    /**
     * 启动定时任务
     *
     * @param className
     * @param cronExpression
     * @param param
     */
    @Override
    public BaseResult schedulerAdd(String className, String cronExpression, String param) {
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(className).getClass()).withIdentity(className).usingJobData("param", param).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return BaseResult.success();
        } catch (Exception e) {
            log.error("SysQuartzServiceImpl -- schedulerAdd -- Exception = {e}", e);
        }
        return BaseResult.failure("定时任务创建失败，请检查任务类名");
    }


    /**
     * 停止定时任务
     *
     * @param className
     */
    @Override
    public BaseResult schedulerDelete(String className) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(className));
            scheduler.unscheduleJob(TriggerKey.triggerKey(className));
            scheduler.deleteJob(JobKey.jobKey(className));
            return BaseResult.success();
        } catch (Exception e) {
            log.error("SysQuartzServiceImpl -- schedulerDelete -- Exception = {e}", e);
        }
        return BaseResult.failure("定时任务删除失败，请检查任务类名");
    }


    /**
     * 获取定时任务分页列表
     *
     * @param map
     * @return
     */
    @Override
    public List<SysQuartz> list(Map map) {
        return sysQuartzMapper.list(map);
    }


    /**
     * 启动定时任务(查询列表用的)
     *
     * @param id
     * @return
     */
    @Override
    public int startJob(Integer id) {
        SysQuartz sysQuartz = sysQuartzMapper.getOneQuqrtz(id);
        String className = sysQuartz.getClassName();
        String cronExpression = sysQuartz.getCronExpression();
        String param = sysQuartz.getParam();
        try {
            // 先删除定时任务
            scheduler.pauseTrigger(TriggerKey.triggerKey(className));
            scheduler.unscheduleJob(TriggerKey.triggerKey(className));
            scheduler.deleteJob(JobKey.jobKey(className));

            // 再启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(className).getClass()).withIdentity(className).usingJobData("param", param).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return sysQuartzMapper.startQuqrtzStatus(id);
        } catch (Exception e) {
            log.error("SysQuartzServiceImpl -- startJob -- Exception = {e}", e);
        }

        return 0;
    }

    /**
     * 停止定时任务(查询列表用的)
     *
     * @param id
     * @return
     */
    @Override
    public int stopJob(Integer id) {
        SysQuartz sysQuartz = sysQuartzMapper.getOneQuqrtz(id);
        String className = sysQuartz.getClassName();
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(className));
            scheduler.unscheduleJob(TriggerKey.triggerKey(className));
            scheduler.deleteJob(JobKey.jobKey(className));
            // 更新sys_quartz值为1
            return sysQuartzMapper.stopQuqrtzStatus(id);
        } catch (Exception e) {
            log.error("SysQuartzServiceImpl -- stopJob -- Exception = {e}", e);
        }
        return 0;
    }


    /**
     * 任务新增
     *
     * @param sysQuartz
     * @return
     */
    @Override
    public int addOneQuartz(SysQuartz sysQuartz) {
        return sysQuartzMapper.addOneQuartz(sysQuartz);
    }


    /**
     * 任务删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteOneById(Integer id) {
        return sysQuartzMapper.deleteOneById(id);
    }


    /**
     * 获取一个定时任务
     *
     * @param id
     * @return
     */
    @Override
    public SysQuartz getOneQuqrtz(Integer id) {
        return sysQuartzMapper.getOneQuqrtz(id);
    }


    /**
     * 任务修改
     *
     * @param sysQuartz
     * @return
     */
    @Override
    public int updateOneQuartz(SysQuartz sysQuartz) {
        return sysQuartzMapper.updateOneQuartz(sysQuartz);
    }

    private static Job getClass(String className) throws Exception {
        Class<?> class1 = Class.forName(className);
        return (Job) class1.newInstance();
    }
}
