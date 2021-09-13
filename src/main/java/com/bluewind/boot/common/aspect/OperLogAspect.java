package com.bluewind.boot.common.aspect;

import com.bluewind.boot.common.utils.IPUtils;
import com.bluewind.boot.common.configuration.security.SecurityUtil;
import com.bluewind.boot.module.sys.sysoperlog.entity.SysOperLog;
import com.bluewind.boot.module.sys.sysoperlog.service.SysOperLogService;
import com.bluewind.boot.common.annotation.OperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author liuxingyu01
 * @date 2021-03-05-13:23
 * @description 操作日志切面处理类，操作日志异常日志记录处理
 **/
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    SysOperLogService sysOperLogService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Around("@annotation(com.bluewind.boot.common.annotation.OperLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        SysOperLog sysOperLog = new SysOperLog();
        long start = System.currentTimeMillis();

        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = methodSignature.getMethod();
        // 获取方法上的注解
        OperLog opLog = method.getAnnotation(OperLog.class);

        if (opLog != null) {
            String operModul = opLog.operModul();
            String operType = opLog.operType();
            String operDesc = opLog.operDesc();
            sysOperLog.setModel(operModul);
            sysOperLog.setType(operType);
            sysOperLog.setDescript(operDesc);
        }

        // 获取请求的类名（com.liuxingyu.industry.sys.sysbasedict.controller.SysBaseDictController）
        String className = joinPoint.getTarget().getClass().getName();

        // 获取请求的方法名（getSysDictList）
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 请求方法
        sysOperLog.setMethod(methodName);
        // 请求Uri
        sysOperLog.setUrl(request.getRequestURI()); // 请求Uri
        // 请求ip
        sysOperLog.setIp(IPUtils.getIpAddress(request));
        // 操作人
        sysOperLog.setCreateUser(SecurityUtil.getSysUserId());

        Object o = joinPoint.proceed();

        long end = System.currentTimeMillis();
        // 获取方法从开始到结束的时长
        sysOperLog.setSpendTime((int) (end - start));
        // 插入sys_oper_log表
        sysOperLogService.saveOperLog(sysOperLog);

        return o;
    }

}
