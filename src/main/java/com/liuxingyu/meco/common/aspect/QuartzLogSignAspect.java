package com.liuxingyu.meco.common.aspect;

import com.liuxingyu.meco.common.annotation.QuartzLogSign;
import com.liuxingyu.meco.common.utils.DateTool;
import com.liuxingyu.meco.sys.sysquartz.entity.SysQuartz;
import com.liuxingyu.meco.sys.sysquartz.mapper.SysQuartzMapper;
import com.liuxingyu.meco.sys.sysquartzlog.entity.SysQuartzLog;
import com.liuxingyu.meco.sys.sysquartzlog.mapper.SysQuartzLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author liuxingyu01
 * @date 2021-03-11-19:52
 * @description 切面，用于记录定时器执行日志
 **/
@Aspect
@Component
public class QuartzLogSignAspect {

    @Autowired
    SysQuartzLogMapper sysQuartzLogMapper;

    @Autowired
    SysQuartzMapper sysQuartzMapper;


    @Around("@annotation(quartzLogSign)")
    public Object signQuartzLog(ProceedingJoinPoint joinPoint, QuartzLogSign quartzLogSign) throws Throwable {
        SysQuartzLog sysQuartzLog = new SysQuartzLog();
        Integer id = Integer.parseInt(quartzLogSign.value());
        String exception = "";
        Integer status = 0;

        SysQuartz sysQuartz = sysQuartzMapper.getOneQuqrtz(id);
        sysQuartzLog.setName(sysQuartz.getName());
        sysQuartzLog.setQuartzId(sysQuartz.getId());
        sysQuartzLog.setStartTime(DateTool.getCurrentTime());

        Object o = null;
        try {
            o = joinPoint.proceed();
        } catch (Throwable e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            exception = stringWriter.toString();
            // 去除字符串中的回车、换行符
            exception = exception.replaceAll("\\r|\\n", "");
            // 去除字符串中的单引号
            exception = exception.replaceAll("'", "");
            // 将字符串间的多个空格缩略为1个空格
            exception = exception.replaceAll("\\s+", " ");
            // 执行失败
            status = 1;
        }

        sysQuartzLog.setErrorLog(exception);
        sysQuartzLog.setEndTime(DateTool.getCurrentTime());
        sysQuartzLog.setStatus(status);
        sysQuartzLogMapper.insertQuartzLog(sysQuartzLog);

        return o;
    }

}
