package com.bluewind.boot.module.system.operlog.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.annotation.RequestLimit;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.module.system.operlog.entity.OperLog;
import com.bluewind.boot.module.system.operlog.service.OperLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-15:19
 * @description
 **/
@Controller
@RequestMapping("/operlog")
public class OperLogController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(OperLogController.class);

    // 基于构造函数注入
    private final OperLogService operLogService;
    public OperLogController(OperLogService operLogService) {
        this.operLogService = operLogService;
    }

    /**
     * 页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/operlog/list";
    }


    /**
     * 操作日志页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestLimit(time = 60, count = 20, waits = 300)
    @LogAround("操作日志页面分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "model") String model,
                           @RequestParam(required = false, defaultValue = "", value = "type") String type,
                           @RequestParam(required = false, defaultValue = "", value = "createTime") String createTime,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysOperLogController -- list -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("model", model);
        paraMap.put("type", type);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        paraMap.put("createTime", createTime);

        List<OperLog> logs = operLogService.list(paraMap);

        PageInfo<OperLog> pageinfo = new PageInfo<>(logs);
        // 取出查询结果
        List<OperLog> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTAL, total);

        return BaseResult.success(result);
    }



    /**
     * 批量删除操作日志
     * @return
     */
    @ApiOperation(value = "批量删除登操作日志", notes = "批量删除操作日志")
    @RequestMapping(value="/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult batchDelete(@RequestBody String data){
        if (logger.isInfoEnabled()) {
            logger.info("SysLoginLogController -- batchDelete -- data：{}", data);
        }
        List<String> logIdList = JsonTool.parseArray(data, String.class);
        int num = operLogService.batchDelete(logIdList);
        if (num > 0) {
            return BaseResult.success("批量删除操作日志成功!");
        } else {
            return BaseResult.failure("批量删除操作日志失败!");
        }
    }


    @ApiOperation(value = "清空操作日志", notes = "清空操作日志")
    @ResponseBody
    @RequestMapping(value = "/emptyLog", method = RequestMethod.POST)
    public BaseResult emptyLog() {
        operLogService.emptyLog();
        return BaseResult.success("清空操作日志成功!");
    }

}
