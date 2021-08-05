package com.liuxingyu.meco.configuration.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.liuxingyu.meco.common.enums.DataSourceType;
import com.liuxingyu.meco.common.utils.encrypt.AESByHexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-10-8:28
 * @description MyBatis动态切数据源
 **/
@Configuration
public class DataSourceConfiguratioin {
    Logger logger = LoggerFactory.getLogger(DataSourceConfiguratioin.class);

    @Value("${spring.datasource.druid.master.url}")
    private String masterUrl;
    @Value("${spring.datasource.druid.master.username}")
    private String masterUsername;
    @Value("${spring.datasource.druid.master.password}")
    private String masterPassword;

    @Value("${spring.datasource.druid.slave.url}")
    private String slaveUrl;
    @Value("${spring.datasource.druid.slave.username}")
    private String slaveUsername;
    @Value("${spring.datasource.druid.slave.password}")
    private String slavePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.druid.filters}")
    private String filters;

    @Autowired
    private AESByHexUtils aesByHexUtils;

    /*** Master data source. */
    @Bean("masterDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource() {
        logger.info("DataSourceConfiguratioin create master datasource...");

        // 之前配合ConfigurationProperties使用的是自动配置，这里为了加密数据库密码，所以改为手动配置
        // return DruidDataSourceBuilder.create().build();

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(masterUrl);
        datasource.setUsername(masterUsername);
        // 对数据库密码进行解密
        masterPassword = aesByHexUtils.decrypt(masterPassword);
        datasource.setPassword(masterPassword);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("masterDataSource druid configuration initialization filter: {e}", e);
        }
        return datasource;
    }

    /*** Slave data source. */
    @Bean("slaveDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        logger.info("DataSourceConfiguratioin create slave datasource...");

        // 之前配合ConfigurationProperties使用的是自动配置，这里为了加密数据库密码，所以改为手动配置
        // return DruidDataSourceBuilder.create().build();

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(slaveUrl);
        datasource.setUsername(slaveUsername);
        // 对数据库密码进行解密
        slavePassword = aesByHexUtils.decrypt(slavePassword);
        datasource.setPassword(slavePassword);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("slaveDataSource druid configuration initialization filter: {e}", e);
        }
        return datasource;
    }


    @Bean("routingDataSource")
    @Primary
    public RoutingDataSource routingDataSource(@Autowired @Qualifier("masterDataSource") DataSource masterDataSource,
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
