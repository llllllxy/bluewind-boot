package com.bluewind.boot.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author liuxingyu01
 * @date 2021-03-18-12:58
 * @description SHA256加密算法
 **/
public class SHA256Utils {
    final static Logger log = LoggerFactory.getLogger(SHA256Utils.class);


    /**
     * 利用java原生的摘要实现SHA256加密
     * @param origin 原始值
     * @param charsetname 编码类型
     * @param salt 盐
     * @return 加密后的报文
     */
    public static String SHA256Encode(String origin, String salt, String charsetname) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            origin = salt + origin;
            messageDigest = MessageDigest.getInstance("SHA-256");
            if (charsetname == null || "".equals(charsetname)) {
                messageDigest.update(origin.getBytes(StandardCharsets.UTF_8));
            } else {
                Charset charset = Charset.forName(charsetname);
                messageDigest.update(origin.getBytes(charset));
            }
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA256Utils -- SHA256Encode -- Exception= {e}", e);
        }
        return encodeStr;
    }


    /**
     * @param origin 原始值
     * @return 加密后的字符串
     */
    public static String SHA256Encode(String origin) {
        return SHA256Encode(origin.trim(), "", "UTF-8");
    }


    /**
     * @param origin 原始值
     * @param salt   盐值
     * @return 加密后的字符串
     */
    public static String SHA256Encode(String origin, String salt) {
        return SHA256Encode(origin.trim(), salt.trim(), "UTF-8");
    }




    /**
     * 利用java原生的摘要实现SHA256加密
     * @param origin 原始值
     * @param charsetname 编码类型
     * @param salt 盐
     * @return 加密后的报文
     */
    public static String SHA256EncodeBase64(String origin, String salt, String charsetname) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            origin = salt + origin;
            messageDigest = MessageDigest.getInstance("SHA-256");
            if (charsetname == null || "".equals(charsetname)) {
                messageDigest.update(origin.getBytes(StandardCharsets.UTF_8));
            } else {
                Charset charset = Charset.forName(charsetname);
                messageDigest.update(origin.getBytes(charset));
            }
            encodeStr = Base64.getEncoder().encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA256Utils -- SHA256Encode -- Exception= {e}", e);
        }
        return encodeStr;
    }


    /**
     * @param origin 原始值
     * @return 加密后的字符串
     */
    public static String SHA256EncodeBase64(String origin) {
        return SHA256EncodeBase64(origin.trim(), "", "UTF-8");
    }


    /**
     * @param origin 原始值
     * @param salt   盐值
     * @return 加密后的字符串
     */
    public static String SHA256EncodeBase64(String origin, String salt) {
        return SHA256EncodeBase64(origin.trim(), salt.trim(), "UTF-8");
    }


    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder sBuilder = new StringBuilder();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                sBuilder.append("0");
            }
            sBuilder.append(temp);
        }
        return sBuilder.toString();
    }


    public static void main(String[] args) {
        String t = "123456";
        System.out.println(SHA256Encode(t));
        System.out.println(SHA256EncodeBase64(t));
    }

}
