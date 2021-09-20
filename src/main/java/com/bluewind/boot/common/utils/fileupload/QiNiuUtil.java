package com.bluewind.boot.common.utils.fileupload;

import com.bluewind.boot.common.utils.FileUtils;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.io.PropertiesFileUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import com.qiniu.util.StringMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuxingyu01
 * @date 2020-09-12-13:58
 * 七牛云上传工具类
 **/
public class QiNiuUtil {
    final static Logger logger = LoggerFactory.getLogger(QiNiuUtil.class);


    /**
     * 取配置文件里的配置信息
     */
    private static final String QINIU_IMG_URL_PRE = PropertiesFileUtil.getInstance("qiniu").get("qiniu_img_url_pre"); // 外链域名地址
    private static final String ACCESS_KEY = PropertiesFileUtil.getInstance("qiniu").get("access_key"); // AccessKey
    private static final String SECRET_KEY = PropertiesFileUtil.getInstance("qiniu").get("secret_key"); // SecretKey
    private static final String BUCKET_NAME = PropertiesFileUtil.getInstance("qiniu").get("bucketname"); // 存储空间名称


    /**
     * 以文件的形式上传
     *
     * @param file     文件
     * @param fileName 文件名字
     * @return: java.lang.String
     */
    public static String upload(File file, String fileName) {
        // 构造一个带指定Zone对象的配置类,Zone.huabei()代表华北地区
        Configuration config = new Configuration(Region.huanan());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(config);
        // 身份认证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET_NAME);
        try {
            String fileType = FileUtils.getFileExtension(fileName);
            // 上传自定义参数，自定义参数名称需要以 x:开头
            StringMap params = new StringMap();
            params.put("x:fileName", fileName);
            params.put("x:fileType", fileType);
            String fileId = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;
            Response response = uploadManager.put(file, fileId, upToken, params, null, false);
            if (response.statusCode == 200) {
                // 解析上传成功的结果
                String bodyString = response.bodyString();
                Map map = JsonTool.getMapFromJsonString(bodyString);
                String key = (String) map.get("key");
                if (StringUtils.isNotEmpty(key)) {
                    return QINIU_IMG_URL_PRE + key;
                }
            } else {
                return "上传图片失败! code=" + response.statusCode;
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error("QiNiuUtil -- uploadQiniuWithFile -- ex= " + r);
        }
        return null;
    }


    /**
     * 以流的形式上传
     *
     * @param inputStream 文件流
     * @param fileName    文件名字
     * @return: java.lang.String
     */
    public static String upload(InputStream inputStream, String fileName) {
        // 构造一个带指定Zone对象的配置类,Zone.huabei()代表华北地区
        Configuration config = new Configuration(Region.huanan());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(config);
        // 身份认证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET_NAME);
        try {
            String fileType = FileUtils.getFileExtension(fileName);
            // 上传自定义参数，自定义参数名称需要以 x:开头
            StringMap params = new StringMap();
            params.put("x:fileName", fileName);
            params.put("x:fileType", fileType);
            String fileId = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;
            Response response = uploadManager.put(inputStream, fileId, upToken, params, null);
            if (response.statusCode == 200) {
                //解析上传成功的结果
                String bodyString = response.bodyString();
                Map map = JsonTool.getMapFromJsonString(bodyString);
                String key = (String) map.get("key");
                if (StringUtils.isNotEmpty(key)) {
                    return QINIU_IMG_URL_PRE + key;
                }
            } else {
                return "上传图片失败! code=" + response.statusCode;
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error("QiNiuUtil -- uploadQiniuWithInputStream -- ex= " + r);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("QiNiuUtil -- uploadQiniuWithInputStream -- IOException - e= ", e);
            }
        }
        return null;
    }


    /**
     * 以Base64的形式上传
     *
     * @param base64   图片base64
     * @param fileName 文件名字
     * @return: java.lang.String
     */
    public static String upload(String base64, String fileName) {
        // 构造一个带指定Zone对象的配置类,Zone.huabei()代表华北地区
        Configuration config = new Configuration(Region.huanan());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(config);
        // 身份认证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET_NAME);
        ByteArrayInputStream inputStream = null;
        try {
            String fileType = FileUtils.getFileExtension(fileName);
            // 上传自定义参数，自定义参数名称需要以 x:开头
            StringMap params = new StringMap();
            params.put("x:fileName", fileName);
            params.put("x:fileType", fileType);
            String fileId = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;
            byte[] bytes = Base64.getDecoder().decode(base64);
            inputStream = new ByteArrayInputStream(bytes);
            Response response = uploadManager.put(inputStream, fileId, upToken, params, null);
            if (response.statusCode == 200) {
                // 解析上传成功的结果
                String bodyString = response.bodyString();
                Map map = JsonTool.getMapFromJsonString(bodyString);
                String key = (String) map.get("key");
                if (StringUtils.isNotEmpty(key)) {
                    return QINIU_IMG_URL_PRE + key;
                }
            } else {
                return "上传图片失败! code=" + response.statusCode;
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error("QiNiuUtil -- uploadQiniuWithBase64 -- ex= " + r);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("QiNiuUtil -- uploadQiniuWithBase64 -- IOException - e= ", e);
            }
        }
        return null;
    }


    /**
     * 删除一个文件
     *
     * @param key，也就是文件名
     * @return: java.lang.String
     */
    public static boolean delete(String key) {
        // 构造一个带指定Zone对象的配置类,Zone.huabei()代表华北地区
        Configuration config = new Configuration(Region.huanan());
        // 身份认证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, config);
        try {
            Response response = bucketManager.delete(BUCKET_NAME, key);
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(BUCKET_NAME, key);
            }
            if (response.statusCode == 200) {
                return true;
            } else {
                return false;
            }
        } catch (QiniuException es) {
            Response r = es.response;
            logger.error("QiNiuUtil -- delete -- ex= " + r);
            return false;
        }
    }


