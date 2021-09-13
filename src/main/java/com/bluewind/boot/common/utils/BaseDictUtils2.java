package com.bluewind.boot.common.utils;

import com.bluewind.boot.sys.sysbasedict.mapper.SysBaseDictMapper;
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
public class BaseDictUtils2 {

    @Autowired
    private SysBaseDictMapper sysBaseDictMapper;

    private static BaseDictUtils2 baseDictUtils2;


    @PostConstruct
    public void init() {
        baseDictUtils2 = this;
        baseDictUtils2.sysBaseDictMapper = this.sysBaseDictMapper;
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
        List<Map<String, String>> dictList = baseDictUtils2.sysBaseDictMapper.getBaseDictByDictId(dictId);
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
        List<Map<String, String>> dictList = baseDictUtils2.sysBaseDictMapper.getBaseDictByDictId(dictId);
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
        List<Map<String, String>> dictList = baseDictUtils2.sysBaseDictMapper.getBaseDictByDictId(dictId);
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
