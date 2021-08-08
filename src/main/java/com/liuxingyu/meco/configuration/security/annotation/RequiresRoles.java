package com.liuxingyu.meco.configuration.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuxingyu01
 * @date 2021-08-08-12:02
 * @description: 角色注解
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {
    String value() default "";
}
