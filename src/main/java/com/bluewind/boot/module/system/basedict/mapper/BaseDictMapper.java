package com.bluewind.boot.module.system.basedict.mapper;

import com.bluewind.boot.module.system.basedict.entity.Dict;
import com.bluewind.boot.module.system.basedict.entity.DictDetail;
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
     * 获取全部枚举列表
     * @param map
     * @return
     */
    List<Dict> getAllBaseDict(Map map);


    /**
     * 枚举修改，获取一条枚举记录
     * @param dictId
     * @return
     */
    Dict findOneBaseDictById(String dictId);

    /**
     * 获取枚举公共方法
     * @param dictCode
     * @return
     */
    List<Map<String,String>> getDictByCode(String dictCode);

    /**
     * 新增一个枚举
     * @param dict
     * @return
     */
    int addOneDict(Dict dict);

    /**
     * 删除一个枚举
     * @param id
     * @return
     */
    int deleteDict(String dictId);

    /**
     * 禁用一个枚举
     * @param id
     * @return
     */
    int forbidDict(String dictId);

    /**
     * 启用一个枚举
     * @param id
     * @return
     */
    int enableDict(String dictId);

    /**
     * 更新一个枚举
     * @param dict
     * @return
     */
    int updateOneDict(Dict dict);

    /**
     * 获取一个枚举明细
     * @param dict
     * @return
     */
    List<DictDetail> getBaseDictDetail(Dict dict);

    /**
     * 新增一个枚举明细
     * @param dictDetail
     * @return
     */
    int addDetail(DictDetail dictDetail);

    /**
     * 删除一个枚举明细
     * @param dictDetailId
     * @return
     */
    int deleteDetail(String dictDetailId);

    /**
     * 获取一个枚举明细的明细
     * @param dictDetailId
     * @return
     */
    DictDetail findOneDictDetailById(String dictDetailId);

    /**
     * 修改一个枚举明细
     * @param dictDetail
     * @return
     */
    int updateDetail(DictDetail dictDetail);

}
