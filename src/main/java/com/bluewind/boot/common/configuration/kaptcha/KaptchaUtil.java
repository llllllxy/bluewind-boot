package com.bluewind.boot.common.configuration.kaptcha;

import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author liuxingyu01
 * @date 2021-02-09-17:51
 * @description Kaptcha工具类
 **/
public class KaptchaUtil {
    final static Logger logger = LoggerFactory.getLogger(KaptchaUtil.class);

    private static RedisUtil redisUtil;

    private static RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            Object bean = SpringUtil.getBean("redisUtil");
            if (bean == null) {
                logger.error("redisUtil bean is null!");
            }
            redisUtil = (RedisUtil) bean;
        }
        return redisUtil;
    }


    /**
     * 验证码校验
     *
     * @param
     * @return 正确:true/错误:false
     */
    public static boolean validate(String registerCode, String kaptchaKey) {

        if (StringUtils.isEmpty(registerCode)) {
            return false;
        }
        if (StringUtils.isEmpty(kaptchaKey)) {
            return false;
        }
        try {
            String captcha = getRedisUtil().get(kaptchaKey).toString();
            boolean result = registerCode.equalsIgnoreCase(captcha);
            if (result) {
                getRedisUtil().del(kaptchaKey);
            }
            return result;
        } catch (Exception e) {  // redis里找不到这个key
            logger.error("KaptchaUtil -- validate Exception = {e}", e);
            return false;
        }
    }
}
