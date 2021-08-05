package com.liuxingyu.meco.sys.sysquartz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxingyu.meco.common.base.BaseController;
import com.liuxingyu.meco.common.utils.BaseDictUtils;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.sys.sysquartz.entity.SysQuartz;
import com.liuxingyu.meco.sys.sysquartz.service.SysQuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.CronExpression;
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
 * @date 2021-03-07-19:18
 * @description 定时器管理
 **/
@Api(tags = "定时任务管理")
@Controller
@RequestMapping("/sysquartz")
public class SysQuartzController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysQuartzController.class);

    @Autowired
    private SysQuartzService sysQuartzService;

    /**
     * 请求定时任务管理列表页
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "请求定时任务管理列表页", notes = "请求定时任务管理列表页")
    @GetMapping("/init")
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> statusList = BaseDictUtils.getDictList("quartz_status");
        List<Map<String, String>> dataStatusList = BaseDictUtils.getDictList("quartz_data_status");

        model.addAttribute("statusList", statusList);
        model.addAttribute("dataStatusList", dataStatusList);
        return "system/sysquartz/sysquartz_list";
    }

    /**
     * 获取定时任务列表(分页)
     *
     * @param pageNum
     * @param pageSize
     * @param className
     * @param quartzStatus
     * @param status
     * @param sortName
     * @param sortOrder
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "name") String name,
                           @RequestParam(required = false, defaultValue = "", value = "className") String className,
                           @RequestParam(required = false, defaultValue = "", value = "quartzStatus") String quartzStatus,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysQuartzController - list - 页面大小：" + pageSize + " -页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("name", name);
        paraMap.put("className", className);
        paraMap.put("quartzStatus", quartzStatus);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        List<SysQuartz> quartzList = sysQuartzService.list(paraMap);

        PageInfo<SysQuartz> pageinfo = new PageInfo<>(quartzList);

        List<SysQuartz> rows = pageinfo.getList();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, pageinfo.getTotal());

        return BaseResult.success(result);
    }


    /**
     * 启动任务
     */
    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult start(@PathVariable Integer id) {
        int num = sysQuartzService.startJob(id);
        if (num > 0) {
            return BaseResult.success("启动成功");
        } else {
            return BaseResult.failure("定时任务启动失败，请检查任务类名！");
        }
    }


    /**
     * 停止任务
     */
    @RequestMapping(value = "/stop/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult stop(@PathVariable Integer id) {
        int num = sysQuartzService.stopJob(id);
        if (num > 0) {
            return BaseResult.success("停止成功");
        } else {
            return BaseResult.failure("定时任务停止失败，请检查任务类名！");
        }
    }


    /**
     * 定时任务新增页
     *
     * @return
     */
    @ApiOperation(value = "定时任务新增页", notes = "定时任务新增页")
    @GetMapping("/forAdd")
    public String forAdd() {
        return "system/sysquartz/sysquartz_add";
    }


    /**
     * 任务新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam("className") String className,
                          @RequestParam("name") String name,
                          @RequestParam("cronExpression") String cronExpression,
                          @RequestParam(required = false, defaultValue = "", value = "param") String param,
                          @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        // 判断Cron表达式是否正确
        if (CronExpression.isValidExpression(cronExpression)) {
            SysQuartz sysQuartz = new SysQuartz();
            sysQuartz.setClassName(className);
            sysQuartz.setName(name);
            sysQuartz.setCronExpression(cronExpression);
            sysQuartz.setParam(param);
            sysQuartz.setDescript(descript);
            sysQuartz.setCreateUser(getSysUserId());
            int num = sysQuartzService.addOneQuartz(sysQuartz);
            if (num > 0) {
                return BaseResult.success("新增任务成功！");
            } else {
                return BaseResult.failure("新增任务失败，请联系后台管理员！");
            }
        } else {
            return BaseResult.failure("请检查Cron表达式是否正确！");
        }
    }


    /**
     * 删除任务
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable Integer id) {
        int num = sysQuartzService.deleteOneById(id);
        if (num > 0) {
            return BaseResult.success("删除成功！");
        } else {
            return BaseResult.failure("删除定时任务失败！");
        }
    }


    /**
     * 定时任务修改页
     *
     * @return
     */
    @ApiOperation(value = "定时任务修改页", notes = "定时任务修改页")
    @GetMapping("/forUpdate/{id}")
    public String forUpdate(Model model, @PathVariable Integer id) {
        SysQuartz sysQuartz = sysQuartzService.getOneQuqrtz(id);
        model.addAttribute("sysQuartz", sysQuartz);
        List<Map<String, String>> dataStatusList = BaseDictUtils.getDictList("quartz_data_status");
        model.addAttribute("dataStatusList", dataStatusList);
        return "system/sysquartz/sysquartz_update";
    }


    /**
     * 任务修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("name") String name,
                             @RequestParam("className") String className,
                             @RequestParam("cronExpression") String cronExpression,
                             @RequestParam("status") Integer status,
                             @RequestParam("id") Integer id,
                             @RequestParam(required = false, defaultValue = "", value = "param") String param,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        // 判断Cron表达式是否正确
        if (CronExpression.isValidExpression(cronExpression)) {
            SysQuartz sysQuartz = new SysQuartz();
            sysQuartz.setClassName(className);
            sysQuartz.setName(name);
            sysQuartz.setCronExpression(cronExpression);
            sysQuartz.setStatus(status);
            sysQuartz.setId(id);
            sysQuartz.setParam(param);
            sysQuartz.setDescript(descript);
            sysQuartz.setCreateUser(getSysUserId());
            int num = sysQuartzService.updateOneQuartz(sysQuartz);
            if (num > 0) {
                return BaseResult.success("修改任务成功！");
            } else {
                return BaseResult.failure("修改任务失败，请联系后台管理员！");
            }
        } else {
            return BaseResult.failure("请检查Cron表达式是否正确！");
        }
    }

}
