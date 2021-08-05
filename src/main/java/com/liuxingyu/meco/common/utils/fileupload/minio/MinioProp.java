package com.liuxingyu.meco.common.utils.fileupload.minio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-04-15-9:24
 * @description minio属性值注入
 **/
@Component
public class MinioProp implements Serializable {
    private static final long serialVersionUID = 1195859524081537555L;

    /**
     * 连接url
     */
    public static String endpoint;
    /**
     * 用户名
     */
    public static String accesskey;
    /**
     * 密码
     */
    public static String secretKey;
    /**
     * 存储桶
     */
    public static String bucket;

    public static String getEndpoint() {
        return endpoint;
    }

    @Value("${minio.endpoint}")
    public void setEndpoint(String endpoint) {
        MinioProp.endpoint = endpoint;
    }

    public static String getAccesskey() {
        return accesskey;
    }

    @Value("${minio.accesskey}")
    public void setAccesskey(String accesskey) {
        MinioProp.accesskey = accesskey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    @Value("${minio.secretKey}")
    public void setSecretKey(String secretKey) {
        MinioProp.secretKey = secretKey;
    }

    public static String getBucket() {
        return bucket;
    }

    @Value("${minio.bucket}")
    public void setBucket(String bucket) {
        MinioProp.bucket = bucket;
    }
}
