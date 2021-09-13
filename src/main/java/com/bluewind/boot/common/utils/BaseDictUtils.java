package com.bluewind.boot.common.utils;

import com.bluewind.boot.sys.sysbasedict.mapper.SysBaseDictMapper;
import com.bluewind.boot.common.utils.spring.SpringUtil;
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

public class BaseDictUtils {
    final static Logger logger = LoggerFactory.getLogger(BaseDictUtils.class);

    private static SysBaseDictMapper sysBaseDictMapper;

    private static SysBaseDictMapper getBaseDictService() {
        if (sysBaseDictMapper == null) {
            Object bean = SpringUtil.getBean("sysBaseDictMapper");
            if (bean == null) {
                logger.error("baseDictMapper bean is null!");
            }
            sysBaseDictMapper = (SysBaseDictMapper) bean;
        }
        return sysBaseDictMapper;
    }

    /**
     * 根据字典id和key获取对应的值
     * @param dictId
     * @param key
     * @return
     */
    public static String getDictValue(String dictId, String key) {
        if (StringUtils.isBlank(dictId)) {
            return "字典id不能为空";
        }
        if (StringUtils.isBlank(key)) {
            return "字典key不能为空";
        }
        String dictValue = "";
        List<Map<String, String>> dictList = getBaseDictService().getBaseDictByDictId(dictId);
        if (CollectionUtils.isNotEmpty(dictList)) {
            for (Map tempMap : dictList) {
                if (!MapUtils.isEmpty(tempMap)) {
                    if (key.equals((String) tempMap.get("code"))) {
                        dictValue = (String) tempMap.get("name");
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
     * @param dictId
     * @return
     */
    public static List<Map<String, String>> getDictList(String dictId) {
        if (StringUtils.isBlank(dictId)) {
            return null;
        }
        List<Map<String, String>> dictList = getBaseDictService().getBaseDictByDictId(dictId);
        if (!CollectionUtils.isEmpty(dictList)) {
            return dictList;
        } else {
            return null;
        }
    }

    /**
     * 获取指定的枚举Map
     * @param dictId
     * @return
     */
    public static Map<String, String> getDictMap(String dictId) {
        if (StringUtils.isBlank(dictId)) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        List<Map<String, String>> dictList = getBaseDictService().getBaseDictByDictId(dictId);
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
     * @param dictId
     * @return
     */
    public static String getDictJson(String dictId) {
        List<Map<String, String>> list = getDictList(dictId);
        if (!CollectionUtils.isEmpty(list)) {
            return JsonTool.listToJsonString(list);
        } else {
            return "获取字典列表为空";
        }
    }

}
