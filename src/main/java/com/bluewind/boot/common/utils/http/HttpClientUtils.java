package com.bluewind.boot.common.utils.http;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

/**
 * @author liuxingyu01
 * @date 2020-12-22-14:39
 * @description httpClient工具类
 **/
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);


    /**
     * @param url    请求路径
     * @param params 参数
     * @Title: doGet
     * @Description: get方式
     * @author liuxingyu01
     */
    public static String doGet(String url, Map<String, String> params) {
        // 返回结果
        String result = "";
        // 创建HttpClient对象
        CloseableHttpClient httpClient = createHttpClient(url);
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            // 拼接参数,可以用URIBuilder,也可以直接拼接在？传值，拼在url后面，如下--httpGet = new
            // HttpGet(uri+"?id=123");
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                    // 或者用 setParameter
                    // 顺便说一下不同(setParameter会覆盖同名参数的值，addParameter则不会)
                    // uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = uriBuilder.build();
            if (logger.isInfoEnabled()) {
                logger.info("HttpClientUtils -- doGet -- url = {}", uri);
            }
            // 创建get请求
            httpGet = new HttpGet(uri);
            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000) // 设置连接超时时间，单位毫秒。
                    .setConnectionRequestTimeout(1000) // 设置从connect Manager获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                    .setSocketTimeout(5000).build(); // 请求获取数据的超时时间，单位毫秒。如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 返回200，请求成功
                // 结果返回
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                if (logger.isInfoEnabled()) {
                    logger.info("HttpClientUtils -- doGet -- 请求成功，返回数据： {}", result);
                }
            } else {
                logger.error("HttpClientUtils -- doGet -- 请求失败，code：= {}", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpClientUtils -- doGet -- Exception： {e}", e);
        } finally {
            // 释放连接
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
            // 回收链接到连接池
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * @param url
     * @param params
     * @return
     * @Title: doPost
     * @Description: post请求 key-value形式
     * @author liuxingyu01
     */
    public static String doPost(String url, Map<String, String> params) {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = createHttpClient(url);
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间，单位毫秒。
                .setConnectionRequestTimeout(1000) // 设置从connect Manager获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                .setSocketTimeout(5000).build(); // 请求获取数据的超时时间，单位毫秒。如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        httpPost.setConfig(requestConfig);

        try { // 参数键值对
            if (null != params && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                NameValuePair pair = null;
                for (String key : params.keySet()) {
                    pair = new BasicNameValuePair(key, params.get(key));
                    pairs.add(pair);
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                if (logger.isInfoEnabled()) {
                    logger.info("HttpClientUtils -- doPost -- 请求成功，返回数据： {}", result);
                }
            } else {
                logger.error("HttpClientUtils -- doPost -- 请求失败，code：= {}", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpClientUtils -- doPost -- Exception： {e}", e);
        } finally {
            // 释放连接
            httpPost.releaseConnection();
            // 回收链接到连接池
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * @param url
     * @param jsonStr
     * @return 返回数据
     * @Title: doPostJson
     * @Description: post发送json字符串
     * @author liuxingyu01
     */
    public static String doPostJson(String url, String jsonStr) {
        String result = "";

        CloseableHttpClient httpClient = createHttpClient(url);
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间，单位毫秒。
                .setConnectionRequestTimeout(1000) // 设置从connect Manager获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                .setSocketTimeout(5000).build(); // 请求获取数据的超时时间，单位毫秒。如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        httpPost.setConfig(requestConfig);

        try {
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (StringUtils.isNotBlank(jsonStr)) {
                StringEntity stringEntity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
            }
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                if (logger.isInfoEnabled()) {
                    logger.info("HttpClientUtils -- doPostJson -- 请求成功，返回数据： {}", result);
                }
            } else {
                logger.error("HttpClientUtils -- doPostJson -- 请求失败，code：= {}", response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            logger.error("HttpClientUtils -- doPostJson -- Exception： {e}", e);
        } finally {
            // 释放连接
            httpPost.releaseConnection();
            // 回收链接到连接池
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     *
     * @param url url
     * @param filePath 本地存储地址
     * @Title: doDownload
     * @Description: 下载文件
     * @author liuxingyu01
     */
    public static String doDownload(String url, String filePath) {
        CloseableHttpClient httpClient = createHttpClient(url);
        CloseableHttpResponse response = null;
        HttpGet httpget = new HttpGet(url);

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间，单位毫秒。
                .setConnectionRequestTimeout(1000) // 设置从connect Manager获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                .setSocketTimeout(5000).build(); // 请求获取数据的超时时间，单位毫秒。如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        httpget.setConfig(requestConfig);
        try {
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            //输入流转换文件
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(filePath);
            int BUFFER_SIZE = 1024;
            byte[] buf = new byte[BUFFER_SIZE];
            int size = 0;

            try {
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
                fos.flush();
            } catch (IOException | ClassCastException e) {
                logger.error("HttpClientUtils -- doDownload写入BufferedInputStream失败！ - {e}", e);
            } finally {
                try {
                    fos.close();
                    bis.close();
                } catch (IOException | NullPointerException e) {
                    logger.error("HttpClientUtils -- doDownload释放BufferedInputStream失败！ - {e}", e);
                }
            }
        } catch (ClientProtocolException e) {
            logger.error("HttpClientUtils -- doDownload - ClientProtocolException - {e}", e);
        } catch (IOException e) {
            logger.error("HttpClientUtils -- doDownload - IOException - {e}", e);
        } finally {
            // 释放连接
            httpget.releaseConnection();
            // 回收链接到连接池
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }


    /**
     * 根据url判断是http还是https
     * @param url
     * @return
     */
    public static CloseableHttpClient createHttpClient(String url) {
        String head = url.substring(0, 5);
        if (StringUtils.isNotBlank(head)) {
            if ("https".equals(head)) {
                return createSSLInsecureClient();
            } else {
                return HttpClientBuilder.create().build();
            }
        } else {
            return HttpClientBuilder.create().build();
        }
    }


    /**
     * 创建 SSL连接
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void main(String[] args) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("id", UUID.randomUUID().toString());
//		String get = doGet("http://10.10.10.67/lcm/restful/ReceiveFinance/getTestTest", map);
//		logger.info("get请求调用成功，返回数据是：" + get);
//		String post = doPost("http://10.10.10.67/lcm/restful/ReceiveFinance/getTestTest", map);
//		logger.info("post调用成功，返回数据是：" + post);

//        String access_token = doGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww18ca1c72a196320c&corpsecret=3yUH3k2I87x6strRkRlXA5qVXUwNgQ3TW2O64epMtqk", null);
//        logger.info("返回结果access_token：" + access_token);

        String token = "yDzs20PZHD_6pGUo_w1sZhnHnzeojsEOxVb0_rGFmEvp2mOzQ_LsaF_euo9jPJO8OleoJt5vNtaXkrJu8_IApeUmg3Mhs5uP5UmzvVkaN5lq9ggEDOUbCqBoJmWWlF-_v5NPFXFDcdNTKMnJ7L5shNLKRtbzamL-As9UzVfWGne3ZhoncVeEHL5jBpZotsgBjpgn1SzXsbfrG0VwfUT2Cw";
        String str = "{\"datetime\":1511971200,\"useridlist\":[\"leisure\",\"liuxingyu\"]}";
        String json = doPostJson("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckinoption?access_token=" + token, str);
        logger.info("json发送成功，返回数据是：" + json);



//        String sss = doDownload("http://upyun.lxyccc.top/halo/article_bg_26_civilization2_4k.jpg", "D:/44dsdsdsds4.jpg");



    }

}
