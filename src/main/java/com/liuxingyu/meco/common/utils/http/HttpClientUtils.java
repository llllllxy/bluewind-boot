package com.liuxingyu.meco.common.utils.http;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = null;
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

            HttpResponse response = httpClient.execute(httpGet);
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
        HttpClient httpClient = HttpClientBuilder.create().build();
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
            HttpResponse response = httpClient.execute(httpPost);
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
            if (null != httpPost) {
                // 释放连接
                httpPost.releaseConnection();
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

        HttpClient httpClient = HttpClientBuilder.create().build();
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
                httpPost.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
            }
            HttpResponse response = httpClient.execute(httpPost);
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
            if (null != httpPost) {
                // 释放连接
                httpPost.releaseConnection();
            }
        }
        return result;
    }


    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @return
     */
    public static String doDownload(String url, String filePath) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);

        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 设置连接超时时间，单位毫秒。
                .setConnectionRequestTimeout(1000) // 设置从connect Manager获取Connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
                .setSocketTimeout(5000).build(); // 请求获取数据的超时时间，单位毫秒。如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        httpget.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpget);
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
            if (null != httpget) {
                // 释放连接
                httpget.releaseConnection();
            }
        }
        return filePath;
    }


    public static void main(String[] args) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("id", UUID.randomUUID().toString());
//		String get = doGet("http://10.10.10.67/lcm/restful/ReceiveFinance/getTestTest", map);
//		logger.info("get请求调用成功，返回数据是：" + get);
//		String post = doPost("http://10.10.10.67/lcm/restful/ReceiveFinance/getTestTest", map);
//		logger.info("post调用成功，返回数据是：" + post);

//        String str = "{\"sites\":{\"site\":[{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"}]}}";
//        String json = doPostJson("http://10.110.1.210:18070/crm_m/elockRestful", str);
//        logger.info("json发送成功，返回数据是：" + json);

        // String ueueueu = doGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww18ca1c72a196320c&corpsecret=3yUH3k2I87x6strRkRlXA5qVXUwNgQ3TW2O64epMtqk", null);
        // logger.info("返回结果ueueueu：" + ueueueu);

        // String sss = doDownload("http://upyun.lxyccc.top/halo/article_bg_26_civilization2_4k.jpg", "D:/44dsdsdsds4.jpg");

    }

}
