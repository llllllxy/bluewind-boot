package com.liuxingyu.meco.configuration;

import com.liuxingyu.meco.configuration.security.AuthenticeInterceptor;
import com.liuxingyu.meco.configuration.security.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liuxingyu01
 * @date 2020-03-22-11:29
 * @description: WebMvc配置
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    /**
     * 我们在注册拦截器之前，先将Interceptor手动进行注入
     * 解决拦截器中无法注入bean的问题
     *
     * @return ItfcInterceptor
     */
    @Bean
    public ItfcInterceptor getItfcInterceptor() {
        return new ItfcInterceptor();
    }

    @Bean
    public AuthenticeInterceptor getAuthenticeInterceptor() {
        return new AuthenticeInterceptor();
    }

    @Bean
    public PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }


    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * addPathPatterns(): 添加需要拦截的路径
     * excludePathPatterns(): 添加不需要拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/admin/login"); // 登陆页面
        list.add("/admin/doLogin"); // 登录接口
        list.add("/kaptcha/**");  // 开放获取验证码接口
        list.add("/anon/**");  // 开放itfc和anon接口（itfc和anon开头的可免认证访问）
        list.add("/itfc/**");  // 开放itfc和anon接口（itfc和anon开头的可免认证访问）
        list.add("/static/**");//静态资源不拦截

        // 开放静态文件
        list.add("/css/**");
        list.add("/images/**");
        list.add("/js/**");
        list.add("/lib/**");
        list.add("/api/**");

        // 注册会话认证拦截器
        registry.addInterceptor(getAuthenticeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(list)
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html", "/doc.html", "/service-worker.js");// 开放接口文档(swagger-ui)

        // 注册权限拦截器
        registry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/**");


        // 注册itfc服务拦截器
        registry.addInterceptor(getItfcInterceptor())
                .addPathPatterns("/itfc/**"); // 拦截itfc开头的url
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


