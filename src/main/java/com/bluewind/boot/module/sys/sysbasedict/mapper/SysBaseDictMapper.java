package com.bluewind.boot.module.sys.sysbasedict.mapper;

import com.bluewind.boot.module.sys.sysbasedict.entity.SysDict;
import com.bluewind.boot.module.sys.sysbasedict.entity.SysDictDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @author liuxingyu01
 * @date 2020-05-29-21:50
 **/
@Repository
public interface SysBaseDictMapper {

    /**
     * 获取全部枚举列表
     * @param map
     * @return
     */
    List<SysDict> getAllBaseDict(Map map);


    /**
     * 枚举修改，获取一条枚举记录
     * @param id
     * @return
     */
    SysDict findOneBaseDictById(String id);

    /**
     * 获取枚举公共方法
     * @param dictId
     * @return
     */
    List<Map<String,String>> getBaseDictByDictId(String dictId);

    /**
     * 新增一个枚举
     * @param sysDict
     * @return
     */
    int addOneDict(SysDict sysDict);

    /**
     * 删除一个枚举
     * @param id
     * @return
     */
    int deleteDict(String id);

    /**
     * 禁用一个枚举
     * @param id
     * @return
     */
    int forbidDict(String id);

    /**
     * 启用一个枚举
     * @param id
     * @return
     */
    int enableDict(String id);

    /**
     * 更新一个枚举
     * @param sysDict
     * @return
     */
    int updateOneDict(SysDict sysDict);

    /**
     * 获取一个枚举明细
     * @param sysDict
     * @return
     */
    List<SysDictDetail> getBaseDictDetail(SysDict sysDict);

    /**
     * 新增一个枚举明细
     * @param sysDictDetail
     * @return
     */
    int addDetail(SysDictDetail sysDictDetail);

    /**
     * 删除一个枚举明细
     * @param id
     * @return
     */
    int deleteDetail(String id);

    /**
     * 获取一个枚举明细的明细
     * @param id
     * @return
     */
    SysDictDetail findOneDictDetailById(String id);

    /**
     * 修改一个枚举明细
     * @param sysDictDetail
     * @return
     */
    int updateDetail(SysDictDetail sysDictDetail);

}
