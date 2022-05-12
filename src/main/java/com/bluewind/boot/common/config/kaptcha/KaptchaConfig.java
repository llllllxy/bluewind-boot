package com.bluewind.boot.common.config.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author liuxingyu01
 * @date 2021-09-19-9:02
 * @description 文字验证码配置类
 **/
@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getKaptcheCode() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty("kaptcha.border", "no");
        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 验证码文本字符大小 默认为40
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width", "100");
        // 验证码图片高度 默认为50
        properties.setProperty("kaptcha.image.height", "36");
        // 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple、鱼眼com.google.code.kaptcha.impl.FishEyeGimpy、阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        // KAPTCHA_SESSION_KEY
        properties.setProperty("kaptcha.session.key", "code");
        // 干扰实现类
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 背景颜色渐变，开始颜色
        properties.setProperty("kaptcha.background.clear.from", "255,255,255");
        // 背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "255,255,255");
        // 验证码文本字符长度 默认为5
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
