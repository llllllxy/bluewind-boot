package com.bluewind.boot.module.system.loginlog.controller;

import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.module.system.loginlog.entity.LoginLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.module.system.loginlog.service.LoginLogService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-19-12:47
 **/
@Controller
@RequestMapping("/loginlog")
public class LoginLogController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(LoginLogController.class);

    // 基于构造函数注入
    private final LoginLogService loginLogService;
    public LoginLogController(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }


    /**
     * 页面初始化
     *
     * @return
     */
    @ApiOperation(value = "登陆日志页面初始化", notes = "登陆日志页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = DictUtils.getDictList("login_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/loginlog/list";
    }


    /**
     * 登陆日志页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    //@DataSourceWith(DataSourceType.SLAVE) // 注解切换数据源测试
    @ApiOperation(value = "登陆日志页面分页查询", notes = "登陆日志页面分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "account") String account,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "createTime") String createTime,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {

        //查询条件得加上
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysLoginLogController -- list -- 页面大小："+pageSize+"--页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("account", account);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        paraMap.put("createTime", createTime);

        List<LoginLog> accounts = loginLogService.list(paraMap);

        PageInfo<LoginLog> pageinfo = new PageInfo<>(accounts);
        //取出查询结果
        List<LoginLog> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, total);

        return BaseResult.success(result);
    }


    /**
     * 批量删除登陆日志
     * @return
     */
    @ApiOperation(value = "批量删除登陆日志", notes = "批量删除登陆日志")
    @RequestMapping(value="/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult batchDelete(@RequestBody String data){
        if (logger.isInfoEnabled()) {
            logger.info("SysLoginLogController -- batchDelete -- data：{}", data);
        }
        List<String> logIdList = JsonTool.parseArray(data, String.class);
        int num = loginLogService.batchDelete(logIdList);
        if (num > 0) {
            return BaseResult.success("批量删除登陆日志成功!");
        } else {
            return BaseResult.failure("批量删除登陆日志失败!");
        }
    }


    @ApiOperation(value = "清空登陆日志", notes = "清空登陆日志")
    @ResponseBody
    @RequestMapping(value = "/emptyLog", method = RequestMethod.POST)
    public BaseResult emptyLog() {
        loginLogService.emptyLog();
        return BaseResult.success("清空登陆日志成功!");
    }

}
