package com.bluewind.boot.common.utils.io;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.*;


/**
 * @author liuxingyu01
 * @date 2020-09-12-21:16
 * @description .properties配置文件读取工具
 * @demo  配置文件名： qiniu.properties
 *        配置文件内容：qiniu_img_url_pre=${app.qiniu.qiniu_img_url_pre:http://halo.xxxxx.top/}
 *                   access_key=${app.qiniu.access_key:xxxxxxxxxxxxxxxxxxxxxx} // 如果环境变量没有的话，则取冒号后面的默认值
 *                   secret_key=${app.qiniu.access_key:xxxxxxxxxxxxxxxxxxxxxx}
 *                   bucketname=${app.qiniu.bucketname:xxxxx-halo}
 *        使用示例：private String imgUrlPre = PropertiesFileUtil.getInstance("qiniu").get("qiniu_img_url_pre");
 **/
public class PropertiesFileUtil {

    private static final Logger log = LoggerFactory.getLogger(PropertiesFileUtil.class);
    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<String, PropertiesFileUtil>();
    /**
     * 默认资源文件名称
     */
    private static final String NAME = "application";
    /**
     * 打开文件时间，判断超时使用
     */
    private Date loadTime = null;

    /**
     * 默认资源文件名称
     */
    private EncodedResource resource = null;

    private Properties properties = null;
    /**
     * 缓存时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 私有构造方法，创建单例
     *
     * @param name
     */
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        try {
            this.resource = new EncodedResource(ResourceUtil.getResource("/" + name + ".properties"),"UTF-8");

            this.properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {

            log.error("读取配置文件失败", e);
        }
    }


    @Deprecated
    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    public static boolean exists(String name){
        Resource resource = ResourceUtil.getResource("/" + name + ".properties");
        return resource.exists();
    }


    /**
     * 根据key读取value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        if(properties == null){
            return null;
        }
        String value = properties.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        value = SystemVariableUtil.convertSystemVariable(value);
        return value;
    }

    public Map getMap() {
        if(properties == null){
            return null;
        }
        Map<String, Object> map = new HashMap();
        Enumeration<?> enumKeys = properties.propertyNames();
        while (enumKeys.hasMoreElements()) {
            String key = (String) enumKeys.nextElement();
            map.put(key, properties.getProperty(key));
        }
        return map;
    }

    public static void main(String[] args) {

    }

    public Date getLoadTime() {
        return loadTime;
    }

}
