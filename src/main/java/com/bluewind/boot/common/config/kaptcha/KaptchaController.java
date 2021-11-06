package com.bluewind.boot.common.config.kaptcha;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.utils.RedisUtil;
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

/**
 * @author liuxingyu01
 * @date 2021-02-09-17:52
 **/
@Api(tags = "获取验证码")
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(KaptchaController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping("/getImage")
    @ResponseBody
    public String getKaptchaImage(@RequestParam("kaptcha_key") String kaptcha_key) {
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
