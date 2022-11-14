package com.bluewind.boot.module.system.ruleinfo.service;

import com.bluewind.boot.module.system.ruleinfo.entity.RuleInfo;
import com.bluewind.boot.module.system.ruleinfo.mapper.RuleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-07-16 11:03
 * @description
 **/
@Service
public class RuleInfoServiceImpl implements RuleInfoService {

    @Autowired
    private RuleInfoMapper ruleInfoMapper;

    @Override
    public List<RuleInfo> getSysRuleInfoList(Map<String, String> map) {
        return ruleInfoMapper.getSysRuleInfoList(map);
    }

    @Override
    public RuleInfo getOne(String ruleId) {
        return ruleInfoMapper.getOne(ruleId);
    }

    @Override
    public int delete(String ruleId) {
        return ruleInfoMapper.deleteOne(ruleId);
    }

    @Override
    public int add(RuleInfo ruleInfo) {
        return ruleInfoMapper.addRuleInfo(ruleInfo);
    }

    @Override
    public int update(RuleInfo ruleInfo) {
        return ruleInfoMapper.updateRuleInfo(ruleInfo);
    }

    @Override
    public int batchDelete(List<String> idList) {
        return ruleInfoMapper.batchDelete(idList);
    }
}
