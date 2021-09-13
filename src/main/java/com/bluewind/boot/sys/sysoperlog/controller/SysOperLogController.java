package com.bluewind.boot.sys.sysoperlog.controller;

import com.bluewind.boot.sys.sysoperlog.entity.SysOperLog;
import com.bluewind.boot.sys.sysoperlog.service.SysOperLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.annotation.RequestLimit;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-05-15:19
 * @description
 **/
@Controller
@RequestMapping("/sysoperlog")
public class SysOperLogController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysOperLogController.class);

    private SysOperLogService sysOperLogService;

    @Autowired
    public SysOperLogController(SysOperLogService sysOperLogService) {
        this.sysOperLogService = sysOperLogService;
    }

    /**
     * 页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/sysoperlog/sysoperlog_list";
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

        List<SysOperLog> logs = sysOperLogService.list(paraMap);

        PageInfo<SysOperLog> pageinfo = new PageInfo<>(logs);
        // 取出查询结果
        List<SysOperLog> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, total);

        return BaseResult.success(result);
    }

}
