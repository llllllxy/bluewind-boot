package com.bluewind.boot.common.annotation;

import java.lang.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-03-23-10:39
 * @description one minutes request frequency is Fifty times, exceeding the wait five minutes
 * 使用方法：@RequestLimit(time = 60, count = 50, waits = 300)
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
