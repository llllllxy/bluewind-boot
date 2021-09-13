package com.bluewind.boot.common.utils.fileupload.alioss;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.FileUtils;
import com.bluewind.boot.common.utils.fileupload.model.StorageFile;
import com.bluewind.boot.common.utils.fileupload.model.StorageStreamFile;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.lang.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-05-11-18:51
 * @description 阿里云OSS文件上传   --   具体参考官方文档：https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.947.40fc53381OxXGR
 **/
public class OssStorageUtils {
    final static Logger logger = LoggerFactory.getLogger(OssStorageUtils.class);

    private static OSS getOssClient() {
        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(200);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(10000);
        return new OSSClientBuilder().build(OssProp.endPoint, OssProp.accessKeyId, OssProp.accessKeySecret, conf);
    }


    /**
     * 根据文件id获取文件
     *
     * @param fileId
     * @return
     */
    public static StorageStreamFile findById(String fileId) {
        OSS ossClient = getOssClient();
        StorageStreamFile ssf = new StorageStreamFile();
        OSSObject ossObject = ossClient.getObject(OssProp.bucketName, fileId);
        ssf.setInputStream(ossObject.getObjectContent());
        ssf.setFileId(fileId);
        ObjectMetadata objectMetadata = ossObject.getObjectMetadata();
        Map<String, String> userMap = objectMetadata.getUserMetadata();
        for (String key : userMap.keySet()) {
            String value = userMap.get(key);
            if (StringUtils.isNotBlank(value)) {
                value = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
                userMap.put(key, value);
            }
        }
        ssf.setMetaData(userMap);
        if (userMap.containsKey("fileName") && StringUtils.isNotBlank(userMap.get("fileName"))) {
            ssf.setFileName(userMap.get("fileName"));
        } else {
            ssf.setFileName(fileId);
        }
        ssf.setMd5(objectMetadata.getContentMD5());
        ssf.setContentType(objectMetadata.getContentType());
        ssf.setLength(objectMetadata.getContentLength());
        ssf.setUploadDate(objectMetadata.getLastModified());
        // 关闭OSSClient。关闭之后流读取不到了
        // ossClient.shutdown();
        return ssf;
    }


    /**
     * 文件存储
     *
     * @param is
     * @param filename
     * @return
     */
    public static StorageFile store(InputStream is, String filename) {
        return store(is, filename, null, null);
    }

    /**
     * 文件存储
     *
     * @param is
     * @param filename
     * @param contentType
     * @return
     */
    public static StorageFile store(InputStream is, String filename, String contentType) {
        return store(is, filename, contentType, null);
    }

    /**
     * @param is
     * @param filename
     * @param metaData
     * @return
     */
    public static StorageFile store(InputStream is, String filename, Map<String, String> metaData) {
        return store(is, filename, null, metaData);
    }

    /**
     * 文件存储
     *
     * @param is
     * @param filename
     * @param contentType
     * @param metaData
     * @return
     */
    public static StorageFile store(InputStream is, String filename, String contentType, Map<String, String> metaData) {
        String key = IdGenerate.uuid();
        return store(key, is, filename, contentType, metaData);
    }


    public static StorageFile store(String fileId, InputStream is, String filename, String contentType, Map<String, String> metaData) {
        StorageFile sf = null;
        OSS ossClient = getOssClient();
        try {
            if (metaData == null) {
                metaData = new HashMap<>();
            }
            for (String key : metaData.keySet()) {
                byte[] by = metaData.get(key).getBytes(StandardCharsets.UTF_8);
                String base64String = Base64.getEncoder().encodeToString(by);
                metaData.put(key, base64String);
            }
            String fileType = FileUtils.getFileExtension(filename);
            fileId = fileId + "." + fileType;

            byte[] by2 = filename.getBytes(StandardCharsets.UTF_8);
            metaData.put("fileName", Base64.getEncoder().encodeToString(by2));
            long isLength = 0;
            try {
                isLength = is.available();
            } catch (IOException e) {
                logger.error("获取输入流信息出错", e);
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(isLength);
            objectMetadata.setUserMetadata(metaData);
            if (contentType != null) {
                objectMetadata.setContentType(contentType);
            }

            // 存储桶不存在就创建
            if (!ossClient.doesBucketExist(OssProp.bucketName)) {
                ossClient.createBucket(OssProp.bucketName);
                CreateBucketRequest cbr = new CreateBucketRequest(OssProp.bucketName);
                cbr.setCannedACL(CannedAccessControlList.Private);
                ossClient.createBucket(cbr);
            }

            ossClient.putObject(OssProp.bucketName, fileId, is, objectMetadata);
            sf = new StorageFile();
            sf.setLength(isLength);
            sf.setFileId(fileId);
            sf.setFileName(filename);
            sf.setUploadDate(new Date());
            sf.setContentType(contentType);
            sf.setMetaData(metaData);

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("OssStorageUtils -- store -- 上传文件异常：", e);
            }
        } finally {
            try {
                ossClient.shutdown();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("OssStorageUtils -- store -- IOException = ", e);
            }
        }
        return sf;
    }


    /**
     * 根据文件id，获取临时文件访问url
     *
     * @param fileId
     * @return
     */
    public static String getExpiryUrlById(String fileId) {
        OSS ossClient = getOssClient();
        URL url = null;
        try {
            // 设置URL过期时间为1小时
            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
            GeneratePresignedUrlRequest generatePresignedUrlRequest;
            generatePresignedUrlRequest = new GeneratePresignedUrlRequest(OssProp.bucketName, fileId);
            generatePresignedUrlRequest.setExpiration(expiration);
            url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        } catch (Exception e) {
            logger.error("OssStorageUtils -- getExpiryUrlById -- Exception = {e}", e);
        } finally {
            ossClient.shutdown();
        }
        if (url != null) {
            return url.toString();
        } else {
            return "";
        }
    }


    /**
     * 流式下载文件
     *
     * @param fileId
     * @return
     */
    public static byte[] getFileByteById(String fileId) {
        OSS ossClient = getOssClient();
        OSSObject ossObject = ossClient.getObject(OssProp.bucketName, fileId);
        InputStream inputStream = ossObject.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            logger.error("OssStorageUtils -- getFileByteById -- Exception = {e}", e);
            return null;
        } finally {
            ossClient.shutdown();
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("OssStorageUtils -- getFileByteById -- IOException - e= ", e);
            }
        }
    }


    /**
     * 根据文件id删除文件
     *
     * @param fileId
     * @return
     */
    public void deleteById(String fileId) {
        OSS ossClient = getOssClient();
        ossClient.deleteObject(OssProp.bucketName, fileId);
        ossClient.shutdown();
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
                logger.info("OssStorageUtils -- uploadFile -- ContentType = {}", file.getContentType());
            }
            StorageFile storageFile = store(file.getInputStream(), fileName, file.getContentType());
            return BaseResult.success("文件上传成功！", getExpiryUrlById(storageFile.getFileId()));
        } catch (Exception e) {
            logger.error("OssStorageUtils -- uploadFile -- Exception = {e}", e);
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
            return BaseResult.success("文件上传成功！", getExpiryUrlById(storageFile.getFileId()));
        } catch (Exception e) {
            logger.error("OssStorageUtils -- uploadFile -- Exception = {e}", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("OssStorageUtils -- uploadFile -- IOException = ", e);
            }
        }
        return BaseResult.failure("文件上传失败！");
    }

}
