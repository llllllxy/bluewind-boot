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
     * @param id
     * @return
     */
    public SysDict findOneBaseDictById(int id) {
        return sysBaseDictMapper.findOneBaseDictById(id);
    }


    public int addOneDict(SysDict sysDict) {
        return sysBaseDictMapper.addOneDict(sysDict);
    }


    public int deleteDict(Integer id) {
        return sysBaseDictMapper.deleteDict(id);
    }

    public int forbidDict(Integer id) {
        return sysBaseDictMapper.forbidDict(id);
    }

    public int enableDict(Integer id) {
        return sysBaseDictMapper.enableDict(id);
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

    public int deleteDetail(Integer id) {
        return sysBaseDictMapper.deleteDetail(id);
    }

    public SysDictDetail findOneDictDetailById(Integer id) {
        return sysBaseDictMapper.findOneDictDetailById(id);
    }

    public int updateDetail(SysDictDetail sysDictDetail) {
        return sysBaseDictMapper.updateDetail(sysDictDetail);
    }


}
