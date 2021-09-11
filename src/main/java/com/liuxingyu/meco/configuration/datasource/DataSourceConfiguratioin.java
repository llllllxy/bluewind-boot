package com.liuxingyu.meco.configuration.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.liuxingyu.meco.common.enums.DataSourceType;
import com.liuxingyu.meco.configuration.datasource.properties.DruidProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
     * 主数据源 Master data source.
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
     * 辅数据源 Slave data source.
     *
     * @param druidProperties Druid 配置文件
     * @return 辅数据源
     */
    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        logger.info("DataSourceConfiguratioin create slave datasource...");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.initDruidDataSource(dataSource);
    }


    /**
     * Quartz定时任务专用数据源 Quartz data source.
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
     * @param slaveDataSource  辅数据源
     * @return {@link RoutingDataSource}
     */
    @Bean("dataSource")
    @Primary
    public RoutingDataSource dataSource(@Autowired @Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Autowired @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        logger.info("DataSourceConfiguratioin create routing datasource...");
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
        RoutingDataSource routing = new RoutingDataSource();
        routing.setTargetDataSources(targetDataSources);
        routing.setDefaultTargetDataSource(masterDataSource);
        routing.afterPropertiesSet();
        return routing;
    }

}
