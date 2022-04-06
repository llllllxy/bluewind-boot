package com.bluewind.boot.module.system.basedict.service;

import com.bluewind.boot.module.system.basedict.entity.Dict;
import com.bluewind.boot.module.system.basedict.entity.DictDetail;
import com.bluewind.boot.module.system.basedict.mapper.BaseDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-05-29-21:50
 **/
@Service
public class BaseDictService {
    @Autowired
    private BaseDictMapper baseDictMapper;


    /**
     * 获取全部枚举列表
     *
     * @param map
     * @return
     */
    public List<Dict> getAllBaseDict(Map map) {
        return baseDictMapper.getAllBaseDict(map);
    }

    /**
     * 枚举修改，获取一条枚举记录
     *
     * @param dictId
     * @return
     */
    public Dict findOneBaseDictById(String dictId) {
        return baseDictMapper.findOneBaseDictById(dictId);
    }


    public int addOneDict(Dict dict) {
        return baseDictMapper.addOneDict(dict);
    }


    public int deleteDict(String dictId) {
        return baseDictMapper.deleteDict(dictId);
    }

    public int forbidDict(String dictId) {
        return baseDictMapper.forbidDict(dictId);
    }

    public int enableDict(String dictId) {
        return baseDictMapper.enableDict(dictId);
    }

    public int updateOneDict(Dict dict) {
        return baseDictMapper.updateOneDict(dict);
    }


    public List<DictDetail> getBaseDictDetail(Dict dict) {
        return baseDictMapper.getBaseDictDetail(dict);
    }

    public int addDetail(DictDetail dictDetail) {
        return baseDictMapper.addDetail(dictDetail);
    }

    public int deleteDetail(String dictDetailId) {
        return baseDictMapper.deleteDetail(dictDetailId);
    }

    public DictDetail findOneDictDetailById(String dictDetailId) {
        return baseDictMapper.findOneDictDetailById(dictDetailId);
    }

    public int updateDetail(DictDetail dictDetail) {
        return baseDictMapper.updateDetail(dictDetail);
    }

}
