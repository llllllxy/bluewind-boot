package com.liuxingyu.meco.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author liuxingyu01
 * @date 2021-03-18-13:08
 * @description AES/CBC/PKCS5Padding加密与解密  使用Hex编码
 **/
@Component
public class AESByHexUtils {
    final static Logger logger = LoggerFactory.getLogger(AESByHexUtils.class);

    // 算法/加密模式/填充方式
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";
    // 秘钥key（可以16或32字节）（128位密钥/256位密钥）
    //private static final String KEY = "22DEA298B0D8A99C8E8E4C71EA1AC0FC";
    // 偏移量iv（必须16字节）
    //private static final String IV = "B54480C3A296C33B";

    @Value("${decrypt.key}")
    private String KEY;

    @Value("${decrypt.iv}")
    private String IV;

    public String getKEY() {
        return this.KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getIV() {
        return this.IV;
    }

    public void setIV(String IV) {
        this.IV = IV;
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
            byte[] raw = KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encrypted);
        } catch (Exception e) {
            logger.error("AESByHexUtils -- encrypt -- Exception=", e);
            return "";
        }
    }

    /**
     * AES解密
     *
     * @param content 要加密的字符串
     * @return 解密后的结果
     * @throws Exception
     */
    public String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            byte[] raw = KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
            byte[] result = cipher.doFinal(hexToByteArray(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("AESByHexUtils -- decrypt -- Exception=", e);
            return "";
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
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        AESByHexUtils aesByHexUtils = new AESByHexUtils();
        aesByHexUtils.setIV("B54480C3A296C33B");
        aesByHexUtils.setKEY("22DEA298B0D8A99C8E8E4C71EA1AC0FC");

        // 原文:
        String message = "system.sysconfig.sysconfig_update";
        System.out.println("Message: " + message);

        // 加密:
        String encrypted = aesByHexUtils.encrypt(message);
        System.out.println("Encrypted: " + encrypted);

        // 解密:
        String decrypted = aesByHexUtils.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }

}
