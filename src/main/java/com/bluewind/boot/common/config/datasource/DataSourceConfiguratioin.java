package com.bluewind.boot.common.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.bluewind.boot.common.enums.DataSourceType;
import com.bluewind.boot.common.config.datasource.properties.DruidProperties;
import com.bluewind.boot.common.utils.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-10-8:28
 * @description Druid多数据源配置
 **/
@Configuration
public class DataSourceConfiguratioin {
    Logger logger = LoggerFactory.getLogger(DataSourceConfiguratioin.class);

    /**
     * 主数据源 Master datasource.
     *
     * @param druidProperties Druid 配置文件
     * @return 主数据源
     */
    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties) {
        logger.info("DataSourceConfiguratioin create master datasource...");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.initDruidDataSource(dataSource);
    }


    /**
     * 辅数据源 Slave datasource.
     *
     * @param druidProperties Druid 配置文件
     * @return 辅数据源
     */
    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        logger.info("DataSourceConfiguratioin create slave datasource...");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.initDruidDataSource(dataSource);
    }


    /**
     * Quartz定时任务专用数据源 Quartz datasource.
     *
     * @param druidProperties Druid 配置文件
     * @return Quartz定时任务专用数据源
     */
    @QuartzDataSource
    @Bean("quartzDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.quartz")
    public DataSource quartzDataSource(DruidProperties druidProperties) {
        logger.info("DataSourceConfiguratioin create quartz datasource...");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.initDruidDataSource(dataSource);
    }


    /**
     * 动态数据源入口
     *
     * @param masterDataSource 主数据源
     * @return {@link RoutingDataSource}
     */
    @Bean("dataSource")
    @Primary
    public RoutingDataSource dataSource(DataSource masterDataSource) {
        logger.info("DataSourceConfiguratioin create routing datasource...");
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
        RoutingDataSource routing = new RoutingDataSource();
        routing.setTargetDataSources(targetDataSources);
        routing.setDefaultTargetDataSource(masterDataSource);
        routing.afterPropertiesSet();
        return routing;
    }


    /**
     * 设置数据源
     *
     * @param targetDataSources 备选数据源集合
     * @param sourceName        数据源名称
     * @param beanName          bean名称
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = SpringUtil.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception e) {
            logger.info("DataSourceConfiguratioin slave datasource not enabled...");
        }
    }

}
