package com.liuxingyu.meco.common.annotation;

import com.liuxingyu.meco.common.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-03-10-10:41
 * @description 数据源切换注解
 * 使用方法 ： 加上@RoutingWith("slaveDataSource")注解即可切换到第二数据源
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceWith {
    /**
     * 切换数据源名称
     */
    public DataSourceType value() default DataSourceType.MASTER;
}
