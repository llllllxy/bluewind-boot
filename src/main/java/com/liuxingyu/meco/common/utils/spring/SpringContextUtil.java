package com.liuxingyu.meco.common.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author liuxingyu01
 * @date 2020-11-10-13:48
 * @description 动态获取Spring Bean工具类
 **/
@Component
@Deprecated
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public SpringContextUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (applicationContext == null) {
            applicationContext = arg0;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static void setAppCtx(ApplicationContext webAppCtx) {
        if (webAppCtx != null) {
            applicationContext = webAppCtx;
        }
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }


    public static <T> T getBean(String name, Class<T> clazz) throws ClassNotFoundException {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过beanName获取Bean
     *
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }


    public static Object getBean(String beanName, String className) throws ClassNotFoundException {
        Class clz = Class.forName(className);
        return getApplicationContext().getBean(beanName, clz.getClass());
    }


    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }


    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().isSingleton(name);
    }


    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getType(name);
    }


    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getAliases(name);
    }

}
