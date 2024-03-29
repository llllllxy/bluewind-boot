package com.bluewind.boot.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author liuxingyu01
 * @date 2021-09-09-9:43
 * @description AESUtils工具类，使用Builder模式构造（CBC模式，256位密钥）
 **/
public class AESUtils {
    final static Logger logger = LoggerFactory.getLogger(AESUtils.class);

    /**
     * 算法/加密模式/填充方式
     */
    private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 密钥key（可以16或32字节）（128位密钥/256位密钥，设置256位密钥更难破解）
     */
    private String sKey;

    /**
     * 偏移量（16字节）
     */
    private String ivParameter;

    /**
     * 是否使用base64编码（默认false，默认使用hex）
     */
    private boolean ifBase64;


    public String getsKey() {
        return sKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey;
    }

    public String getIvParameter() {
        return ivParameter;
    }

    public void setIvParameter(String ivParameter) {
        this.ivParameter = ivParameter;
    }

    public boolean isIfBase64() {
        return ifBase64;
    }

    public void setIfBase64(boolean ifBase64) {
        this.ifBase64 = ifBase64;
    }


    private AESUtils(AESUtilsBuilder builder) {
        this.sKey = builder.sKey;
        this.ivParameter = builder.ivParameter;
        this.ifBase64 = builder.ifBase64;
    }


    public static AESUtilsBuilder builder() {
        return new AESUtilsBuilder();
    }



    /**
     * 2020-08-13-14:07--liuxingyu01
     * AES加密
     *
     * @param content 要加密的字符串
     * @return 430301102072 / d086af4d68888097504e536e5cfe351c / Hex字符串展示
     */
    public String encrypt(String content) {
        try {
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

            if (ifBase64) { // 此处使用BASE64做编码
                return Base64.getEncoder().encodeToString(encrypted);
            } else { // 此处使用Hex做编码
                return bytesToHex(encrypted);
            }
        } catch (Exception e) {
            logger.error("AESUtils -- encrypt -- Exception=", e);
            return null;
        }
    }


    /**
     * AES解密
     *
     * @param content 要解密的字符串
     * @return 解密后的结果
     */
    public String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
            byte[] result;
            if (ifBase64) { // 此处使用BASE64做编码
                result = cipher.doFinal(Base64.getDecoder().decode(content));
            } else { // 此处使用Hex做编码
                result = cipher.doFinal(hexToByteArray(content));
            }
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("AESUtils -- decrypt -- Exception=", e);
            return null;
        }
    }


    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    /**
     * AESUtilsBuilder
     */
    public static final class AESUtilsBuilder {
        private String sKey = "1G78Av#yej%WZJ3uiSZRz9oy%UAv4!EG";
        private String ivParameter = "E%BJuDUTvXfwSuGQ";
        private boolean ifBase64 = false;

        public AESUtilsBuilder() {
            this.sKey = sKey;
            this.ivParameter = ivParameter;
            this.ifBase64 = ifBase64;
        }

        public AESUtilsBuilder sKey(String sKey) {
            this.sKey = sKey;
            return this;
        }

        public AESUtilsBuilder ivParameter(String ivParameter) {
            this.ivParameter = ivParameter;
            return this;
        }

        public AESUtilsBuilder ifBase64(boolean ifBase64) {
            this.ifBase64 = ifBase64;
            return this;
        }

        public AESUtils build() {
            AESUtils aesUtils = new AESUtils(this);
            return aesUtils;
        }
    }


    /**
     * 测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 原文:
        String message = "Helloworld!";
        System.out.println("原文: " + message);

        // 使用builder构建AESUtils方法
        AESUtils aesUtils = AESUtils.builder().sKey("1G78Av#yej%WZJ3uiSZRz9oy%UAv4AAA").ivParameter("E%BAAAUTvXfwSuGQ").build();

        // 加密:
        String encrypted = aesUtils.encrypt(message);
        System.out.println("加密后: " + encrypted);

        // 解密:
        String decrypted = aesUtils.decrypt(encrypted);
        System.out.println("解密后: " + decrypted);
    }
}


