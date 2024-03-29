package com.bluewind.boot.module.common.kaptcha.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-09-17:52
 * @description 获取文字验证码控制器
 **/
@Api(value = "获取文字验证码公共控制器", tags = "获取文字验证码公共控制器")
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(KaptchaController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获取文字验证码-参数生成key", notes = "获取文字验证码-参数生成key")
    @GetMapping("/getImage")
    @ResponseBody
    public String getImage(@RequestParam("kaptcha_key") String kaptcha_key) {
        // create the text for the image
        String capText = producer.createText();
        if (logger.isInfoEnabled()) {
            logger.info("******************当前验证码为：{}******************", capText);
        }

        // store the text in the redis
        redisUtil.set(kaptcha_key, capText, 60);

        // return the image with the Base64
        BufferedImage bi = producer.createImage(capText);
        return bufferedImageToBase64(bi);
    }


    @ApiOperation(value = "获取文字验证码-每次自己生成key", notes = "获取文字验证码-每次自己生成key")
    @GetMapping("/getImage2")
    @ResponseBody
    public BaseResult getImage2() {
        // create the text for the image
        String capText = producer.createText();
        if (logger.isInfoEnabled()) {
            logger.info("******************当前验证码为：{}******************", capText);
        }
        String kaptcha_key = IdGenerate.uuid();

        // store the text in the redis
        redisUtil.set(kaptcha_key, capText, 60);

        // return the image with the Base64
        BufferedImage bi = producer.createImage(capText);
        String base64 =  bufferedImageToBase64(bi);

        Map<String, String> result = new HashMap<>();
        result.put("kaptcha_key", kaptcha_key);
        result.put("base64", base64);
        return BaseResult.success("获取文字验证码成功", result);
    }


    /**
     * BufferedImage 编码转换为 base64
     *
     * @param image
     * @return base64
     */
    public static String bufferedImageToBase64(BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", stream);
            String base64 = Base64.getEncoder().encodeToString(stream.toByteArray());
            // 删除 \r\n
            base64 = base64.trim();
            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");
            base64 = "data:image/jpg;base64," + base64;
            return base64;
        } catch (Exception e) {
            logger.error("KaptchaController -- bufferedImageToBase64 -- Exception: ", e);
        }
        return null;
    }

}
