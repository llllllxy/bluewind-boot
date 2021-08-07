package com.liuxingyu.meco.configuration;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Title GlobalExceptionHandler.java
 * @description 用于解决shiroFilterFactoryBean.setUnauthorizedUrl(" / 403 ");不生效的问题
 * @time 2021-03-05-15:57
 * @author liuxingyu01
 **/


/**
 * @author liuxingyu01
 * @date 2021-03-05-15:57
 * @description 统一异常处理
 **/
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

//    // 缺少权限异常
//    @ExceptionHandler(value = AuthorizationException.class)
//    public String handleAuthorizationException() {
//        return "error/403";
//    }
//
//
//    // 未登陆异常
//    @ExceptionHandler(value = AuthenticationException.class)
//    public String handleAuthenticationException() {
//        return "login/login";
//    }


}
