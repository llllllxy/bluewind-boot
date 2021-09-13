package com.bluewind.boot.common.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptUtils {

    public static String encrypt(String password, String encryptStr) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 加密所需的salt（盐值）
        textEncryptor.setPassword(password);
        // 要加密的数据（数据库的用户名或密码）
        return textEncryptor.encrypt(encryptStr);
    }

    public static String decrypt(String password, String decryptStr) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 加密所需的salt（盐值）
        textEncryptor.setPassword(password);
        // 要解密的数据（数据库的用户名或密码）
        return textEncryptor.decrypt(decryptStr);
    }

    public static void main(String[] args) throws Exception {
        String password = "ymp8R3Vg7Kv5$y5fM3*xl&ins7SZcTEY";

        System.out.println(encrypt(password, "root"));
        System.out.println(encrypt(password, "199725"));

        System.out.println(decrypt(password, "31ptzOxoKsoUfGKLJhBEnQ=="));
    }

}
