package com.bluewind.boot.module.system.ruleinfo.mapper;

import com.bluewind.boot.module.system.ruleinfo.entity.RuleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-06-08 19:01
 * @description
 **/
@Repository
public interface RuleInfoMapper {

    List<RuleInfo> getSysRuleInfoList(Map<String, String> map);

    RuleInfo getOne(String ruleId);

    int deleteOne(String ruleId);

    int addRuleInfo(RuleInfo ruleInfo);

    int updateRuleInfo(RuleInfo ruleInfo);
}
