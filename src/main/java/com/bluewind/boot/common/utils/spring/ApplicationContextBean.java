package com.bluewind.boot.common.utils.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author liuxingyu01
 * @date 2020-11-10-13:48
 * @description 打印出Spring容器所有的Bean名称
 **/
@Component
public class ApplicationContextBean implements ApplicationContextAware, InitializingBean {
    final static Logger logger = LoggerFactory.getLogger(ApplicationContextBean.class);
    public static ApplicationContext applicationContext;

    /**
     * 获取 ApplicationContext
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextBean.applicationContext = applicationContext;
    }

    /**
     * 打印IOC容器中所有的Bean名称
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
//        String[] names = applicationContext.getBeanDefinitionNames();
//        for (String name : names) {
//            logger.info("ApplicationContextBean >>>>>> " + name);
//        }
        logger.info("ApplicationContextBean -- Bean 总计:" + applicationContext.getBeanDefinitionCount());
    }

}
