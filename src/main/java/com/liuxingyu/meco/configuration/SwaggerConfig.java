package com.liuxingyu.meco.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2020-07-01-23:21
 * @description Swagger2配置类
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {


    @Bean(value = "defaultApi2")
    public Docket defaultApi2(Environment environment) {
        // 设置要显示swagger的环境(dev和test)
        Profiles of = Profiles.of("dev", "test");
        // 判断当前是否处于该环境
        // 通过 enable() 接收此参数判断是否要显示
        boolean enabled = environment.acceptsProfiles(of);

        // 配置请求头，即在header里加上token，前后端分离时能用到
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name("meco_token")
                .description("认证token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enabled) // 配置是否启用Swagger，如果是false，在浏览器将无法访问
                .globalOperationParameters(parameters)
                .select()
                // 扫描所有带有 @Api 注解的
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("meco-server接口文档") // 标题
                .contact(new Contact("liuxingyu01", "", "liuxingyu9725@foxmail.com")) // 作者
                .description("meco-server系统接口文档") // 简介
                .termsOfServiceUrl("无") // 服务条款url
                .version("1.0.0") // 版本
                .build();
    }

}
