package com.bluewind.boot.common.config.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxingyu01
 * @date 2022-11-25 16:15
 * @description Caffeine本地缓存配置类（Caffeine：本地缓存性能之王）
 **/
@Configuration
public class CaffeineCacheConfig {

    @Bean(name="caffeineDictCache")
    public Cache<String, Map<String, String>> caffeineDictCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(name="caffeineOrganCache")
    public Cache<String, String> caffeineOrganCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }
}
