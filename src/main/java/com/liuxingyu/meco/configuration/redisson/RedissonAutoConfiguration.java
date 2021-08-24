package com.liuxingyu.meco.configuration.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author liuxingyu01
 * @date 2021-08-23-13:30
 * @description redisson配置类
 **/
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redssionProperties;

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean("redissonSingle")
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        // useSingleServer 单机模式
        // useClusterServers 集群模式
        // useMasterSlaveServers 主从模式
        // useSentinelServers 哨兵模式
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }


    /**
     * 哨兵模式自动装配
     * @return
     */
    @Bean("redissonSentinel")
    @ConditionalOnProperty(name="redisson.master-name")
    RedissonClient redissonSentinel() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(redssionProperties.getSentinelAddresses())
                .setMasterName(redssionProperties.getMasterName())
                .setTimeout(redssionProperties.getTimeout())
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }



    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     *
     * @return
     */
    @Bean
    RedissLockUtil redissLockUtil(@Qualifier("redissonSingle") RedissonClient redissonClient) {
        RedissLockUtil redissLockUtil = new RedissLockUtil();
        redissLockUtil.setRedissonClient(redissonClient);
        return redissLockUtil;
    }


    /**
     * 可以直接使用RedissLockUtil工具类，也可以自己在业务层注入，如下所示
     * 下面是使用示例代码块，请不要放开注释
     */
//    @Autowired
//    @Qualifier("redissonSingle")
//    private RedissonClient redissonSingle;
//
//    RLock lock = redissonClient.getLock("testRedisLock");
//        // 加锁
//        lock.lock();
//    try {
//        System.out.println("test1加锁，延迟50秒释放锁,当前时间为:" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
//        TimeUnit.SECONDS.sleep(50);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    } finally {
//        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
//            // 释放锁
//            System.out.println("test1锁释放，当前时间为:" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
//            lock.unlock();
//        }
//    }

}
