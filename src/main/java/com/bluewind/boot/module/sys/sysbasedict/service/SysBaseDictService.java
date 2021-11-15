package com.bluewind.boot.module.sys.sysbasedict.service;

import com.bluewind.boot.module.sys.sysbasedict.mapper.SysBaseDictMapper;
import com.bluewind.boot.module.sys.sysbasedict.entity.SysDict;
import com.bluewind.boot.module.sys.sysbasedict.entity.SysDictDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-05-29-21:50
 **/
@Service
public class SysBaseDictService {
    @Autowired
    private SysBaseDictMapper sysBaseDictMapper;


    /**
     * 获取全部枚举列表
     *
     * @param map
     * @return
     */
    public List<SysDict> getAllBaseDict(Map map) {
        return sysBaseDictMapper.getAllBaseDict(map);
    }

    /**
     * 枚举修改，获取一条枚举记录
     *
     * @param dictId
     * @return
     */
    public SysDict findOneBaseDictById(String dictId) {
        return sysBaseDictMapper.findOneBaseDictById(dictId);
    }


    public int addOneDict(SysDict sysDict) {
        return sysBaseDictMapper.addOneDict(sysDict);
    }


    public int deleteDict(String dictId) {
        return sysBaseDictMapper.deleteDict(dictId);
    }

    public int forbidDict(String dictId) {
        return sysBaseDictMapper.forbidDict(dictId);
    }

    public int enableDict(String dictId) {
        return sysBaseDictMapper.enableDict(dictId);
    }

    public int updateOneDict(SysDict sysDict) {
        return sysBaseDictMapper.updateOneDict(sysDict);
    }


    public List<SysDictDetail> getBaseDictDetail(SysDict sysDict) {
        return sysBaseDictMapper.getBaseDictDetail(sysDict);
    }

    public int addDetail(SysDictDetail sysDictDetail) {
        return sysBaseDictMapper.addDetail(sysDictDetail);
    }

    public int deleteDetail(String id) {
        return sysBaseDictMapper.deleteDetail(id);
    }

    public SysDictDetail findOneDictDetailById(String id) {
        return sysBaseDictMapper.findOneDictDetailById(id);
    }

    public int updateDetail(SysDictDetail sysDictDetail) {
        return sysBaseDictMapper.updateDetail(sysDictDetail);
    }


}
