package com.bluewind.boot.module.system.basedict.mapper;

import com.bluewind.boot.module.system.basedict.entity.DictInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @author liuxingyu01
 * @date 2020-05-29-21:50
 **/
@Repository
public interface BaseDictMapper {

    /**
     * 获取枚举公共方法
     * @param dictCode
     * @return
     */
    List<Map<String,String>> getDictByCode(String dictCode);


    /**
     * 获取全部枚举列表
     * @param map
     * @return
     */
    List<DictInfo> getAllBaseDict(Map map);


    /**
     * 检查是否已存在
     * @param idCode
     * @return
     */
    Integer checkExistByDictCode(String idCode);


    /**
     * 枚举修改，获取一条枚举记录
     * @param dictCode
     * @return
     */
    List<DictInfo> getDictByDictCode(String dictCode);


    /**
     * 新增一个枚举
     * @param dict
     * @return
     */
    int addOneDict(DictInfo dict);

    /**
     * 删除一个枚举
     * @param dictCode
     * @return
     */
    int deleteDict(String dictCode);

    /**
     * 禁用一个枚举
     * @param dictCode
     * @return
     */
    int forbidDict(String dictCode);

    /**
     * 启用一个枚举
     * @param dictCode
     * @return
     */
    int enableDict(String dictCode);

}