    /**
     * 下载文件
     *
     * @param url 文件在七牛云服务器上公链或者私链
     * @return
     */
    public static byte[] download(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            okhttp3.Response resp = client.newCall(request).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream inputStream = body.byteStream();
                ByteArrayOutputStream writer = new ByteArrayOutputStream();
                byte[] buff = new byte[1024 * 2];
                int len = 0;
                try {
                    while ((len = inputStream.read(buff)) != -1) {
                        writer.write(buff, 0, len);
                    }
                } catch (Exception e) {
                    logger.error("QiNiuUtil -- download -- e= ", e);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                return writer.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 私链下载文件
     *
     * @param key
     * @return byte[]
     */
    public static byte[] downloadPrivate(String key) {
        String downloadUrl = getPrivateUrl(key);
        if (logger.isDebugEnabled()) {
            logger.debug("QiNiuUtil -- downloadPrivateByte -- downloadUrl= " + downloadUrl);
        }
        return download(downloadUrl);
    }

    /**
     * 私链下载文件
     *
     * @param key
     * @return base64
     */
    public static String downloadPrivateBase64(String key) {
        String downloadUrl = getPrivateUrl(key);
        if (logger.isDebugEnabled()) {
            logger.debug("QiNiuUtil -- downloadPrivateBase64 -- downloadUrl= " + downloadUrl);
        }
        return Base64.getEncoder().encodeToString(download(downloadUrl));
        // 测试文件生成（成功）
        // File file = FileUtils.Base64ToImage(base64Str,"D:/","893ab9c335cb47wqewqa956840f75d46c.jpg");
    }


    /**
     * 获取公链链接
     *
     * @param key
     * @return
     */
    public static String getPublicUrl(String key) throws UnsupportedEncodingException {
        String encodedFileName = URLEncoder.encode(key, "utf-8").replace("+", "%20");
        return String.format("%s/%s", BUCKET_NAME, encodedFileName);
    }


    /**
     * 获取私链链接
     *
     * @param key
     * @return
     */
    public static String getPrivateUrl(String key) {
        // 构造下载 URL
        String downloadUrl = QINIU_IMG_URL_PRE + key;
        long time = System.currentTimeMillis();
        // 获取30分钟后的时间
        time += 30 * 1000 * 60;
        String expireTime = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
        expireTime = Date2TimeStamp(expireTime, "yyyyMMddHHmmss");
        // 为下载 URL 加上过期时间 e 参数，Unix时间戳
        downloadUrl = downloadUrl + "?e=" + expireTime;
        // 对上一步得到的 URL字符串计算HMAC-SHA1签名（假设访问密钥（AK/SK）是 MY_SECRET_KEY），并对结果做URL安全的Base64编码
        byte[] sign = HmacSHA1Encrypt(downloadUrl, SECRET_KEY);
        String signStr = safeUrlBase64Encode(sign);
        // 将访问密钥（AK/SK）（假设是 MY_ACCESS_KEY）与上一步计算得到的结果用英文符号 : 连接起来，得到token
        String token = ACCESS_KEY + ":" + signStr;
        // 将上述 Token 拼接到含过期时间参数 e 的 DownloadUrl 之后，作为最后的下载 URL
        downloadUrl = downloadUrl + "&token=" + token;
        if (logger.isDebugEnabled()) {
            logger.debug("QiNiuUtil -- getPrivateUrl -- downloadUrl= " + downloadUrl);
        }
        return downloadUrl;
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


    public static void main(String[] args) throws FileNotFoundException {

//        System.out.println("哈哈哈哈哈哈");
//        File file = new File("D:/icon-map-sign-red.png");
//        System.out.println(uploadQiniuWithFile(file,"o9505kdkd883jdmmmdasmd.png"));

//        File file = new File("D:/logo_sm.png");
//        InputStream in = new uploadQiniuWithInputStream(file);
//        System.out.println(upload2Qiniu(in,"logo_sm.jpg"));

//        String base64 = "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";
//        System.out.println(uploadQiniuWithBase64(base64,"wuieuqwdqiwiieqwe828ewkeq.jpg"));

//        System.out.println(delete("893ab9c335cb472cb0a956840f75d46c.jpg"));

//        try {
//            byte[] ss = downloadPrivateByte("893ab9c335cb472cb0a956840f75d46c.jpg");
//
//        } catch (Exception e) {
//
//        }

    }
}
