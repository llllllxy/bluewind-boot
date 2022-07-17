package com.bluewind.boot.module.system.ruleinfo.service;

import com.bluewind.boot.module.system.ruleinfo.entity.RuleInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-06-08 19:01
 * @description
 **/

public interface RuleInfoService {

    List<RuleInfo>  getSysRuleInfoList(Map<String, String> map);
}
