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

    public List<RuleInfo> getSysRuleInfoList(Map<String, String> map) {
        return ruleInfoMapper.getSysRuleInfoList(map);
    }

}
