package com.bluewind.boot.common.utils.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuxingyu01
 * @date 2020-10-15-19:30
 * @description: 原生http请求工具类
 **/
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    // 设置连接主机超时（单位：毫秒）
    private static final int CONNECT_TIMEOUT = 5000;

    // 设置从主机读取数据超时（单位：毫秒）
    private static final int READ_TIMEOUT = 5000;

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        String response = "";
        try {
            // 建立连接
            URL serverUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();
            // 设置参数
            con.setDoOutput(true); // 需要输出
            con.setUseCaches(false); // 不允许缓存
            con.setRequestMethod("GET"); // 设置GET方式连接
            con.setConnectTimeout(CONNECT_TIMEOUT);
            con.setReadTimeout(READ_TIMEOUT);
            // 设置请求属性
            con.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-type", "application/json");
            // 必须设置false，否则会自动redirect到重定向后的地址
            // con.setInstanceFollowRedirects(false);
            con.connect();
            // 获得返回内容
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = getReturn(con);
            }
            con.disconnect();
        } catch (Exception e) {
            log.error("HttpsUtils -- get -- Exception = {e} ", e);
        }
        return response;
    }

    /**
     * key-value形式的post请求
     *
     * @param path
     * @param param
     * @return
     */
    private static String post(String path, Map<String, String> param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 设置连接超时时间和读取超时时间
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            out = new OutputStreamWriter(conn.getOutputStream());
            // POST的请求参数写在正文中
            for (String key : param.keySet()) {
                out.write(key + "=" + param.get(key) + "&");
            }
            out.flush();
            out.close();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            log.error("HttpsUtils -- post -- Exception = {e} ", e);
        }
        // 关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("HttpsUtils -- post -- Exception = {ex} ", ex);
            }
        }
        return result.toString();
    }

    /**
     * post请求 body形式
     *
     * @param url
     * @param data
     * @return
     */
    public static String postJson(String url, String data) {
        String response = "";
        DataOutputStream dos = null;
        try {
            // 建立连接
            URL serverUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();

            // 设置参数
            con.setDoOutput(true); // 需要输出
            con.setDoInput(true); // 需要输入
            con.setUseCaches(false); // 不允许缓存
            con.setRequestMethod("POST"); // 设置POST方式连接
            con.setConnectTimeout(CONNECT_TIMEOUT);
            con.setReadTimeout(READ_TIMEOUT);
            // 设置请求属性
            con.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-type", "application/json");
            // 必须设置false，否则会自动redirect到重定向后的地址
            // con.setInstanceFollowRedirects(false);
            con.connect();
            // 建立输入流，向指向的URL传入参数
            dos = new DataOutputStream(con.getOutputStream());
            // dos.writeBytes(data);
            dos.write(data.getBytes("UTF-8"));
            dos.flush();
            dos.close();
            // 获得返回内容
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = getReturn(con);
            }
            con.disconnect();
        } catch (Exception e) {
            log.error("HttpsUtils -- postJson -- Exception = {e} ", e);
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 获取数据流，写入文件
     *
     * @param url
     * @param filePath
     */
    public static void getByte(String url, String filePath) {
        try {
            // 建立连接
            URL serverUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();

            // 设置参数
            con.setDoOutput(true); // 需要输出
            con.setUseCaches(false); // 不允许缓存
            con.setRequestMethod("GET"); // 设置GET方式连接
            con.setConnectTimeout(CONNECT_TIMEOUT);
            con.setReadTimeout(READ_TIMEOUT);

            // 设置请求属性
            con.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-type", "application/json");
            // 必须设置false，否则会自动redirect到重定向后的地址
//            con.setInstanceFollowRedirects(false);
            con.connect();

            // 创建输出文件
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            // 获得返回内容
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuffer buffer = new StringBuffer();
                // 将返回的输入流转换成字符串
                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                FileOutputStream fos = new FileOutputStream(filePath);
                int BUFFER_SIZE = 1024;
                byte[] buf = new byte[BUFFER_SIZE];
                int size = 0;

                try {
                    while ((size = bis.read(buf)) != -1) {
                        fos.write(buf, 0, size);
                    }
                    fos.flush();
                } catch (IOException e) {
                } catch (ClassCastException e) {
                } finally {
                    try {
                        fos.close();
                        bis.close();
                    } catch (IOException e) {
                    } catch (NullPointerException e) {
                    }
                }
            }
            con.disconnect();
        } catch (Exception e) {
            log.error("HttpsUtils -- getByte -- Exception = {e}  ", e);
        }
    }

    /**
     * 请求url获取返回的内容 HttpURLConnection
     *
     * @param connection
     * @return
     * @throws IOException
     */
    public static String getReturn(HttpURLConnection connection) {
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        // 将返回的输入流转换成字符串
        try {
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (IOException e) {
            log.error("HttpsUtils -- getReturn -- IOException = {e}  ", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                log.error("HttpsUtils -- getReturn -- IOException2 = {ex} ", ex);
            }
        }
        return buffer.toString();
    }


    public static void main(String[] args) throws Exception {
        // getByte("https://www.baidu.com/img/baidu_jgylogo3.gif", "E:\\baidu.png");
//		String get = get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww18ca1c72a196320c&corpsecret=3yUH3k2I87x6strRkRlXA5qVXUwNgQ3TW2O64epMtqk");
//		System.out.println(get);

        Map param = new HashMap();
        param.put("inJson",
                "{\"root\":{\"head\":{\"msgid\":\"2020071500001\",\"msgcode\":\"lc0000001\",\"msgname\":\"接收设备基本信息\",\"sourcesys\":\"yy01\",\"targetsys\":\"scycyt\",\"createtime\":\"20200915121314\"},\"data\":{\"row\":[{\"action\":\"update\",\"seriesnum\":\"SH0620200708001\",\"fixedassetsid\":\"200708001\",\"receivetime\":\"20200915121314\"}]}}}");
        String result = post("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckinoption", param);
        System.out.println(result);

        String result2 = get("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckinoption");

        System.out.println(result2);
    }

}
