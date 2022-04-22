package com.bluewind.boot.common.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-11-10-13:48
 * @description 动态获取Spring Bean工具类
 * @demo1
 *          private static OrganToolMapper organToolMapper;
 *          private static OrganToolMapper getOrganToolService() {
 *             if (organToolMapper == null) {
 *                Object bean = SpringContextUtil.getBean("organToolMapper");
 *                if (bean == null) {
 *                    log.error("organToolMapper bean is null!");
 *                }
 *                organToolMapper = (OrganToolMapper) bean;
 *            }
 *            return organToolMapper;
 *         }
 * @demo2
 *      private static ConvertUtilsService ConvertUtilsService; // ConvertUtilsService是接口层
 *      private static ConvertUtilsService getConvertUtilsServiceBean() {
 *         if (ConvertUtilsService == null) {
 *             ConvertUtilsService bean = SpringContextUtil.getBean(ConvertUtilsService.class);
 *             if (bean == null) {
 *                 logger.error("ConvertUtilsService bean is null");
 *             }
 *             ConvertUtilsService = bean;
 *             return ConvertUtilsService;
 *         }
 *         return ConvertUtilsService;
 *      }
 **/
@Component
@Deprecated
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public SpringContextUtil() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 通过名称获取Bean
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }


    /**
     * 根据class类型获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        T t = null;
        Map<String, T> map = getApplicationContext().getBeansOfType(clazz);
        for (Map.Entry<String, T> entry : map.entrySet()) {
            t = entry.getValue();
        }
        return t;
    }

    /**
     * 根据bean名称获取指定类型bean
     * @param name bean名称
     * @param clazz 返回的bean类型,若类型不匹配,将抛出异常
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


    public static Object getBean(String beanName, String className) throws ClassNotFoundException {
        Class clz = Class.forName(className);
        return getApplicationContext().getBean(beanName, clz.getClass());
    }

    /**
     * 判断是否包含bean
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 判断bean是否单例
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().isSingleton(name);
    }

    /**
     * 获取bean的类型
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getType(name);
    }


    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getAliases(name);
    }

}
