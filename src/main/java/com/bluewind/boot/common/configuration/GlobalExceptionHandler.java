package com.bluewind.boot.common.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author liuxingyu01
 * @date 2021-03-05-15:57
 * @description 统一异常处理
 **/
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 运行时异常
    @ExceptionHandler(value = RuntimeException.class)
    public String handleRuntimeException(RuntimeException e) {
        logger.error("GlobalExceptionHandler -- RuntimeException = {e}", e);
        return "error/500";
    }
//
//
//    // 未登陆异常
//    @ExceptionHandler(value = AuthenticationException.class)
//    public String handleAuthenticationException() {
//        return "login/login";
//    }


}
