package com.liuxingyu.meco.configuration.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author liuxingyu01
 * @date 2021-02-09-17:49
 **/
@Component
public class KaptchaConfig {
    final static Logger logger = LoggerFactory.getLogger(KaptchaConfig.class);

    @Bean
    public DefaultKaptcha getKaptcheCode() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no"); // 图片边框
        properties.setProperty("kaptcha.textproducer.font.color", "black"); //
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "36");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.background.clear.from", "255,255,255");
        properties.setProperty("kaptcha.background.clear.to", "255,255,255");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
