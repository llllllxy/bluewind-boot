package com.bluewind.boot.common.utils.http;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author liuxingyu01
 * @date 2021-09-11-22:55
 * @description HttpClient连接池公共方法
 * 注意点：1. http连接池不是万能的,过多的长连接会占用服务器资源,导致其他服务受阻
 *        2. http连接池只适用于请求是经常访问同一主机(或同一个接口)的情况下
 *        3. 并发数不高的情况下资源利用率低下
 * 使用连接池的优点：
 *        1. 复用http连接,省去了tcp的3次握手和4次挥手的时间,极大降低请求响应的时间
 *        2. 自动管理tcp连接,不用人为地释放/创建连接
 **/
public class HttpClientPoolUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientPoolUtils.class);

    private static PoolingHttpClientConnectionManager clientConnectionManager = null;
    // 它是线程安全的，所有的线程都可以使用它一起发送http请求
    // 私有化实例要加上volatile，防止jvm重排序，导致空指针
    private static volatile CloseableHttpClient httpClient = null;
    private static RequestConfig config = null;
    private static HttpRequestRetryHandler httpRequestRetryHandler = null;


    /**
     * 单例
     * 双检锁/双重校验锁, 防止httpClient实例多次
     */
    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClientPoolUtils.class) {
                if (httpClient == null) {
                    init();
                    httpClient = HttpClients.custom()
                            .setConnectionManager(clientConnectionManager) //连接管理器
                            //.setProxy(new HttpHost("myproxy", 8080))     //设置代理
                            .setDefaultRequestConfig(config)               //默认请求配置
                            .setRetryHandler(httpRequestRetryHandler)      //重试策略
                            .evictExpiredConnections() // 开启独立线程清理过期连接
                            .evictIdleConnections(5L, TimeUnit.SECONDS) // 清理空闲连接
                            .build();
                }
            }
        }
        return httpClient;
    }


    /**
     * 创建httpclient连接池并初始化
     */
    private static void init() {
        try {
            //添加对https的支持，该sslContext没有加载客户端证书
            // 如果需要加载客户端证书，请使用如下sslContext,其中KEYSTORE_FILE和KEYSTORE_PASSWORD分别是你的证书路径和证书密码
            //KeyStore keyStore  =  KeyStore.getInstance(KeyStore.getDefaultType()
            //FileInputStream instream =   new FileInputStream(new File(KEYSTORE_FILE));
            //keyStore.load(instream, KEYSTORE_PASSWORD.toCharArray());
            //SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore,KEYSTORE_PASSWORD.toCharArray())
            // .loadTrustMaterial(null, new TrustSelfSignedStrategy())
            //.build();

            // 这里设置信任所有证书
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .build();

            // 配置连接池
            clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 最大连接数
            clientConnectionManager.setMaxTotal(50);
            // 默认的每个路由的最大连接数
            clientConnectionManager.setDefaultMaxPerRoute(25);
            // 设置到某个路由的最大连接数，会覆盖defaultMaxPerRoute
            clientConnectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("127.0.0.1", 80)), 150);
            /** 此处解释下MaxtTotal和DefaultMaxPerRoute的区别：
                1、MaxtTotal是整个池子的大小；
                2、DefaultMaxPerRoute是根据连接到的主机对MaxTotal的一个细分；比如：
                MaxtTotal=400 DefaultMaxPerRoute=200
                只连接到http://www.abc.com时，到这个主机的并发最多只有200；而不是400；
                而连接到http://www.bac.com 和 http://www.ccd.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）；所以起作用的设置是DefaultMaxPerRoute
             */

            /**
             * socket配置（默认配置 和 某个host的配置）
             */
            SocketConfig socketConfig = SocketConfig.custom()
                    .setTcpNoDelay(true)     // 是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                    .setSoReuseAddress(true) // 是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                    .setSoTimeout(500)       // 接收数据的等待超时时间，单位ms
                    .setSoLinger(6)          // 关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
                    .setSoKeepAlive(true)    // 开启监视TCP连接是否有效
                    .build();
            clientConnectionManager.setDefaultSocketConfig(socketConfig);
            clientConnectionManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);


            /**
             * HTTP connection相关配置（默认配置 和 某个host的配置）
             * 一般不修改HTTP connection相关配置，故不设置
             */
            // 消息约束
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            // Http connection相关配置
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            //一般不修改HTTP connection相关配置，故不设置
            //connManager.setDefaultConnectionConfig(connectionConfig);
            //connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);


            // 配置请求的超时设置
            config = RequestConfig.custom()
                    .setConnectTimeout(5 * 1000)         // 连接超时时间
                    .setSocketTimeout(5 * 1000)          // 读超时时间（等待数据超时时间）
                    .setConnectionRequestTimeout(1000)    // 从池中获取连接超时时间
                    .setStaleConnectionCheckEnabled(true)// 检查是否为陈旧的连接，默认为true，类似testOnBorrow
                    .build();

            /**
             * 重试处理
             * 默认是重试3次
             */
            // 禁用重试(参数：retryCount、requestSentRetryEnabled)
            HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);
            // 自定义重试策略
            httpRequestRetryHandler = new HttpRequestRetryHandler() {
                public boolean retryRequest(IOException exception,
                                            int executionCount, HttpContext context) {
                    if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // Retry if the request is considered idempotent
                    // 如果请求类型不是HttpEntityEnclosingRequest，被认为是幂等的，那么就重试
                    // HttpEntityEnclosingRequest指的是有请求体的request，比HttpRequest多一个Entity属性
                    // 而常用的GET请求是没有请求体的，POST、PUT都是有请求体的
                    // Rest一般用GET请求获取数据，故幂等，POST用于新增数据，故不幂等
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
        CloseableHttpClient httpClient = getHttpClient();
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
                logger.info("HttpClientPoolUtils -- doGet -- url = {}", uri);
            }
            // 创建get请求
            httpGet = new HttpGet(uri);

            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 返回200，请求成功
                // 结果返回
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                if (logger.isInfoEnabled()) {
                    logger.info("HttpClientPoolUtils -- doGet -- 请求成功，返回数据： {}", result);
                }
            } else {
                logger.error("HttpClientPoolUtils -- doGet -- 请求失败，code：= {}", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error("HttpClientPoolUtils -- doGet -- Exception： {e}", e);
        } finally {
            // 释放连接
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
            // 回收链接到连接池
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    // 这个response到底需不需要释放呢，我也不知道
                    // response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }



    public static void main(String[] args) {

        String ueueueu = doGet("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww18ca1c72a196320c&corpsecret=3yUH3k2I87x6strRkRlXA5qVXUwNgQ3TW2O64epMtqk", null);
        logger.info("返回结果ueueueu：" + ueueueu);


    }
}
