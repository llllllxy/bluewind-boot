package com.bluewind.boot.common.annotation;

import com.bluewind.boot.common.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-03-10-10:41
 * @description 数据源切换注解
 * 使用方法 ： 加上@DataSourceWith(DataSourceType.SLAVE)注解即可切换到第二数据源
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceWith {

    /**
     * 切换数据源名称
     */
    DataSourceType value() default DataSourceType.MASTER;
}
