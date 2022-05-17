package com.bluewind.boot.common.aspect;

import com.bluewind.boot.common.annotation.LogAround;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author liuxingyu01
 * @date 2021-01-25-14:15
 * @description aop切面，用于输出方法的进入和结束日志
 **/
@Component
@Aspect
public class LogAroundAspect {

    @Around(value = "anyMethod() && @annotation(logAround)")
    public Object logAround(ProceedingJoinPoint jp, LogAround logAround) throws Throwable {

        Logger log = LoggerFactory.getLogger(jp.getTarget().getClass());
        String methodName = jp.getSignature().getName();
        String value = logAround.value();

        if (log.isInfoEnabled()) {
            log.info("进入方法：" + value + "----" + methodName);
        }

        long start = System.currentTimeMillis();

        Object o = jp.proceed();

        long end = System.currentTimeMillis();

        if (log.isInfoEnabled()) {
            log.info("方法结束：" + value + "----" + methodName + ",用时：" + (end - start) + "毫秒");
        }

        return o;
    }

    /**
     * 第一个*表示要拦截的目标方法返回值任意(也可以明确指定返回值类型)
     * 第二个*表示包中或者子包的任意类(也可以明确指定类)
     * 第三个*表示类中的任意方法
     * 最后面的两个点表示方法参数任意，个数任意，类型任意
     */
    @Pointcut("execution(* com.bluewind.boot..*.*(..))")
    public void anyMethod() {

    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {

    }

}
