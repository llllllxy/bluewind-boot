package com.liuxingyu.meco.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * @author liuxingyu01
 * @date 2021-03-18-14:00
 * @description AES/CBC/PKCS5Padding加密与解密   使用Base64编码
 **/
public class AESByBase64Utils {
    final static Logger logger = LoggerFactory.getLogger(AESByBase64Utils.class);

    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-256-CBC加密模式，key需要为16位。
     * 秘钥key（可以16或32字节）（128位密钥/256位密钥）
     */
    private final String sKey = "22DEA298B0D8A99C8E8E4C71EA1AC0FC";// key，可自行修改
    private final String ivParameter = "B54480C3A296C33B";// 偏移量,可自行修改

    // 使用 volatile 关键字可以防止指令重排序
    private static volatile AESByBase64Utils instance = null;

    private AESByBase64Utils() {
    }

    /**
     * 懒汉式单例模式（加锁，防止线程安全问题）
     * @return
     */
    public static AESByBase64Utils getInstance() {
        // 线程A和线程B同时看到instance = null，如果不为null，则直接返回instance
        if (instance == null) {
            // 线程A或线程B获得该锁进行初始化
            synchronized(AESByBase64Utils.class) {
                // 其中一个线程进入该分支，另外一个线程则不会进入该分支
                if (instance == null) {
                    instance = new AESByBase64Utils();
                }
            }
        }
        return instance;
    }

    /**
     * 加密 自定义密钥和偏移量
     *
     * @param content   内容
     * @param secretKey 密钥
     * @param ivs       偏移量
     * @return
     */
    public String encrypt(String content, String secretKey, String ivs) {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 32) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);// 此处使用BASE64做转码。
        } catch (Exception e) {
            logger.error("AESByBase64Utils -- encrypt -- Exception=", e);
            return null;
        }
    }


    /**
     * 加密 使用公共的密钥和偏移量
     *
     * @param content 内容
     * @return
     */
    public String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);// 此处使用BASE64做转码。
        } catch (Exception e) {
            logger.error("AESByBase64Utils -- encrypt -- Exception=", e);
            return null;
        }

    }


    /**
     * 解密 使用自定义的密钥和偏移量
     *
     * @param content   内容
     * @param secretKey 密钥
     * @param ivs       偏移量
     * @return
     */
    public String decrypt(String content, String secretKey, String ivs) {
        try {
            byte[] raw = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 使用base64解密
            byte[] encrypted1 = Base64.getDecoder().decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("AESByBase64Utils -- decrypt -- Exception=", ex);
            return null;
        }
    }


    /**
     * 解密 使用公共的密钥和偏移量
     *
     * @param content 内容
     * @return
     */
    public String decrypt(String content) {
        try {
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 使用base64解密
            byte[] encrypted1 = Base64.getDecoder().decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("AESByBase64Utils -- decrypt -- Exception=", ex);
            return null;
        }
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 需要加密的字串
        String cSrc = "ch.qos.logback.core.rolling.RollingFileAppender";

        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AESByBase64Utils.getInstance().encrypt(cSrc);
        System.out.println("AESByBase64Utils加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("AESByBase64Utils加密耗时：" + lUseTime + "毫秒");

        // 解密
        lStart = System.currentTimeMillis();
        String DeString = AESByBase64Utils.getInstance().decrypt(enString);
        System.out.println("AESByBase64Utils解密后的字串是：" + DeString);

        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("AESByBase64Utils解密耗时：" + lUseTime + "毫秒");
    }

}
