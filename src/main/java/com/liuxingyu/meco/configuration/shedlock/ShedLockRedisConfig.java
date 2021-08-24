package com.liuxingyu.meco.configuration.shedlock;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuxingyu01
 * @date 2021-08-24-18:56
 * @description shedlock配置类
 **/
@Configuration
@EnableScheduling //开启定时任务注解
// 开启定时任务锁，并设置默认锁最大时间为30秒(PT为固定格式，S为时间单位)   ---  PT5M表示五分钟
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class ShedLockRedisConfig {

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
        // 环境变量 -需要区分不同环境避免冲突，如dev环境和test环境，
        // 两者都部署时，只有一个实例进行，此时会造成相关环境未启动情况
        return new RedisLockProvider(connectionFactory, env);
    }

}
