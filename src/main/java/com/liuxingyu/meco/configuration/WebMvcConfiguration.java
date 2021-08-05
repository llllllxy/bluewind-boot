package com.liuxingyu.meco.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author liuxingyu01
 * @date 2020-03-22-11:29
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    /**
     * 我们在注册拦截器之前，先将Interceptor手动进行注入
     * 解决拦截器中无法注入bean的问题
     *
     * @return
     */
    @Bean
    public ItfcInterceptor getItfcInterceptor() {
        return new ItfcInterceptor();
    }

    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * addPathPatterns(): 添加需要拦截的路径
     * excludePathPatterns(): 添加不需要拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getItfcInterceptor())
                .addPathPatterns("/itfc/**"); // 拦截itfc开头的url
        // .excludePathPatterns(list) // 不拦截
        // .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");// 配置swagger-ui不被拦截
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源不被拦截
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 配置swagger-ui不被拦截(knife4j)
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    /**
     * 处理CORS跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }


}


