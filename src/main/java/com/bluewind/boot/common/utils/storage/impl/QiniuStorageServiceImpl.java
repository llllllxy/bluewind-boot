package com.bluewind.boot.common.utils.storage.impl;

import com.bluewind.boot.common.utils.FileUtils;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.storage.api.StorageService;
import com.bluewind.boot.common.utils.storage.model.StorageFile;
import com.bluewind.boot.common.utils.storage.model.StorageStreamFile;
import com.bluewind.boot.common.utils.storage.properties.QiniuProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuxingyu01
 * @date 2022-04-22 17:01
 * @description 七牛云对象存储实现
 **/
@Service
@ConditionalOnProperty(prefix = "bluewind", name = "storage-type", havingValue = "qiniu")
public class QiniuStorageServiceImpl implements StorageService {
    final static Logger logger = LoggerFactory.getLogger(QiniuStorageServiceImpl.class);

    @Autowired
    private QiniuProperties qiniuProperties;

    private String getUpToken() {
        // 身份认证
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuProperties.getBucketName());
        return upToken;
    }


    /**
     * 通过fileId获取文件
     *
     * @param fileId 文件ID
     * @return
     */
    @Override
    public StorageStreamFile findById(String fileId) {
        StorageStreamFile storageStreamFile = null;
        try {
            String url = getExpiryUrlById(fileId);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            okhttp3.Response resp = client.newCall(request).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream in = body.byteStream();
                storageStreamFile = new StorageStreamFile();
                storageStreamFile.setFileId(fileId);
                storageStreamFile.setInputStream(in);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("QiniuStorageServiceImpl - findById - 下载文件异常：", e);
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
            if (metaData == null) {
                metaData = new HashMap<>();
            }
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            // 获取文件扩展名，拼接在uuid后面，作为fileId
            String fileType = FileUtils.getFileExtension(fileName);
            fileId = fileId + "." + fileType;
            long fileSize = inputStream.available();

            // 构造一个带指定Zone对象的配置类,Zone.huabei()代表华北地区
            Configuration config = new Configuration(Region.huanan());
            // ...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(config);

            // 上传自定义参数，自定义参数名称需要以 x:开头
            StringMap params = new StringMap();
            params.put("x:fileId", fileId);
            params.put("x:fileName", fileName);
            params.put("x:fileType", fileType);
            // 便利map里的内容
            metaData.forEach((k, v) -> {
                params.put("x:" + k, v);
            });
            Response response = uploadManager.put(inputStream, fileId, getUpToken(), params, null);

            String hash = "";
            if (response.statusCode == 200) {
                // 解析上传成功的结果
                // 返回示例：{"hash":"FumiAjv1zwzLL9ER9ZnZSxNcOVnK","key":"ca9faefe1d4940bab0b047818500ee43.jpg","x:fileName":"w92929uqwdqiwiieqwe828ewkeq.jpg","x:fileType":"jpg"}
                String bodyString = response.bodyString();
                Map resultMap = JsonTool.parseMap(bodyString);
                fileId = (String) resultMap.get("key");
                hash = (String) resultMap.get("hash");
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error("QiniuStorageServiceImpl -- store -- 上传异常：{}", response.bodyString());
                }
            }

            String md5 = hash;
            storageFile = new StorageFile();
            storageFile.setFileId(fileId);
            storageFile.setFileName(fileName);
            storageFile.setMd5(md5);
            storageFile.setLength(fileSize);
            storageFile.setContentType(contentType);
            storageFile.setUploadDate(new Date());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("QiniuStorageServiceImpl -- store -- 上传异常：", e);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("QiniuStorageServiceImpl -- store -- IOException = ", e);
            }
        }
        return storageFile;
    }


    /**
     * 获取私链链接（默认一小时有效时长）
     *
     * @param fileId
     * @return
     */
    public String getExpiryUrlById(String fileId) {
        // 构造下载 URL
        String downloadUrl = qiniuProperties.getEndPoint() + fileId;
        long time = System.currentTimeMillis();
        // 获取60分钟后的时间
        time += 60 * 1000 * 60;
        String expireTime = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
        expireTime = Date2TimeStamp(expireTime, "yyyyMMddHHmmss");
        // 为下载 URL 加上过期时间 e 参数，Unix时间戳
        downloadUrl = downloadUrl + "?e=" + expireTime;
        // 对上一步得到的 URL字符串计算HMAC-SHA1签名（假设访问密钥（AK/SK）是 MY_SECRET_KEY），并对结果做URL安全的Base64编码
        byte[] sign = HmacSHA1Encrypt(downloadUrl, qiniuProperties.getSecretKey());
        String signStr = safeUrlBase64Encode(sign);
        // 将访问密钥（AK/SK）（假设是 MY_ACCESS_KEY）与上一步计算得到的结果用英文符号 : 连接起来，得到token
        String token = qiniuProperties.getAccessKey() + ":" + signStr;
        // 将上述 Token 拼接到含过期时间参数 e 的 DownloadUrl 之后，作为最后的下载 URL
        downloadUrl = downloadUrl + "&token=" + token;
        if (logger.isDebugEnabled()) {
            logger.debug("QiniuStorageServiceImpl -- getExpiryUrlById -- downloadUrl= " + downloadUrl);
        }
        return downloadUrl;
    }


    /**
     * 获取私链链接，自定义时长，单位小时
     *
     * @param fileId
     * @param expires
     * @return
     */
    public String getExpiryUrlById(String fileId, Integer expires) {
        // 构造下载 URL
        String downloadUrl = qiniuProperties.getEndPoint() + fileId;
        long time = System.currentTimeMillis();
        // 算出过期时间
        time += 60 * 1000 * 60 * expires;
        String expireTime = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
        expireTime = Date2TimeStamp(expireTime, "yyyyMMddHHmmss");
        // 为下载 URL 加上过期时间 e 参数，Unix时间戳
        downloadUrl = downloadUrl + "?e=" + expireTime;
        // 对上一步得到的 URL字符串计算HMAC-SHA1签名（假设访问密钥（AK/SK）是 MY_SECRET_KEY），并对结果做URL安全的Base64编码
        byte[] sign = HmacSHA1Encrypt(downloadUrl, qiniuProperties.getSecretKey());
        String signStr = safeUrlBase64Encode(sign);
        // 将访问密钥（AK/SK）（假设是 MY_ACCESS_KEY）与上一步计算得到的结果用英文符号 : 连接起来，得到token
        String token = qiniuProperties.getAccessKey() + ":" + signStr;
        // 将上述 Token 拼接到含过期时间参数 e 的 DownloadUrl 之后，作为最后的下载 URL
        downloadUrl = downloadUrl + "&token=" + token;
        if (logger.isDebugEnabled()) {
            logger.debug("QiniuStorageServiceImpl -- getExpiryUrlById -- downloadUrl= " + downloadUrl);
        }
        return downloadUrl;
    }



    /**
     * 根据文件id删除文件
     *
     * @param fileId 文件id
     */
    @Override
    public void deleteById(String fileId) {
        try {
            // 构造一个带指定Zone对象的配置类,Zone.huabei()代表华北地区
            Configuration config = new Configuration(Region.huanan());
            // 身份认证
            Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
            BucketManager bucketManager = new BucketManager(auth, config);

            Response response = bucketManager.delete(qiniuProperties.getBucketName(), fileId);
            // 如果需要重试，则重试三次
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(qiniuProperties.getBucketName(), fileId);
            }
            if (response.statusCode == 200) {
                if (logger.isInfoEnabled()) {
                    logger.info("QiniuStorageServiceImpl -- deleteById -- 删除文件成功 = {}", fileId);
                }
            } else {
                if (logger.isErrorEnabled()) {
                    logger.error("QiniuStorageServiceImpl -- deleteById -- 删除文件失败 = {}", fileId);
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("QiniuStorageServiceImpl -- deleteById -- Exception = {e}", e);
            }
        }
    }


    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) {
        String MAC_NAME = "HmacSHA1";
        String ENCODING = "UTF-8";
        try {
            if (StringUtils.isNotBlank(encryptText) && StringUtils.isNotBlank(encryptKey)) {
                byte[] data = encryptKey.getBytes(ENCODING);
                //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
                SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
                //生成一个指定 Mac 算法 的 Mac 对象
                Mac mac = Mac.getInstance(MAC_NAME);
                //用给定密钥初始化 Mac 对象
                mac.init(secretKey);
                byte[] text = encryptText.getBytes(ENCODING);
                //完成 Mac 操作
                return mac.doFinal(text);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("HMACSHA1 -- Exception -- e= ", e);
            return null;
        }
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (StringUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        return nowTimeStamp;
    }


    /**
     * 获取URL安全的Base64编码
     *
     * @param data
     * @return
     */
    public static String safeUrlBase64Encode(byte[] data) {
        String encodeBase64 = Base64.getEncoder().encodeToString(data);
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("=", "");
        return safeBase64Str;
    }

    /**
     * 安全的url BASE64编码反向转换为byte[]
     *
     * @param safeBase64Str
     * @return
     */
    public static byte[] safeUrlBase64Decode(String safeBase64Str) {
        String base64Str = safeBase64Str.replace('-', '+');
        base64Str = base64Str.replace('_', '/');
        int mod4 = base64Str.length() % 4;
        if (mod4 > 0) {
            base64Str = base64Str + "====".substring(mod4);
        }
        return Base64.getDecoder().decode(base64Str);
    }

}
