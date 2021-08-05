package com.liuxingyu.meco.configuration;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate配置类，进行一些个性化的配置
 *
 * @author liuxingyu01
 * @date 2020-12-26-21:03
 **/
@Configuration
public class RestTemplateConfig {

    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(1000); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(500); // 每个主机的并发
        return poolingConnectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setHttpClient(httpClientBuilder().build());
        httpRequestFactory.setConnectionRequestTimeout(3000);// 获取链接超时时间
        httpRequestFactory.setConnectTimeout(3000);// 指客户端和服务器建立连接的timeout
        httpRequestFactory.setReadTimeout(120000);// 读取数据的超时时间
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }

}
