package com.bluewind.boot.common.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2020-06-18-13:45
 * @description 功能:本类功能为将各种对象转化为Json字符串
 * 使用 jackson 包
 */
public class JsonTool {
    final static Logger log = LoggerFactory.getLogger(JsonTool.class);

    /**
     * 将 List 转化为Json字符串
     *
     * @param 'List' dataList （可存map或databean或者混合存储都可以）
     * @return String JsonString
     */
    public static String listToJsonString(List dataList) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(dataList);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- listToJsonString -- Exception=" , e);
            }
        }
        return jsonString;
    }

    /**
     * 将 Map 转化为Json字符串
     *
     * @param 'Map' map
     * @return String JsonString
     */
    public static String mapToJsonString(Map map) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(map);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- mapToJsonString -- Exception=" , e);
            }
        }
        return jsonString;
    }


    /**
     * 将 JavaBean 类型的对象   转化为Json字符串
     *
     * @param 'Object' obj
     * @return String JsonString
     */
    public static String beanToJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- beanToJsonString -- Exception=", e);
            }
        }
        return jsonString;
    }


    /**
     * 将 Object 转化为Json字符串
     *
     * @param 'Object' obj
     * @return String JsonString
     */
    public static String objectToJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- objectToJsonString -- Exception=", e);
            }
        }
        return jsonString;
    }

    /**
     * 将 Json String 转化为 对象 Object
     * @param str
     * @param cla
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readToObject(String str, Class<T> cla) throws IOException {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(str, cla);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- readToObject -- Exception=", e);
            }
            return null;
        }
    }

    /**
     * 将 Json String 转化为Map
     *
     * @param 'String JsonString'
     * @return Map returnMap
     */
    public static Map getMapFromJsonString(String JsonString) {
        Map returnMap = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnMap = mapper.readValue(JsonString, Map.class);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- getMapFromJsonString -- Exception=", e);
            }
        }
        return returnMap;
    }

    /**
     * 将 Json String 转化为List(Json中List对象名称为tag)
     *
     * @param 'String JsonString  '
     * @return Map returnList
     */
    public static List getListFromJsonString(String JsonString, String tag) {
        List returnList = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map returnMap = mapper.readValue(JsonString, Map.class);
            if (returnMap.containsKey(tag)) {
                returnList = (List) returnMap.get(tag);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- getListFromJsonString -- Exception=", e);
            }
        }
        return returnList;
    }

    /**
     * 将 Json String 转化为List(Json为数组)
     *
     * @param 'String JsonString  '
     * @return Map returnList
     */
    public static List getListFromJsonString(String JsonString) {
        List returnList = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnList = mapper.readValue(JsonString, List.class);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- getListFromJsonString -- Exception=", e);
            }
        }
        return returnList;
    }


    /**
     * 将 Json String 转化为 Bean
     *
     * @param 'String JsonString  '
     * @return Map returnList
     */
    public static <T> T getBeanFromJsonString(String JsonString, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(JsonString, clazz);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- getBeanFromJsonString -- Exception=", e);
            }
            return null;
        }
    }



    /**
     * 将 Json String 转化为 List<Bean>
     *
     * @param 'String JsonString'
     * @return Map returnList
     */
    public static <T> List getListFromJsonString(String JsonString, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType =  mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        try {
            return mapper.readValue(JsonString, javaType);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("JsonTool -- getListFromJsonString -- Exception=", e);
            }
            return null;
        }
    }





    public static void main(String[] args) {
        String jsonStr = "{\"password\":\"123456\",\"username\":\"张三\"}";
        Map map = getMapFromJsonString(jsonStr);
        System.out.println("map=" + map);


//        String ooh = "[{\"id\":25,\"account\":\"hhyniubi\",\"password\":\"99B26BE5F5F7AF4A576DFB6DF0DD38FF\",\"name\":\"huahiyang\",\"phone\":\"0\",\"avatar\":\"http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif\",\"sex\":0,\"status\":1,\"createUser\":0,\"createTime\":\"2021-01-07 15:28:30\",\"updateUser\":0,\"updateTime\":\"2021-01-10 01:32:03\",\"delFlag\":0},{\"id\":25,\"account\":\"hhyniubi\",\"password\":\"99B26BE5F5F7AF4A576DFB6DF0DD38FF\",\"name\":\"huahiyang\",\"phone\":\"0\",\"avatar\":\"http://halo.lxyccc.top/f778738c-e4f8-4870-b634-56703b4acafe_1608734603765.gif\",\"sex\":0,\"status\":1,\"createUser\":0,\"createTime\":\"2021-01-07 15:28:30\",\"updateUser\":0,\"updateTime\":\"2021-01-10 01:32:03\",\"delFlag\":0}]";
//        List<SysUserInfo> sysy = getListFromJsonString(ooh, SysUserInfo.class);
//
//        System.out.println("SysUserInfo=" + sysy);
    }
}