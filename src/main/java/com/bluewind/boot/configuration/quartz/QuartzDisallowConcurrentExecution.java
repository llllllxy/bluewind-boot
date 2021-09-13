package com.bluewind.boot.configuration.quartz;

import com.bluewind.boot.sys.sysjob.entity.SysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;


/**
 * 定时任务处理（禁止并发执行）
 *
 * @author liuxingyu01
 * @date 2021-08-27-13:22
 * @description 在job接口实现类上添加@DisallowConcurrentExecution注解可以保证上一个任务执行完后，再去执行下一个任务，这里的任务是同一个任务
 *              note: 该时间段应该执行几个任务还是会执行几个任务，即使上一个任务执行完毕后已经超过该时间段
 **/
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
