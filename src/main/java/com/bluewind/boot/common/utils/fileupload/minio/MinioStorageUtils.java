package com.bluewind.boot.common.utils.fileupload.minio;

import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.FileUtils;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.fileupload.model.StorageFile;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.common.utils.fileupload.model.StorageStreamFile;
import io.minio.*;
import io.minio.messages.Bucket;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @author liuxingyu01
 * @date 2021-04-15-9:32
 * @description minio工具类
 **/
public class MinioStorageUtils {
    final static Logger logger = LoggerFactory.getLogger(MinioStorageUtils.class);

    private static String defaultBucket = null;

    /**
     * 创建bucket
     *
     * @param bucket bucket名称
     * @return
     * @throws Exception
     */
    public static MinioClient getMinioClient(String bucket) throws Exception {
        MinioClient minioClient = null;
        if (StringUtils.isBlank(defaultBucket)) {
            defaultBucket = MinioProp.bucket;
        }

        if (StringUtils.isNotBlank(MinioProp.endpoint) && StringUtils.isNotBlank(MinioProp.accesskey) && StringUtils.isNotBlank(MinioProp.secretKey)) {
            minioClient = MinioClient.builder()
                    .endpoint(MinioProp.endpoint)
                    .credentials(MinioProp.accesskey, MinioProp.secretKey)
                    .build();
            if (StringUtils.isBlank(bucket)) {
                bucket = defaultBucket;
            }
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        }
        return minioClient;
    }


    /**
     * 获取全部bucket
     */
    public static List<Bucket> getAllBuckets() {
        try {
            MinioClient minioClient = getMinioClient(null);
            return minioClient.listBuckets();
        } catch (Exception e) {
            logger.error("MinioUtils -- getAllBuckets -- Exception = {e}", e);
            return null;
        }
    }

    /**
     * 根据bucketName获取桶信息
     *
     * @param bucketName bucket名称
     */
    public static Optional<Bucket> getBucket(String bucketName) {
        try {
            MinioClient minioClient = getMinioClient(null);
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (Exception e) {
            logger.error("MinioUtils -- getBucket -- Exception = {e}", e);
            return null;
        }
    }

    /**
     * 根据bucketName删除桶信息
     *
     * @param bucketName bucket名称
     */
    public static void removeBucket(String bucketName) {
        try {
            MinioClient minioClient = getMinioClient(null);
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            logger.error("MinioUtils -- removeBucket -- Exception = {e}", e);
        }
    }


    /**
     * 获取文件外链
     *
     * @param fileId  文件名称
     * @param expires 过期时间 <=7
     * @return url
     */
    public static String getExpiryUrlById(String fileId, Integer expires) {
        try {
            MinioClient minioClient = getMinioClient(null);
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .expiry(expires)
                            .build());
        } catch (Exception e) {
            logger.error("MinioUtils -- getObjectURL -- Exception = {e}", e);
            return null;
        }
    }


    /**
     * 获取文件
     *
     * @param fileId
     * @return
     */
    public static StorageStreamFile findById(String fileId) {
        StorageStreamFile storageStreamFile = null;
        try {
            MinioClient minioClient = getMinioClient(null);
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
                storageStreamFile.setMetaData(JsonTool.getMapFromJsonString(Extendinfo));
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
                logger.error("MinioUtils - findById - 下载文件异常：", e);
            }
        }
        return storageStreamFile;
    }


    public static StorageFile store(InputStream inputStream, String fileName) {
        return store(inputStream, fileName, null, null);
    }

    public static StorageFile store(InputStream inputStream, String fileName, String contentType) {
        return store(inputStream, fileName, contentType, null);
    }

    public static StorageFile store(InputStream inputStream, String fileName, Map<String, String> map) {
        return store(inputStream, fileName, "application/octet-stream", map);
    }

    public static StorageFile store(InputStream inputStream, String fileName, String contentType, Map<String, String> map) {
        return store(IdGenerate.uuid(), inputStream, fileName, contentType, map);
    }


    /**
     * 上传文件
     *
     * @param fileId      文件id
     * @param inputStream 输入流
     * @param fileName    文件名字
     * @param contentType 上下文类型
     * @param map
     * @return
     */
    private static StorageFile store(String fileId, InputStream inputStream, String fileName, String contentType, Map<String, String> map) {
        StorageFile storageFile = null;
        try {
            MinioClient minioClient = getMinioClient(null);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            if (map == null) {
                map = new HashMap<String, String>();
            }
            String fileType = FileUtils.getFileExtension(fileName);
            fileId = fileId + "." + fileType;
            long fileSize = inputStream.available();

            map.put("fileId", fileId);
            map.put("fileName", fileName);
            map.put("fileType", fileType);
            // 使用putObject上传一个文件到存储桶中。

            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .contentType(contentType)
                            // userMetadata 中放置的属性要到  headers 中获取
                            .userMetadata(map)
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
                logger.error("MinioUtils -- store -- 上传minio异常：", e);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("MinioUtils -- store -- IOException = ", e);
            }
        }
        return storageFile;
    }


    /**
     * 根据文件id删除文件
     *
     * @param fileId 文件id
     */
    public static void deleteById(String fileId) {
        try {
            MinioClient minioClient = getMinioClient(null);
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(defaultBucket)
                            .object(fileId)
                            .build()
            );
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("MinioUtils -- removeObject -- Exception = {e}", e);
            }
        }
    }


    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    public static BaseResult uploadFile(MultipartFile file, String fileName) {
        // 判断上传文件是否为空
        if (null == file || 0 == file.getSize()) {
            return BaseResult.failure("上传文件不能为空");
        }
        try {
            if (StringUtils.isBlank(fileName)) {
                fileName = file.getOriginalFilename(); // 原始文件名
            }
            // 开始上传
            if (logger.isInfoEnabled()) {
                logger.info("MinioUtils -- uploadFile -- ContentType = {}", file.getContentType());
            }
            StorageFile storageFile = store(file.getInputStream(), fileName, file.getContentType());
            return BaseResult.success("文件上传成功！", MinioProp.endpoint + "/" + MinioProp.bucket + "/" + storageFile.getFileId());
        } catch (Exception e) {
            logger.error("MinioUtils -- uploadFile -- Exception = {e}", e);
        }
        return BaseResult.failure("文件上传失败！");
    }


    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @return
     */
    public static BaseResult uploadFile(InputStream inputStream, String fileName) {
        // 判断上传文件是否为空
        if (null == inputStream) {
            return BaseResult.failure("上传文件不能为空");
        }
        try {
            StorageFile storageFile = store(inputStream, fileName, "application/octet-stream");
            return BaseResult.success("文件上传成功！", MinioProp.endpoint + "/" + MinioProp.bucket + "/" + storageFile.getFileId());
        } catch (Exception e) {
            logger.error("MinioUtils -- uploadFile -- Exception = {e}", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("MinioUtils -- uploadFile -- IOException = ", e);
            }
        }
        return BaseResult.failure("文件上传失败！");
    }


}
