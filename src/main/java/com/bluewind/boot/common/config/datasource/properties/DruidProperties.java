package com.bluewind.boot.common.config.datasource.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @author liuxingyu01
 * @date 2021-08-21-14:40
 * @description Druid 配置读取
 **/
@Configuration
public class DruidProperties {
    final static Logger logger = LoggerFactory.getLogger(DruidProperties.class);

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

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

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

    public DruidDataSource initDruidDataSource(DruidDataSource dataSource) {
        try {
            /*
             * 配置连接池初始化大小、最小连接数、最大连接数
             */
            dataSource.setInitialSize(initialSize);
            dataSource.setMaxActive(maxActive);
            dataSource.setMinIdle(minIdle);

            /*
             * 获取连接时最大等待时间,单位毫秒.
             * 配置了maxWait之后,缺省启用公平锁,并发效率会有所下降.如果需要可以通过配置useUnfairLock属性为true使用非公平锁
             */
            dataSource.setMaxWait(maxWait);

            /*
             * 配置间隔多久才进行一次检测, 检测需要关闭的空闲连接, 单位是毫秒
             */
            dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

            /*
             * 配置一个连接在池中最小、最大生存的时间, 单位是毫秒
             */
            dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

            /*
             * 用来检测连接是否有效的sql, 要求是一个查询语句, 常用select 'x'
             * 如果validationQuery为null, testOnBorrow、testOnReturn、testWhileIdle 都不会起作用
             */
            dataSource.setValidationQuery(validationQuery);
            /*
             * 建议配置为true, 不影响性能, 并且保证安全性
             * 申请连接的时候检测, 如果空闲时间大于 timeBetweenEvictionRunsMillis, 执行validationQuery检测连接是否有效
             */
            dataSource.setTestWhileIdle(testWhileIdle);
            /*
             * 申请连接时执行 validationQuery 检测连接是否有效, 做了这个配置会降低性能
             */
            dataSource.setTestOnBorrow(testOnBorrow);
            /*
             * 归还连接时执行 validationQuery 检测连接是否有效, 做了这个配置会降低性能
             */
            dataSource.setTestOnReturn(testOnReturn);


            dataSource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("DruidProperties initDruidDataSource SQLException: {e}", e);
        }

        return dataSource;
    }

}
