package com.liuxingyu.meco.timer.job;

import com.liuxingyu.meco.common.annotation.QuartzLogSign;
import com.liuxingyu.meco.common.utils.DateTool;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * @author liuxingyu01
 * @date 2021-01-17-23:00
 * @description 测试定时器
 **/
public class TestJob implements Job {

    @QuartzLogSign("1")
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String param = dataMap.getString("param");
        System.out.println("定时任务启动：" + DateTool.getCurrentTime("yyyy-MM-dd HH:mm:ss") + " 参数为：" + param);
    }
}
