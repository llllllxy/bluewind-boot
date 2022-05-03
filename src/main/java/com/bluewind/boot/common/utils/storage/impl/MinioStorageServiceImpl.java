package com.bluewind.boot.common.utils.storage.impl;

import com.bluewind.boot.common.utils.FileUtils;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.common.utils.storage.api.StorageService;
import com.bluewind.boot.common.utils.storage.model.StorageFile;
import com.bluewind.boot.common.utils.storage.model.StorageStreamFile;
import com.bluewind.boot.common.utils.storage.properties.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxingyu01
 * @date 2022-04-22 17:00
 * @description minio对象存储实现
 **/
@Service
@ConditionalOnProperty(prefix = "bluewind", name = "storage-type", havingValue = "minio")
public class MinioStorageServiceImpl implements StorageService {
    final static Logger logger = LoggerFactory.getLogger(MinioStorageServiceImpl.class);

    private static String defaultBucket = null;

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 获取MinioClient
     *
     * @return
     * @throws Exception
     */
    public MinioClient getMinioClient() throws Exception {
        MinioClient minioClient = null;
        if (StringUtils.isBlank(defaultBucket)) {
            defaultBucket = minioProperties.getBucket();
        }

        if (StringUtils.isNotBlank(minioProperties.getEndpoint())
                && StringUtils.isNotBlank(minioProperties.getAccessKey())
                && StringUtils.isNotBlank(minioProperties.getSecretKey())) {
            minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpoint())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(defaultBucket).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(defaultBucket).build());
            }
        }
        return minioClient;
    }


    /**
     * 获取文件
     *
     * @param fileId
     * @return
     */
    @Override
    public StorageStreamFile findById(String fileId) {
        StorageStreamFile storageStreamFile = null;
        try {
            MinioClient minioClient = getMinioClient();
            // 获取文件信息
            StatObjectResponse statObjectResponse = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .build()
            );
            Headers headers = statObjectResponse.headers();
            storageStreamFile = new StorageStreamFile();
            // userMetadata 中设置自定义属性，statObjectResponse.userMetadata()中获取不到，需要到headers中获取
            // 自定义属性在获取时需要添加前缀 X-Amz-Meta-
            // 文件名称也会发生相应的改变，例如：fileName -> Filename,description -> Description,
            storageStreamFile.setFileId(fileId);
            storageStreamFile.setMd5(headers.get("Etag"));
            storageStreamFile.setFileName(headers.get("X-Amz-Meta-Filename"));
            String Extendinfo = headers.get("X-Amz-Meta-Extendinfo");
            if (StringUtils.isNotEmpty(Extendinfo)) {
                storageStreamFile.setMetaData(JsonTool.parseMap(Extendinfo));
            }
            storageStreamFile.setContentType(statObjectResponse.contentType());
            storageStreamFile.setLength(statObjectResponse.size());
            storageStreamFile.setUploadDate(Date.from(statObjectResponse.lastModified().toInstant()));
            // 获取文件
            GetObjectResponse object = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .build());
            storageStreamFile.setInputStream(object);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("MinioStorageServiceImpl - findById - 下载文件异常：", e);
            }
        }
        return storageStreamFile;
    }

    @Override
    public StorageFile store(InputStream inputStream, String fileName) {
        return store(inputStream, fileName, null, null);
    }

    @Override
    public StorageFile store(InputStream inputStream, String fileName, String contentType) {
        return store(inputStream, fileName, contentType, null);
    }

    @Override
    public StorageFile store(InputStream inputStream, String fileName, Map<String, String> metaData) {
        return store(inputStream, fileName, "application/octet-stream", metaData);
    }

    @Override
    public StorageFile store(InputStream inputStream, String fileName, String contentType, Map<String, String> metaData) {
        return store(IdGenerate.uuid(), inputStream, fileName, contentType, metaData);
    }


    /**
     * 上传文件
     *
     * @param fileId      文件id
     * @param inputStream 输入流
     * @param fileName    文件名字
     * @param contentType 上下文类型
     * @param metaData
     * @return
     */
    private StorageFile store(String fileId, InputStream inputStream, String fileName, String contentType, Map<String, String> metaData) {
        StorageFile storageFile = null;
        try {
            MinioClient minioClient = getMinioClient();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            if (metaData == null) {
                metaData = new HashMap<String, String>();
            }
            String fileType = FileUtils.getFileExtension(fileName);
            fileId = fileId + "." + fileType;
            long fileSize = inputStream.available();

            metaData.put("fileId", fileId);
            metaData.put("fileName", fileName);
            metaData.put("fileType", fileType);
            // 使用putObject上传一个文件到存储桶中。

            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .contentType(contentType)
                            // userMetadata 中放置的属性要到  headers 中获取
                            .userMetadata(metaData)
                            // partSize值在5242880L和5368709120L中间
                            .stream(inputStream, fileSize, 5242880L)
                            .build()
            );
            String md5 = objectWriteResponse.etag();
            storageFile = new StorageFile();
            storageFile.setFileId(fileId);
            storageFile.setFileName(fileName);
            storageFile.setMd5(md5);
            storageFile.setLength(fileSize);
            storageFile.setContentType(contentType);
            storageFile.setUploadDate(new Date());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("MinioStorageServiceImpl -- store -- 上传minio异常：", e);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("MinioStorageServiceImpl -- store -- IOException = ", e);
            }
        }
        return storageFile;
    }


    /**
     * 获取文件外链
     *
     * @param fileId  文件名称
     * @param expires 过期时间 单位小时
     * @return url
     */
    @Override
    public String getExpiryUrlById(String fileId, Integer expires) {
        if (expires < 1 || expires > (7 * 24)) {
            logger.error("MinioStorageServiceImpl -- 过期时间必须在1小时和七天之间！");
            return "";
        }
        try {
            MinioClient minioClient = getMinioClient();
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(defaultBucket)
                            .object(fileId)
                            .expiry(expires, TimeUnit.HOURS)
                            .build());
        } catch (Exception e) {
            logger.error("MinioStorageServiceImpl -- getExpiryUrlById -- Exception = {e}", e);
            return null;
        }
    }



    /**
     * 获取文件外链（默认一小时）
     *
     * @param fileId  文件名称
     * @return url
     */
    @Override
    public String getExpiryUrlById(String fileId) {
        try {
            MinioClient minioClient = getMinioClient();
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(defaultBucket)
                            .object(fileId)
                            .expiry(1, TimeUnit.HOURS) // 按小时传参数
                            //.expiry(24 * 60 * 60) // 默认单位是秒
                            //.expiry(1, TimeUnit.DAYS) // 按天传参
                            .build());
        } catch (Exception e) {
            logger.error("MinioStorageServiceImpl -- getExpiryUrlById -- Exception = {e}", e);
            return null;
        }
    }


    /**
     * 根据文件id删除文件
     *
     * @param fileId 文件id
     */
    @Override
    public void deleteById(String fileId) {
        try {
            MinioClient minioClient = getMinioClient();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .build()
            );
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("MinioStorageServiceImpl -- deleteById -- Exception = {e}", e);
            }
        }
    }


}
