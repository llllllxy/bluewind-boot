package com.bluewind.boot.module.system.ruleinfo.controller;

import com.bluewind.boot.common.base.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuxingyu01
 * @date 2022-06-08 19:01
 * @description 业务规则管理
 **/
@Api(tags = "业务规则管理")
@Controller
@RequestMapping("/ruleinfo")
public class RuleInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(RuleInfoController.class);



}
