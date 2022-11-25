package com.bluewind.boot.common.utils;

import com.bluewind.boot.common.utils.spring.SpringUtil;
import com.bluewind.boot.module.system.basedict.mapper.BaseDictMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-05-30-0:28
 * @description 获取枚举值 公共方法类 - 使用SpringUtil工具类获取bean
 **/

public class DictUtils {
    final static Logger logger = LoggerFactory.getLogger(DictUtils.class);

    private static BaseDictMapper baseDictMapper;

    private static BaseDictMapper getBaseDictService() {
        if (baseDictMapper == null) {
            Object bean = SpringUtil.getBean("baseDictMapper");
            baseDictMapper = (BaseDictMapper) bean;
        }
        return baseDictMapper;
    }

    /**
     * 根据字典id和key获取对应的值
     * @param dictCode 字典编码
     * @param key 字典键
     * @return
     */
    public static String getDictValue(String dictCode, String key) {
        if (StringUtils.isBlank(dictCode)) {
            return "字典id不能为空";
        }
        if (StringUtils.isBlank(key)) {
            return "字典key不能为空";
        }
        String dictValue = "";
        List<Map<String, String>> dictList = getBaseDictService().getDictByCode(dictCode);
        if (CollectionUtils.isNotEmpty(dictList)) {
            for (Map<String, String> tempMap : dictList) {
                if (!MapUtils.isEmpty(tempMap)) {
                    if (key.equals(tempMap.get("code"))) {
                        dictValue = tempMap.get("name");
                        break;
                    }
                }
            }
        }
        return dictValue;
    }


    /**
     * 获取指定的枚举列表
     *
     * @param dictCode 字典编码
     * @return
     */
    public static List<Map<String, String>> getDictList(String dictCode) {
        if (StringUtils.isBlank(dictCode)) {
            return null;
        }
        List<Map<String, String>> dictList = getBaseDictService().getDictByCode(dictCode);
        if (!CollectionUtils.isEmpty(dictList)) {
            return dictList;
        } else {
            return null;
        }
    }

    /**
     * 获取指定的枚举Map
     * @param dictCode 字典编码
     * @return
     */
    public static Map<String, String> getDictMap(String dictCode) {
        if (StringUtils.isBlank(dictCode)) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        List<Map<String, String>> dictList = getBaseDictService().getDictByCode(dictCode);
        if (!CollectionUtils.isEmpty(dictList)) {
            for (int i = 0; i < dictList.size(); i++) {
                Map<String, String> tempMap = dictList.get(i);
                String K = tempMap.get("code");
                String V = tempMap.get("name");
                result.put(K, V);
            }
        }
        return result;
    }

    /**
     * 获取指定的枚举JSON
     * @param dictCode 字典编码
     * @return
     */
    public static String getDictJson(String dictCode) {
        List<Map<String, String>> list = getDictList(dictCode);
        if (!CollectionUtils.isEmpty(list)) {
            return JsonTool.toJsonString(list);
        } else {
            return "获取字典列表为空";
        }
    }

}
