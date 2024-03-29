package com.bluewind.boot.common.config;

import com.bluewind.boot.common.config.itfc.ItfcInterceptor;
import com.bluewind.boot.common.config.security.AuthenticeInterceptor;
import com.bluewind.boot.common.config.security.PermissionInterceptor;
import com.bluewind.boot.common.config.security.RoleInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
     * 在注册拦截器之前，先将Interceptor手动进行注入
     * 解决拦截器中无法注入bean的问题
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

    @Bean
    public RoleInterceptor getRoleInterceptor() {
        return new RoleInterceptor();
    }



    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * addPathPatterns(): 添加需要拦截的路径
     * excludePathPatterns(): 添加不需要拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathList = new ArrayList<>();
        excludePathList.add("/admin/login"); // 登陆页面
        excludePathList.add("/admin/register"); // 注册页面
        excludePathList.add("/admin/doLogin"); // 登录接口
        excludePathList.add("/admin/doRegister"); // 注册接口
        excludePathList.add("/admin/sendEmail"); // 注册获取邮箱验证码的接口

        excludePathList.add("/captcha/**"); // 开放获取行为验验证码接口
        excludePathList.add("/kaptcha/**");  // 开放获取文字验证码接口

        excludePathList.add("/anon/**");  // 开放itfc和anon接口（itfc和anon开头的可免认证访问）
        excludePathList.add("/itfc/**");  // 开放itfc和anon接口（itfc和anon开头的可免认证访问）

        excludePathList.add("/static/**");//静态资源不拦截

        // 开放静态文件
        excludePathList.add("/css/**");
        excludePathList.add("/images/**");
        excludePathList.add("/js/**");
        excludePathList.add("/lib/**");
        excludePathList.add("/api/**");

        // 注册会话拦截器
        registry.addInterceptor(getAuthenticeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathList)
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html", "/doc.html", "/service-worker.js");// 开放接口文档(swagger-ui)

        // 注册权限拦截器
        registry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathList);

        // 注册角色拦截器
        registry.addInterceptor(getRoleInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathList);

        // 注册itfc三方服务拦截器，只拦截itfc开头的url
        registry.addInterceptor(getItfcInterceptor())
                .addPathPatterns("/itfc/**");
    }


    /**
     * 配置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源不被拦截
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 配置swagger-ui不被拦截(knife4j)
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}


