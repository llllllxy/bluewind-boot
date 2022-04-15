package com.bluewind.boot.module.system.basedict.service;

import com.bluewind.boot.module.system.basedict.entity.DictInfo;
import com.bluewind.boot.module.system.basedict.mapper.BaseDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<DictInfo> getAllBaseDict(Map map) {
        return baseDictMapper.getAllBaseDict(map);
    }


    /**
     * 检查是否已存在
     * @param idCode
     * @return
     */
    public Integer checkExistByDictCode(String idCode) {
        return baseDictMapper.checkExistByDictCode(idCode);
    }

    /**
     * 枚举修改，获取一条枚举记录
     *
     * @param dictCode
     * @return
     */
    public List<DictInfo> getDictByDictCode(String dictCode) {
        return baseDictMapper.getDictByDictCode(dictCode);
    }

    @Transactional
    public int addDict(List<DictInfo> insertDataList) {
        int nums = 0;
        for (DictInfo dictInfo: insertDataList) {
            int num = baseDictMapper.addOneDict(dictInfo);
            nums = nums + num;
        }
        return nums;
    }

    public int deleteDict(String dictCode) {
        return baseDictMapper.deleteDict(dictCode);
    }

    public int forbidDict(String dictCode) {
        return baseDictMapper.forbidDict(dictCode);
    }

    public int enableDict(String dictCode) {
        return baseDictMapper.enableDict(dictCode);
    }
}
