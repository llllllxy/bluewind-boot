package com.bluewind.boot.common.utils.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-05-07-10:59
 * @description bean工具类，使用Spring BeanUtils封装实现
 * @additional 避免用 Apache Beanutils 进行属性的 copy。因为Apache BeanUtils 性能较差，
 *             可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意均是浅拷贝。
 **/
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);


    /**
     * 批量copy两个bean属性
     *
     * @param source 源
     * @param tClass 目标类型
     * @return List<T> 目标类型列表中
     */
    public static <S, T> List<T> batchTransformBean(List<S> source, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        try {
            for (S co : source) {
                T t = tClass.newInstance();

                org.springframework.beans.BeanUtils.copyProperties(co, t);
                result.add(t);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("转换失败");
        }
        return result;
    }

    /**
     * 拷贝两个bean属性
     *
     * @param source 源
     * @param tClass 目标类型
     * @return 目标类型实例
     */
    public static <S, T> T transformBean(S source, Class<T> tClass) {
        T t;
        try {
            t = tClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, t);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("转换失败");
        }
        return t;
    }

    /**
     * bean转map
     *
     * @param source bean
     * @param <T>    传入的bean类型
     * @return Map<String, Object>
     */
    public static <T> Map<String, Object> beanToMap(T source) {
        Map<String, Object> map = new HashMap<>(16);
        if (source != null) {
            BeanMap beanMap = BeanMap.create(source);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * map转为javaBean
     *
     * @param map   要转换的map
     * @param clazz 转换成的bean类
     * @return 转换后的bean
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            BeanMap beanMap = BeanMap.create(bean);
            for (String key : map.keySet()) {
                if (key.contains("_")) {
                    String newKey = StringUtils.toLowerCaseFirstOne(StringUtils.lineToHump(key));
                    beanMap.put(newKey, map.get(key));
                } else {
                    beanMap.put(key, map.get(key));
                }
            }
            return bean;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 批量转换map为javaBean
     *
     * @param list  map
     * @param clazz 类型
     * @return 转换后的list
     */
    public static <T, S> List<T> batchMapToBean(List<Map<String, S>> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Map<String, ?> map : list) {
            result.add(mapToBean(map, clazz));
        }
        return result;
    }

}
