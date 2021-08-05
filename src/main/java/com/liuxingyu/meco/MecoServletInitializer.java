package com.liuxingyu.meco;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * @author liuxingyu01
 * @date 2021-03-15-18:10
 * @description 用于web容器中进行部署（打成war包部署）
 **/
public class MecoServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MecoApplication.class);
    }

}
