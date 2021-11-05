package com.bluewind.boot.common.utils.http;

import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxingyu01
 * @date 2021-02-14-22:20
 * @description OkHttp工具类
 **/
public class OkHttpUtils {
    final static Logger log = LoggerFactory.getLogger(OkHttpUtils.class);

    // 私有化示例要加上volatile，防止jvm重排序，导致空指针
    private static volatile OkHttpClient okHttpClient = null;

    // 单例禁止new实例化
    private OkHttpUtils() {

    }


    /**
     * 获取单例的线程池对象（懒汉式单例，有线程安全问题，所以加锁）
     * @return
     */
    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .build();
                }
            }
        }
        return okHttpClient;
    }


    /**
     * Get请求
     *
     * @param url URL地址
     * @return 返回结果
     */
    public static String get(String url) {
        String result = null;
        try {
            OkHttpClient okHttpClient = OkHttpUtils.getInstance();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() != null && response.isSuccessful()) {
                result = response.body().string();
            }
            log.info("OkHttp[Get}请求返回：{}", result);
            return result;
        } catch (Exception e) {
            log.error("OkHttp[Get]请求异常", e);
            return result;
        }
    }


    /**
     * Post请求 (key-value形式)
     *
     * @param url    URL地址
     * @param params 参数
     * @return 返回结果
     */
    public static String post(String url, Map<String, String> params) {
        String result = null;
        if (params == null) {
            params = new HashMap<>();
        }
        try {
            OkHttpClient okHttpClient = OkHttpUtils.getInstance();
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            // 添加参数
            log.info("Post - params：{}", params);
            for (Map.Entry<String, String> map : params.entrySet()) {
                String key = map.getKey();
                String value;
                if (map.getValue() == null) {
                    value = "";
                } else {
                    value = map.getValue();
                }
                formBodyBuilder.add(key, value);
            }
            FormBody formBody = formBodyBuilder.build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() != null && response.isSuccessful()) {
                result = response.body().string();
            }
            log.info("Post请求返回：{}", result);
            return result;
        } catch (Exception e) {
            log.error("OkHttp[Post]请求异常", e);
            return result;
        }
    }


    /**
     * Post请求 (body形式)
     *
     * @param url  URL地址
     * @param json body参数
     * @return 返回结果
     */
    public static String postJson(String url, String json) {
        String result = null;
        try {
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            OkHttpClient mOkHttpClient = OkHttpUtils.getInstance();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.body() != null && response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (Exception e) {
            log.error("OkHttpUtil - postJson - e：{e}", e);
        }
        return result;
    }


    /**
     * 上传文件请求（Post请求）
     *
     * @param url    URL地址
     * @param params 文件 key：参数名 value：文件 （可以多文件）
     * @return 返回结果
     */
    public static String upload(String url, Map<String, File> params) {
        String result = null;
        try {
            OkHttpClient okHttpClient = OkHttpUtils.getInstance();
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            for (Map.Entry<String, File> map : params.entrySet()) {
                String key = map.getKey();
                File file = map.getValue();
                if (file == null || (file.exists() && file.length() == 0)) {
                    continue;
                }
                multipartBodyBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
            RequestBody requestBody = multipartBodyBuilder.build();
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() != null) {
                result = response.body().string();
            }
            log.info("OkHttpUtil - upload请求返回：{}", result);
            return result;
        } catch (Exception e) {
            log.error("OkHttpUtil - [upload]请求异常", e);
            return result;
        }
    }


    /**
     * 下载文件请求（Get请求）
     *
     * @param url      URL地址
     * @param savePath 保存路径（包括文件名）
     * @return 文件保存路径
     */
    public static String download(String url, String savePath) {
        String result = null;
        try {
            OkHttpClient okHttpClient = OkHttpUtils.getInstance();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            File file = new File(savePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedSink sink = Okio.buffer((Okio.sink(file)));
            if (response.body() != null) {
                sink.writeAll(response.body().source());
            }
            sink.flush();
            sink.close();
            result = savePath;
            log.info("OkHttpUtil - Download请求返回：{}", result);
            return result;
        } catch (Exception e) {
            log.error("OkHttpUtil - [Download]请求异常", e);
            return result;
        }
    }



    /**
     * Get请求（异步请求示例，暂时）
     *
     * @param url URL地址
     * @return 返回结果
     */
    public static void asyncGet(String url) {
        try {
            OkHttpClient okHttpClient = OkHttpUtils.getInstance();
            Request request = new Request.Builder()
                    //.addHeader("token", "123") // 设置请求头
                    .url(url)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onResponse(Call call,Response response) throws IOException {
                    if (response.body() != null && response.isSuccessful()) {
                        String result = response.body().string();
                        log.info("OkHttp[asyncGet]请求结果：{}", result);
                    }
                }
                @Override
                public void onFailure(Call call, IOException e){

                }
            });

        } catch (Exception e) {
            log.error("OkHttp[asyncGet]请求异常", e);
        }
    }



    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", "1");
        map.put("username", "superadmin");

        String get = get("http://10.110.34.64:18098/ldm-lnode-server/sso/local/login?password=1&username=superadmin");
        log.info("get请求调用成功，返回数据是：" + get);
        String post = post("http://10.110.34.64:18098/ldm-lnode-server/sso/local/login", map);
        log.info("post调用成功，返回数据是：" + post);
        String json = postJson("http://10.110.34.64:18098/ldm-lnode-server/sso/local/login", "{\"name\":\"David\"}");
        log.info("json发送成功，返回数据是：" + json);

//        String ueueueu = get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww18ca1c72a196320c&corpsecret=3yUH3k2I87x6strRkRlXA5qVXUwNgQ3TW2O64epMtqk");
//        log.info("返回结果ueueueu：" + ueueueu);
//
//        String sss = download("http://upyun.lxyccc.top/halo/article_bg_26_civilization2_4k.jpg", "D:/44dsdsdsds4.jpg");

    }


}
