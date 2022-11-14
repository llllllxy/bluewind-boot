package com.bluewind.boot.module.system.ruleinfo.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.ruleinfo.entity.RuleInfo;
import com.bluewind.boot.module.system.ruleinfo.service.RuleInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @Autowired
    private RuleInfoService ruleInfoService;

    @ApiOperation(value = "业务规则管理页面初始化", notes = "业务规则管理页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> ruleTypeDictList = DictUtils.getDictList("sys_rule_info_rule_type");
        List<Map<String,String>> statusDictList = DictUtils.getDictList("sys_rule_info_status");
        model.addAttribute("ruleTypeDictList", ruleTypeDictList);
        model.addAttribute("statusDictList", statusDictList);
        return "system/ruleinfo/list";
    }


    @LogAround("业务规则数据分页查询")
    @ApiOperation(value = "业务规则数据分页查询", notes = "业务规则数据分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "ruleName") String ruleName,
                           @RequestParam(required = false, defaultValue = "", value = "ruleKey") String ruleKey,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "ruleType") String ruleType,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("ruleName", ruleName);
        paraMap.put("ruleKey", ruleKey);
        paraMap.put("ruleType", ruleType);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<RuleInfo> ruleInfoList = ruleInfoService.getSysRuleInfoList(paraMap);

        PageInfo<RuleInfo> pageinfo = new PageInfo<>(ruleInfoList);
        // 取出查询结果
        List<RuleInfo> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTAL, total);

        return BaseResult.success(result);
    }


    @ApiOperation(value = "业务规则管理新增页面", notes = "业务规则管理新增页面")
    @RequestMapping(value = "/forAdd", method = RequestMethod.GET)
    public String forAdd(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> ruleTypeDictList = DictUtils.getDictList("sys_rule_info_rule_type");
        model.addAttribute("ruleTypeDictList", ruleTypeDictList);

        return "system/ruleinfo/add";
    }


    @ApiOperation(value = "业务规则管理修改页面", notes = "业务规则管理修改页面")
    @RequestMapping(value = "/forUpdate/{ruleId}", method = RequestMethod.GET)
    public String forUpdate(Model model,@PathVariable String ruleId) {
        // 获取下拉栏枚举值
        List<Map<String,String>> ruleTypeDictList = DictUtils.getDictList("sys_rule_info_rule_type");
        List<Map<String,String>> statusDictList = DictUtils.getDictList("sys_rule_info_status");
        RuleInfo ruleInfo = ruleInfoService.getOne(ruleId);

        model.addAttribute("ruleTypeDictList", ruleTypeDictList);
        model.addAttribute("statusDictList", statusDictList);
        model.addAttribute("ruleInfo", ruleInfo);
        return "system/ruleinfo/update";
    }


    @ApiOperation(value = "业务规则新增", notes = "业务规则新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam("ruleName") String ruleName,
                          @RequestParam("ruleKey") String ruleKey,
                          @RequestParam("ruleValue") String ruleValue,
                          @RequestParam("ruleType") String ruleType,
                          @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setRuleId(IdGenerate.nextId());
        ruleInfo.setRuleType(ruleType);
        ruleInfo.setRuleKey(ruleKey);
        ruleInfo.setRuleName(ruleName);
        ruleInfo.setRuleValue(ruleValue);
        ruleInfo.setDescript(descript);
        ruleInfo.setCreateUser(getSysUserId());

        int num = ruleInfoService.add(ruleInfo);
        if (num > 0) {
            return BaseResult.success("新增规则信息'" + ruleName + "'成功！");
        } else {
            return BaseResult.failure("新增规则信息失败，请联系后台管理员！");
        }
    }


    @ApiOperation(value = "业务规则修改", notes = "业务规则修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("ruleId") String ruleId,
                             @RequestParam("ruleName") String ruleName,
                             @RequestParam("ruleKey") String ruleKey,
                             @RequestParam("ruleValue") String ruleValue,
                             @RequestParam("ruleType") String ruleType,
                             @RequestParam("status") String status,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setRuleId(ruleId);
        ruleInfo.setRuleType(ruleType);
        ruleInfo.setRuleKey(ruleKey);
        ruleInfo.setStatus(status);
        ruleInfo.setRuleName(ruleName);
        ruleInfo.setRuleValue(ruleValue);
        ruleInfo.setDescript(descript);
        ruleInfo.setUpdateUser(getSysUserId());

        int num = ruleInfoService.update(ruleInfo);
        if (num > 0) {
            return BaseResult.success("更新规则信息'" + ruleName + "'成功！");
        } else {
            return BaseResult.failure("更新规则信息失败，请联系后台管理员！");
        }
    }

    @ApiOperation(value = "业务规则信息删除", notes = "业务规则信息删除")
    @RequestMapping(value = "/delete/{ruleId}/{ruleName}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String ruleId, @PathVariable String ruleName) {
        int num = ruleInfoService.delete(ruleId);
        if (num > 0) {
            return BaseResult.success("删除规则信息'" + ruleName + "'成功！");
        } else {
            return BaseResult.failure("杀出岗位信息，请联系后台管理员！");
        }
    }


    @ApiOperation(value = "业务规则信息批量删除", notes = "业务规则信息批量删除")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult batchDelete(@RequestBody String data) {
        if (logger.isInfoEnabled()) {
            logger.info("batchDelete -- data：" + data);
        }
        List<String> idList = JsonTool.parseArray(data, String.class);
        if (logger.isInfoEnabled()) {
            logger.info("batchDelete -- idList：{}", idList);
        }

        int num = ruleInfoService.batchDelete(idList);
        if (num > 0) {
            return BaseResult.success("批量删除成功!");
        } else {
            return BaseResult.failure("批量删除失败!");
        }
    }


}
