package com.bluewind.boot.common.utils.fileupload.alioss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liuxingyu01
 * @date 2021-05-11-18:42
 **/
@Component
public class OssProp {

    public static String endPoint;

    public static String accessKeyId;

    public static String accessKeySecret;

    public static String bucketName;

    public static String getEndPoint() {
        return endPoint;
    }

    @Value("${oss.endPoint}")
    public void setEndPoint(String endPoint) {
        OssProp.endPoint = endPoint;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    @Value("${oss.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        OssProp.accessKeyId = accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    @Value("${oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        OssProp.accessKeySecret = accessKeySecret;
    }

    public static String getBucketName() {
        return bucketName;
    }

    @Value("${oss.bucketName}")
    public void setBucketName(String bucketName) {
        OssProp.bucketName = bucketName;
    }
}
