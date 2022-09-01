package com.bluewind.boot.common.utils.encrypt.sm4;

import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuxingyu01
 * @date 2021-09-08-19:45
 * @description 国密4加密算法工具类（对称加密）
 **/
public class SM4Utils {

    /**
     * 密钥key（可以16或32字节）（128位密钥/256位密钥，设置256位密钥更难破解）
     */
    private String secretKey;

    /**
     * 偏移量（16字节）
     */
    private String ivParameter;

    /**
     * 是否使用hex编码（默认true，默认使用hex）
     */
    private boolean hexString;

    /**
     * 加密模式（默认ECB）
     */
    private String mode;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIvParameter() {
        return ivParameter;
    }

    public void setIvParameter(String ivParameter) {
        this.ivParameter = ivParameter;
    }

    public boolean isHexString() {
        return hexString;
    }

    public void setHexString(boolean hexString) {
        this.hexString = hexString;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private SM4Utils(SM4Builder builder) {
        this.secretKey = builder.secretKey;
        this.ivParameter = builder.ivParameter;
        this.hexString = builder.hexString;
        this.mode = builder.mode;
    }


    public static SM4Builder builder() {
        return new SM4Builder();
    }


    /**
     * AESUtilsBuilder
     */
    public static final class SM4Builder {
        private String secretKey = "G78Av#yej%WZJ3ui";
        private String ivParameter = "E%BJuDUTvXfwSuGQ";
        private boolean hexString = true;
        private String mode = "ECB";

        public SM4Builder() {
            this.secretKey = secretKey;
            this.ivParameter = ivParameter;
            this.hexString = hexString;
            this.mode = mode;
        }

        public SM4Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public SM4Builder ivParameter(String ivParameter) {
            this.ivParameter = ivParameter;
            return this;
        }

        public SM4Builder hexString(boolean hexString) {
            this.hexString = hexString;
            return this;
        }

        public SM4Builder mode(String mode) {
            this.mode = mode;
            return this;
        }

        public SM4Utils build() {
            SM4Utils sm4Utils = new SM4Utils(this);
            return sm4Utils;
        }
    }


    /**
     * 加密
     *
     * @param plainText 待加密的字符串
     * @return
     */
    public String encrypt(String plainText) throws Exception {
        if ("ECB".equals(mode)) {
            return encrypt_ECB(plainText, secretKey, hexString);
        } else if ("CBC".equals(mode)) {
            return encrypt_CBC(plainText, secretKey, ivParameter, hexString);
        } else {
            throw new Exception("mode error!");
        }
    }


    /**
     * 解密
     *
     * @param cipherText 待解密的字符串
     * @return
     */
    public String decrypt(String cipherText) throws Exception {
        if ("ECB".equals(mode)) {
            return decrypt_ECB(cipherText, secretKey, hexString);
        } else if ("CBC".equals(mode)) {
            return decrypt_CBC(cipherText, secretKey, ivParameter, hexString);
        } else {
            throw new Exception("mode error!");
        }
    }


    /**
     * 十六进制串转化为byte数组
     *
     * @return the array of byte
     */
    public static byte[] hexToByte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }


