package com.bluewind.boot.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * @author liuxingyu01
 * @date 2021-02-09-18:45
 * @description springboot中默认使用jackson格式化数据
 **/
@Configuration
public class JacksonConfig {


    /**
     * 让返回的json数据中的null转为""字符串
     * @param builder
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 让返回json数据中的Long类型转换为String（暂时用不到，先注释掉）
        // SimpleModule simpleModule = new SimpleModule();
        // simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        // simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // objectMapper.registerModule(simpleModule);

        // 让返回的json数据中的null转为""字符串
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}
