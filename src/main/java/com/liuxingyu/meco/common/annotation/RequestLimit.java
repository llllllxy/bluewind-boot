package com.liuxingyu.meco.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuxingyu01
 * @date 2021-03-23-10:39
 * @description one minutes request frequency is Fifty times, exceeding the wait five minutes
 * 一分钟的请求频率是五十次，超过了，则禁止访问300秒
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
    int time() default 60;

    int count() default 50;

    int waits() default 300;
}
