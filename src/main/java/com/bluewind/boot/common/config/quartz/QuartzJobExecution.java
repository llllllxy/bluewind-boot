package com.bluewind.boot.common.config.quartz;

import com.bluewind.boot.module.system.job.entity.Job;
import org.quartz.JobExecutionContext;


/**
 * @author liuxingyu01
 * @date 2021-08-27-13:23
 **/
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, Job job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
