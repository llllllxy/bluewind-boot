package com.bluewind.boot.common.utils.http;

import com.bluewind.boot.common.utils.JsonTool;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
     *
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
            //log.info("Post - params：{}", params);
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
            //log.info("Post请求返回：{}", result);
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

            String dispositionHeader = response.header("Content-Disposition");
            try {
                dispositionHeader = java.net.URLDecoder.decode(dispositionHeader, "UTF-8");
                dispositionHeader = dispositionHeader.split("\"")[1];
                dispositionHeader = dispositionHeader.replace("*", "&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // dispositionHeader = "123456.pdf";
            savePath = savePath + dispositionHeader;

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

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.body() != null && response.isSuccessful()) {
                        String result = response.body().string();
                        log.info("OkHttp[asyncGet]请求结果：{}", result);
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {

                }
            });

        } catch (Exception e) {
            log.error("OkHttp[asyncGet]请求异常", e);
        }
    }


    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        // 测试爬取下载巨潮资讯网上的财报
        String url1 = "http://www.cninfo.com.cn/new/hisAnnouncement/query";
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", "1");
        params.put("pageSize", "30");
        params.put("column", "szse");
        params.put("tabName", "fulltext");
        params.put("category", "category_ndbg_szsh");
        params.put("searchkey", "2019年");

        params.put("trade", "制造业");
        params.put("seDate", "2020-01-01~2021-11-24");
        params.put("isHLtitle", "true");
        String resutl1 = post(url1, params);

        Map resutl1Map = JsonTool.parseMap(resutl1);
        int totalAnnouncement = Integer.parseInt(resutl1Map.get("totalAnnouncement").toString());

        int pageCounts = totalAnnouncement / 30 + 1;

        try {
            for (int i = 1; i <= pageCounts; i++) {
                params.put("pageNum", i + "");
                String resutl2 = post(url1, params);
                Map resutl1Ma2 = JsonTool.parseMap(resutl2);
                List<Map> announcements = (List<Map>) resutl1Ma2.get("announcements");
                for (Map announcement : announcements) {
                    String adjunctUrl = announcement.get("adjunctUrl").toString();
                    String announcementId = announcement.get("announcementId").toString();
                    String announcementTitle = announcement.get("announcementTitle").toString();

                    if (announcementTitle != null && announcementTitle.contains("2019") && !announcementTitle.contains("摘要")) {
                        String[] adjunctUrls = adjunctUrl.split("/");
                        String dateStr = adjunctUrls[1];
                        String downloadUrl = "http://www.cninfo.com.cn/new/announcement/download?bulletinId=" + announcementId + "&announceTime=" + dateStr;
                        System.out.println(downloadUrl);
                        fixedThreadPool.execute(() -> {
                            // 开启下载
                            download(downloadUrl, "D:/caibao/");
                        });
                    }

                }
            }
        } catch (Exception e) {
            log.error("OkHttp[main]运行异常Exception", e);
        }
        log.info("OkHttp[asyncGet]分页页数：{}", pageCounts);

        // String sss = download("http://www.cninfo.com.cn/new/announcement/download?bulletinId=1210778491&announceTime=2021-08-19", "D:/caibao/");
    }

}
