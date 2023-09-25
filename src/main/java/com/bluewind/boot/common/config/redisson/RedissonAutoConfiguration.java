package com.bluewind.boot.common.config.redisson;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * redisson自动配置类
 * 使用文档：https://github.com/redisson/redisson/wiki/8.-distributed-locks-and-synchronizers
 *
 * @author liuxingyu01
 * @date 2021-08-23-13:30
 **/
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    /**
     * 单机模式
     */
    @Bean("redissonSingle")
    @ConditionalOnProperty(prefix = "redisson", name = "mode", havingValue = "single")
    RedissonClient redissonSingle() {
        Config config = new Config();
        // useSingleServer 单机模式
        // useClusterServers 集群模式
        // useMasterSlaveServers 主从模式
        // useSentinelServers 哨兵模式
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        serverConfig.setDatabase(0);
        return Redisson.create(config);
    }


    /**
     * 哨兵模式
     */
    @Bean("redissonSentinel")
    @ConditionalOnProperty(prefix = "redisson", name = "mode", havingValue = "sentinel")
    RedissonClient redissonSentinel() {
        String[] addressArray = redissonProperties.getSentinelAddresses().split(",");
        if (ArrayUtils.isEmpty(addressArray)) {
            throw new IllegalArgumentException("The sentinelAddresses cannot be empty!");
        }
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(addressArray)
                .setMasterName(redissonProperties.getMasterName())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectTimeout(redissonProperties.getConnectTimeout())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        serverConfig.setDatabase(0);
        return Redisson.create(config);
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
//    // 加锁，如果锁被其他线程占用，那么将会一直阻塞等待，直到获取锁
//    lock.lock();
//    // 或者lock.lock(5000, TimeUnit.MILLISECONDS); 参数1：锁的最长持有时间，超时后将自动释放锁；参数2：时间的单位
//    try {
//        TimeUnit.SECONDS.sleep(50);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    } finally {
//        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
//            // 释放锁
//            lock.unlock();
//        }
//    }


//
//    RLock lock = redissonClient.getLock("testRedisLock");
//    // 尝试获取锁，参数1：最大等待时间，参数2：上锁后自动释放锁时间，参数3：时间单位
//    boolean tryLock = lock.tryLock(5000, 5000, TimeUnit.MILLISECONDS);
//    // 或boolean tryLock = lock.tryLock(5000, TimeUnit.MILLISECONDS); 参数1：最大等待时间，参数2：时间单位；若没有主动释放的话，那就一直持有锁
//    try {
//        TimeUnit.SECONDS.sleep(50);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    } finally {
//        if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
//            // 释放锁
//            lock.unlock();
//        }
//    }

}