    /**
     * 字节数组转换为十六进制字符串
     *
     * @param b byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    public static String byteToHex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toLowerCase();
    }


    /**
     * 加密（ECB模式）
     *
     * @param plainText 待加密的内容
     * @param secretKey 加密密钥
     * @param hexString 返回数据类型
     * @return
     */
    public static String encrypt_ECB(String plainText, String secretKey, boolean hexString) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes = secretKey.getBytes("UTF-8");

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));

            if (hexString) {
                return byteToHex(encrypted);
            } else {
                return Base64.getEncoder().encodeToString(encrypted);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密（ECB模式）- 返回hex
     *
     * @param plainText 待加密的内容
     * @param secretKey 加密密钥
     * @return
     */
    public static String encrypt_ECB(String plainText, String secretKey) {
        return encrypt_ECB(plainText, secretKey, true);
    }

    /**
     * 解密（ECB模式）
     *
     * @param cipherText 待解密的内容
     * @param secretKey  加密密钥
     * @param hexString  返回数据类型
     * @return
     */
    public static String decrypt_ECB(String cipherText, String secretKey, boolean hexString) {
        try {
            byte[] encrypted;
            if (hexString) {
                encrypted = hexToByte(cipherText);
            } else {
                if (cipherText != null && cipherText.trim().length() > 0) {
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(cipherText);
                    cipherText = m.replaceAll("");
                }
                encrypted = Base64.getDecoder().decode(cipherText);
            }

            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes = secretKey.getBytes("UTF-8");

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, encrypted);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解密（ECB模式）
     *
     * @param cipherText 待解密的内容
     * @param secretKey  加密密钥
     * @return
     */
    public static String decrypt_ECB(String cipherText, String secretKey) {
        return decrypt_ECB(cipherText, secretKey, true);
    }


    /**
     * 加密（CBC模式）
     *
     * @param plainText 待加密的内容
     * @param secretKey 加密密钥
     * @param iv        偏移量
     * @param hexString 返回数据类型
     * @return
     */
    public static String encrypt_CBC(String plainText, String secretKey, String iv, boolean hexString) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;

            keyBytes = secretKey.getBytes("UTF-8");
            ivBytes = iv.getBytes("UTF-8");

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("UTF-8"));

            if (hexString) {
                return byteToHex(encrypted);
            } else {
                return Base64.getEncoder().encodeToString(encrypted);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密（CBC模式）
     *
     * @param plainText 待加密的内容
     * @param secretKey 加密密钥
     * @param iv        偏移量
     * @return
     */
    public static String encrypt_CBC(String plainText, String secretKey, String iv) {
        return encrypt_CBC(plainText, secretKey, iv, true);
    }


    /**
     * 解密（CBC模式）
     *
     * @param cipherText 待解密的内容
     * @param secretKey  加密密钥
     * @param iv         偏移量
     * @param hexString  返回数据类型
     * @return
     */
    public static String decrypt_CBC(String cipherText, String secretKey, String iv, boolean hexString) {
        try {
            byte[] encrypted;
            if (hexString) {
                encrypted = hexToByte(cipherText);
            } else {
                if (cipherText != null && cipherText.trim().length() > 0) {
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(cipherText);
                    cipherText = m.replaceAll("");
                }
                encrypted = Base64.getDecoder().decode(cipherText);
            }

            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes = secretKey.getBytes("UTF-8");
            byte[] ivBytes = iv.getBytes("UTF-8");

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, encrypted);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 解密（CBC模式）
     *
     * @param cipherText 待解密的内容
     * @param secretKey  加密密钥
     * @param iv         偏移量
     * @return
     */
    public static String decrypt_CBC(String cipherText, String secretKey, String iv) {
        return decrypt_CBC(cipherText, secretKey, iv, true);
    }


    /**
     * 测试
     * secretKey和iv必须设置为16进制串（32字节）
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        String plainText = "Test SM4 Function";

        // 设置加密密钥
        String secretKey = "Ber3z8TK96xrg@e2";

        // 偏移量（CBC模式使用）
        String iv = "E%BJuDUTvXfwSuGQ";

        // ECB模式
        System.out.println("ECB模式加密");
        String cipherText = SM4Utils.encrypt_ECB(plainText, secretKey);
        System.out.println("密文: " + cipherText);

        String plainText2 = SM4Utils.decrypt_ECB(cipherText, secretKey);
        System.out.println("明文: " + plainText2);

        // CBC模式（需要设置一个32位的偏移量）
        System.out.println("CBC模式加密");

        String cipherText2 = SM4Utils.encrypt_CBC(plainText, secretKey, iv);
        System.out.println("加密密文: " + cipherText2);

        String plainText3 = SM4Utils.decrypt_CBC(cipherText2, secretKey, iv);
        System.out.println("解密明文: " + plainText3);


        // 测试builder模式
        SM4Utils sm4Utils = SM4Utils.builder().secretKey("Ber3z8TK96xrg@e2").ivParameter("E%BJuDUTvXfwSuGQ").mode("CBC").hexString(false).build();
        System.out.println("build - 加密密文: " + sm4Utils.encrypt(plainText));
        System.out.println("build - 解密明文: " + sm4Utils.decrypt(sm4Utils.encrypt(plainText)));
    }
}
