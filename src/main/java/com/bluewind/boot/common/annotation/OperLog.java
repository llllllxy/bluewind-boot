package com.bluewind.boot.common.annotation;

import java.lang.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-03-05-13:20
 * @description 系统操作日志的注解
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
    String operModul() default ""; // 操作模块

    String operType() default "";  // 操作类型

    String operDesc() default "";  // 操作说明
}
