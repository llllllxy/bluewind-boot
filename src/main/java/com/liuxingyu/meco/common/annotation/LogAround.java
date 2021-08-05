package com.liuxingyu.meco.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuxingyu01
 * @date 2021-01-25-14:13
 * @Description: 日志注解：用于输出方法的进入和结束日志
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAround {
    String value() default "";
}
