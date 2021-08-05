package com.liuxingyu.meco.sys.sysquartzlog.controller;

import com.liuxingyu.meco.common.base.BaseController;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.sys.sysquartzlog.entity.SysQuartzLog;
import com.liuxingyu.meco.sys.sysquartzlog.service.SysQuartzLogService;
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
 * @date 2021-03-11-17:21
 **/
@Controller
@RequestMapping("/sysquartzlog")
public class SysQuartzLogController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysQuartzLogController.class);

    @Autowired
    private SysQuartzLogService sysQuartzLogService;


    /**
     * 请求定时任务管理列表页
     *
     * @return
     */
    @ApiOperation(value = "定时任务执行日志列表页", notes = "定时任务执行日志列表页")
    @GetMapping("/init/{id}")
    public String init(@PathVariable Integer id,
                       Model model) {
        model.addAttribute("quartzId", id);
        return "system/sysquartz/sysquartz_log";
    }


    /**
     * 获取定时任务列表(分页)
     *
     * @param quartzId
     * @return
     */
    @ApiOperation(value = "定时任务执行日志列表", notes = "定时任务执行日志列表")
    @PostMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam("quartzId") Integer quartzId) {
        if (logger.isInfoEnabled()) {
            logger.info("SysQuartzLogController - list - quartzId：" + quartzId);
        }
        List<SysQuartzLog> quartzLogList = sysQuartzLogService.getAllLogByQuartzId(quartzId);
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, quartzLogList);
        result.put(RESULT_TOTLAL, quartzLogList.size());
        return BaseResult.success(result);
    }


}
