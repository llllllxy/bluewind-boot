package com.bluewind.boot.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuxingyu01
 * @date 2020-10-15-19:18
 * @description 图片添加文字水印公共方法类
 **/
public class WaterMarkUtils {
    private static Logger log = LoggerFactory.getLogger(WaterMarkUtils.class);

    public static String waterMark(List<String> text, byte[] imageByte) {
        String base64 = "";
        BufferedImage bImage = null;
        Graphics2D g = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte)) {
            bImage = ImageIO.read(byteArrayInputStream);
            // 获得图片的属性信息
            int width = bImage.getWidth(); // 原图片宽
            int height = bImage.getHeight();// 原图片高
            int minSide = Math.min(width, height);
            // 构建2D画笔
            g = bImage.createGraphics();
            // 设置2D画笔的画出的文字颜色
            g.setColor(Color.white);
            // 设置2D画笔的画出的文字背景色
//            g.setBackground(Color.white);
            // 动态设置字体大小及坐标
            int fontSize = 35;
            int fontBlack = 40;
            double ss = (double) minSide / 1080;
            fontSize = (int) Math.round(ss * fontSize);
            fontBlack = (int) Math.round(ss * fontBlack);
            // 设置文字样式
            Font font = new Font("黑体", Font.BOLD, fontSize);
            g.setFont(font);
            // 设置文件透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.6));
            // 向画板上写字
            int x = 40;
            if (text != null && text.size() > 0) {
                for (int i = 0, size = text.size(); i < size; i++) {
                    String content = text.get(i);
                    g.drawString(content, x, height - fontBlack * (size - i));//向画板上写字
                }
            }
            base64 = bufferedImageToBase64(bImage);
        } catch (Exception e) {
            log.error("WaterMarkUtils -- waterMark -- Exception: ", e);
        } finally {
            if (bImage != null) {
                bImage.flush();
            }
            if (g != null) {
                g.dispose();
            }
        }
        return base64;
    }


    public static byte[] waterMarkReturnByte(List<String> text, byte[] imageByte) {
        byte[] base64 = new byte[8];
        BufferedImage bImage = null;
        Graphics2D g = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte)) {
            bImage = ImageIO.read(byteArrayInputStream);
            // 获得图片的属性信息
            int width = bImage.getWidth(); // 原图片宽
            int height = bImage.getHeight();// 原图片高
            int minSide = Math.min(width, height);
            // 构建2D画笔
            g = bImage.createGraphics();
            // 设置2D画笔的画出的文字颜色
            g.setColor(Color.white);
            // 设置2D画笔的画出的文字背景色
            // g.setBackground(Color.white);
            // 动态设置字体大小及坐标
            int fontSize = 35;
            int fontBlack = 40;
            double ss = (double) minSide / 1080;
            fontSize = (int) Math.round(ss * fontSize);
            fontBlack = (int) Math.round(ss * fontBlack);
            // 设置文字样式
            Font font = new Font("黑体", Font.BOLD, fontSize);
            g.setFont(font);
            // 设置文件透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.6));
            // 向画板上写字
            int x = 40;
            if (text != null && text.size() > 0) {
                for (int i = 0, size = text.size(); i < size; i++) {
                    String content = text.get(i);
                    g.drawString(content, x, height - fontBlack * (size - i));//向画板上写字
                }
            }
            base64 = bufferedImageToByte(bImage);
        } catch (Exception e) {
            log.error("WaterMarkUtils -- waterMarkReturnByte -- Exception: ", e);
        } finally {
            if (bImage != null) {
                bImage.flush();
            }
            if (g != null) {
                g.dispose();
            }
        }
        return base64;
    }


    public static String waterMarkWithBase64(List<String> text, String fileStr) {
        String base64 = "";
        BufferedImage bImage = null;
        Graphics2D g = null;
        if (StringUtils.isBlank(fileStr)) {
            // 传入的64位编码违规
            return null;
        }
        String[] tempFileArr = fileStr.split(",");
        if (tempFileArr.length == 2) {
            fileStr = tempFileArr[1];
        }
        byte[] imageByte = Base64.getDecoder().decode(fileStr);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte)) {
            bImage = ImageIO.read(byteArrayInputStream);
            // 获得图片的属性信息
            int width = bImage.getWidth(); // 原图片宽
            int height = bImage.getHeight();// 原图片高
            int minSide = Math.min(width, height);
            // 构建2D画笔
            g = bImage.createGraphics();
            // 设置2D画笔的画出的文字颜色
            g.setColor(Color.black);
            // 设置2D画笔的画出的文字背景色
//            g.setBackground(Color.white);
            // 动态设置字体大小及坐标
            int fontSize = 35;
            int fontBlack = 40;
            double ss = (double) minSide / 1080;
            fontSize = (int) Math.round(ss * fontSize);
            fontBlack = (int) Math.round(ss * fontBlack);
            // 设置文字样式
            Font font = new Font("黑体", Font.BOLD, fontSize);
            g.setFont(font);
            // 设置文件透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.6));
            // 向画板上写字
            int x = 40;
            if (text != null && text.size() > 0) {
                for (int i = 0, size = text.size(); i < size; i++) {
                    String content = text.get(i);
                    g.drawString(content, x, height - fontBlack * (size - i));//向画板上写字
                }
            }
            base64 = bufferedImageToBase64(bImage);
        } catch (Exception e) {
            log.error("WaterMarkUtils -- waterMarkWithBase64 -- Exception: ", e);
        } finally {
            if (bImage != null) {
                bImage.flush();
            }
            if (g != null) {
                g.dispose();
            }
        }
        return base64;
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
            log.error("WaterMarkUtils--BufferedImageToBase64--Exception: ", e);
        }
        return null;
    }


    /**
     * BufferedImage 转换为 byte[]
     *
     * @param image
     * @return base64
     */
    public static byte[] bufferedImageToByte(BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", stream);
            byte[] result = stream.toByteArray();
            return result;
        } catch (Exception e) {
            log.error("WaterMarkUtils--bufferedImageToByte--Exception: ", e);
        }
        return null;
    }


    /**
     * base64 编码转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    private static BufferedImage base64ToBufferedImage(String base64) {
        try {
            byte[] bytes1 = Base64.getDecoder().decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            log.error("WaterMarkUtils--base64ToBufferedImage--Exception: ", e);
        }
        return null;
    }
}
