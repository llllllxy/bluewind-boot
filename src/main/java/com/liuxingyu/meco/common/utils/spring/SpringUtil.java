package com.liuxingyu.meco.common.utils.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-07-1:35
 * @description Spring IOC容器中的bean对象获取
 * @demo final Demo2 testDemo = SpringUtil.getBean("testDemo");
 **/
@Component
public class SpringUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * "@PostConstruct"注解标记的类中，由于ApplicationContext还未加载，导致空指针<br>
     * 因此实现BeanFactoryPostProcessor注入ConfigurableListableBeanFactory实现bean的操作
     */
    private static ConfigurableListableBeanFactory beanFactory;
    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 获取{@link ApplicationContext}
     *
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取{@link ListableBeanFactory}，可能为{@link ConfigurableListableBeanFactory} 或 {@link ApplicationContextAware}
     *
     * @return {@link ListableBeanFactory}
     * @since 5.7.0
     */
    public static ListableBeanFactory getBeanFactory() {
        return null == beanFactory ? applicationContext : beanFactory;
    }


    /**
     * 通过name获取 Bean
     *
     * @param <T>  Bean类型
     * @param name Bean名称
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getBeanFactory().getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param <T>   Bean类型
     * @param clazz Bean类
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param <T>   bean类型
     * @param name  Bean名称
     * @param clazz bean类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getBeanFactory().getBean(name, clazz);
    }


    /**
     * 获取指定类型对应的所有Bean，包括子类
     *
     * @param <T>  Bean类型
     * @param type 类、接口，null表示获取所有bean
     * @return 类型对应的bean，key是bean注册的name，value是Bean
     * @since 5.3.3
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 获取指定类型对应的Bean名称，包括子类
     *
     * @param type 类、接口，null表示获取所有bean名称
     * @return bean名称
     * @since 5.3.3
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key 配置项key
     * @return 属性值
     * @since 5.3.3
     */
    public static String getProperty(String key) {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     * @since 5.3.3
     */
    public static String[] getActiveProfiles() {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     * @since 5.3.3
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        if (activeProfiles != null && activeProfiles.length != 0) {
            return activeProfiles[0];
        } else {
            return null;
        }
    }

    /**
     * 动态向Spring注册Bean
     * <p>
     * 由{@link org.springframework.beans.factory.BeanFactory} 实现，通过工具开放API
     *
     * @param <T>      Bean类型
     * @param beanName 名称
     * @param bean     bean
     * @author shadow
     * @since 5.4.2
     */
    public static <T> void registerBean(String beanName, T bean) {
        if (null != beanFactory) {
            beanFactory.registerSingleton(beanName, bean);
        } else if (applicationContext instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            context.getBeanFactory().registerSingleton(beanName, bean);
        }
    }


    /**
     * 判断bean是否单例
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isSingleton(name);
    }

}
