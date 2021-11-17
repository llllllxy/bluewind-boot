package com.bluewind.boot.common.utils;

import com.bluewind.boot.module.sys.sysbasedict.mapper.SysBaseDictMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-11-10-14:07
 * @description 动态获取bean第二种实现方式
 * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
 * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
 **/
@Component
public class DictUtils2 {

    @Autowired
    private SysBaseDictMapper sysBaseDictMapper;

    private static DictUtils2 dictUtils2;

    @PostConstruct
    public void init() {
        dictUtils2 = this;
        dictUtils2.sysBaseDictMapper = this.sysBaseDictMapper;
    }

    /**
     * 根据字典id和key获取对应的值
     * @param dictCode
     * @param key
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
        List<Map<String, String>> dictList = dictUtils2.sysBaseDictMapper.getDictByCode(dictCode);
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
     * @param dictCode
     * @return
     */
    public static List<Map<String, String>> getDictList(String dictCode) {
        if (StringUtils.isBlank(dictCode)) {
            return null;
        }
        List<Map<String, String>> dictList = dictUtils2.sysBaseDictMapper.getDictByCode(dictCode);
        if (!CollectionUtils.isEmpty(dictList)) {
            return dictList;
        } else {
            return null;
        }
    }

    /**
     * 获取指定的枚举Map
     * @param dictCode
     * @return
     */
    public static Map<String, String> getDictMap(String dictCode) {
        if (StringUtils.isBlank(dictCode)) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        List<Map<String, String>> dictList = dictUtils2.sysBaseDictMapper.getDictByCode(dictCode);
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
     * @param dictCode
     * @return
     */
    public static String getDictJson(String dictCode) {
        List<Map<String, String>> list = getDictList(dictCode);
        if (!CollectionUtils.isEmpty(list)) {
            return JsonTool.listToJsonString(list);
        } else {
            return "获取字典列表为空";
        }
    }

}
