package com.liuxingyu.meco.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuxingyu01
 * @date 2021-03-11-19:51
 * @description 自定义注解，用于记录定时器执行日志
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QuartzLogSign {
    String value() default "";
}
