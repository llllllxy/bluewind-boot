package com.bluewind.boot.module.sys.sysjoblog.controller;

import com.bluewind.boot.module.sys.sysjoblog.entity.SysJobLog;
import com.bluewind.boot.module.sys.sysjoblog.service.SysJobLogService;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
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
 * @date 2021-08-27-16:23
 **/
@Controller
@RequestMapping("/sysjoblog")
public class SysJobLogcontroller extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysJobLogcontroller.class);

    @Autowired
    private SysJobLogService jobLogService;


    /**
     * 定时任务执行日志列表页
     *
     * @return
     */
    @ApiOperation(value = "定时任务执行日志列表页", notes = "定时任务执行日志列表页")
    @GetMapping("/init/{jobId}")
    public String init(@PathVariable String jobId,
                       Model model) {
        model.addAttribute("jobId", jobId);
        return "system/sysjob/sysjob_log";
    }


    /**
     * 获取定时任务列表(不分页)
     *
     * @param jobId
     * @return
     */
    @ApiOperation(value = "定时任务执行日志列表", notes = "定时任务执行日志列表")
    @PostMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam("jobId") String jobId) {
        if (logger.isInfoEnabled()) {
            logger.info("SysJobLogcontroller - list - jobId：" + jobId);
        }
        List<SysJobLog> quartzLogList = jobLogService.getLogByJobId(jobId);
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, quartzLogList);
        result.put(RESULT_TOTLAL, quartzLogList.size());
        return BaseResult.success(result);
    }


}
