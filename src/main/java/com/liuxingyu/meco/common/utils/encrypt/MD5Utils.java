package com.liuxingyu.meco.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author liuxingyu01
 * @date 2020-05-28-23:34
 * @description MD5加密
 **/
public class MD5Utils {
    final static Logger log = LoggerFactory.getLogger(MD5Utils.class);

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将byte[]转为16进制
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    /**
     * 返回小写MD5（hex编码）
     *
     * @param origin 原始值
     * @param charsetname 编码类型
     * @param salt 盐
     * @return 加密后的字符串
     */
    private static String MD5Encode(String origin, String salt, String charsetname) {
        String resultString = null;
        try {
            resultString = salt + origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(StandardCharsets.UTF_8)));
            } else {
                Charset charset = Charset.forName(charsetname);
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
            }
        } catch (Exception exception) {
            if (log.isErrorEnabled()) {
                log.error("MD5Utils -- MD5Encode -- Exception= {exception}", exception);
            }
        }
        return resultString;
    }


    /**
     * @param origin 原始值
     * @return 加密后的字符串
     */
    public static String MD5Encode(String origin) {
        return MD5Encode(origin.trim(), "", "UTF-8");
    }


    /**
     * @param origin 原始值
     * @param salt   盐值
     * @return 加密后的字符串
     */
    public static String MD5Encode(String origin, String salt) {
        return MD5Encode(origin.trim(), salt.trim(), "UTF-8");
    }


    /**
     * 返回小写MD5（base64编码）
     *
     * @param origin 原始值
     * @param charsetname 编码类型
     * @param salt 盐
     * @return 加密后的字符串
     */
    private static String MD5EncodeBase64(String origin, String salt, String charsetname) {
        String resultString = null;
        try {
            resultString = salt + origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = Base64.getEncoder().encodeToString(md.digest(resultString.getBytes(StandardCharsets.UTF_8)));
            } else {
                Charset charset = Charset.forName(charsetname);
                resultString = Base64.getEncoder().encodeToString(md.digest(resultString.getBytes(charset)));
            }
        } catch (Exception exception) {
            if (log.isErrorEnabled()) {
                log.error("MD5Utils -- MD5EncodeByBase64 -- Exception= {exception}", exception);
            }
        }
        return resultString;
    }


    /**
     *
     * @param origin 原始值
     * @return 加密后的字符串
     */
    public static String MD5EncodeBase64(String origin) {
        return MD5EncodeBase64(origin.trim(), "", "UTF-8");
    }


    /**
     * @param origin 原始值
     * @param salt   盐值
     * @return 加密后的字符串
     */
    public static String MD5EncodeBase64(String origin, String salt) {
        return MD5EncodeBase64(origin.trim(), salt.trim(), "UTF-8");
    }



    public static void main(String[] args) {
        //  66dea298b0d8a91c8e8e4c71ea1ac0fc
        System.out.println(MD5Encode("123456", "323@#@$1234da", "utf-8"));
        System.out.println(MD5EncodeBase64("123456", "323@#@$1234da"));
    }

}
