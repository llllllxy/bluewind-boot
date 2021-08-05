package com.liuxingyu.meco.configuration.errordeal;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author liuxingyu01
 * @date 2020-11-12-19:53
 * @description 自定义错误页面配置磊
 **/
@Configuration
public class ErrorDealConfig {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/error404");
            ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, "/error/error403");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/error500");
            factory.addErrorPages(errorPage403, errorPage404, errorPage500);
        };
    }
}
