package com.bluewind.boot.module.sys.sysjob.controller;

import com.bluewind.boot.common.exception.TaskException;
import com.bluewind.boot.common.configuration.quartz.CronUtils;
import com.bluewind.boot.module.sys.sysjob.entity.SysJob;
import com.bluewind.boot.module.sys.sysjob.service.SysJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.BaseDictUtils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import io.swagger.annotations.ApiOperation;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
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
 * @date 2021-08-27-16:38
 **/
@Controller
@RequestMapping("/sysjob")
public class SysJobController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysJobController.class);

    @Autowired
    private SysJobService sysJobService;

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
        List<Map<String, String>> sysJobGroupDict = BaseDictUtils.getDictList("sys_job_group");
        List<Map<String, String>> sysJobStatusDict = BaseDictUtils.getDictList("sys_job_status");
        model.addAttribute("sysJobGroupDict", sysJobGroupDict);
        model.addAttribute("sysJobStatusDict", sysJobStatusDict);
        return "system/sysjob/sysjob_list";
    }


    /**
     * 获取定时任务列表(分页)
     *
     * @param pageNum
     * @param pageSize
     * @param jobName
     * @param jobGroup
     * @param status
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "jobName") String jobName,
                           @RequestParam(required = false, defaultValue = "", value = "jobGroup") String jobGroup,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysJobController - list - 页面大小：" + pageSize + " -页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("jobName", jobName);
        paraMap.put("jobGroup", jobGroup);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        List<SysJob> list = sysJobService.list(paraMap);

        PageInfo<SysJob> pageinfo = new PageInfo<>(list);

        List<SysJob> sysJobList = pageinfo.getList();

        Map<String, String> sysJobGroupDict = BaseDictUtils.getDictMap("sys_job_group");

        sysJobList.forEach(item -> {
            item.setJobGroup(sysJobGroupDict.get(item.getJobGroup()));
        });

        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, sysJobList);
        result.put(RESULT_TOTLAL, pageinfo.getTotal());

        return BaseResult.success(result);
    }


    /**
     * 执行一次
     *
     * @return
     */
    @ApiOperation(value = "执行一次", notes = "执行一次")
    @ResponseBody
    @GetMapping("/executeonce/{jobId}")
    public BaseResult executeonce(@PathVariable String jobId) throws SchedulerException {
        sysJobService.executeonce(jobId);
        return BaseResult.success("执行一次成功！");
    }


    /**
     * 启动
     *
     * @return
     */
    @ApiOperation(value = "启动", notes = "启动")
    @ResponseBody
    @GetMapping("/start/{jobId}")
    public BaseResult start(@PathVariable String jobId) throws SchedulerException {
        int num = sysJobService.start(jobId);
        if (num > 0) {
            return BaseResult.success("启动成功！");
        } else {
            return BaseResult.failure("启动失败，请联系后台管理员！");
        }
    }


    /**
     * 停止
     *
     * @return
     */
    @ApiOperation(value = "停止", notes = "停止")
    @ResponseBody
    @GetMapping("/stop/{jobId}")
    public BaseResult stop(@PathVariable String jobId) throws SchedulerException {
        int num = sysJobService.stop(jobId);

        if (num > 0) {
            return BaseResult.success("停止成功！");
        } else {
            return BaseResult.failure("停止失败，请联系后台管理员！");
        }
    }


    /**
     * 删除
     *
     * @return
     */
    @ApiOperation(value = "删除", notes = "删除")
    @ResponseBody
    @GetMapping("/delete/{jobId}")
    public BaseResult delete(@PathVariable String jobId) throws SchedulerException, TaskException {
        int num = sysJobService.delete(jobId);

        if (num > 0) {
            return BaseResult.success("删除定时任务成功！");
        } else {
            return BaseResult.failure("删除定时任务失败，请联系后台管理员！");
        }
    }




    /**
     * 定时任务新增页
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "定时任务新增页", notes = "定时任务新增页")
    @GetMapping("/forAdd")
    public String forAdd(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> sysJobGroupDict = BaseDictUtils.getDictList("sys_job_group");
        model.addAttribute("sysJobGroupDict", sysJobGroupDict);
        return "system/sysjob/sysjob_add";
    }


    /**
     * 任务新增（默认不启动）
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam("jobName") String jobName,
                          @RequestParam("jobGroup") String jobGroup,
                          @RequestParam("invokeTarget") String invokeTarget,
                          @RequestParam("cronExpression") String cronExpression,
                          @RequestParam(required = false, defaultValue = "1", value = "concurrent") String concurrent,
                          @RequestParam(required = false, defaultValue = "3", value = "misfirePolicy") String misfirePolicy) throws SchedulerException, TaskException {
        // 判断Cron表达式是否正确
        if (CronExpression.isValidExpression(cronExpression)) {
            SysJob sysJob = new SysJob();
            sysJob.setJobId(IdGenerate.uuid());
            sysJob.setJobName(jobName);
            sysJob.setJobGroup(jobGroup);
            sysJob.setInvokeTarget(invokeTarget);
            sysJob.setCronExpression(cronExpression);
            sysJob.setConcurrent(concurrent);
            sysJob.setMisfirePolicy(misfirePolicy);
            sysJob.setCreateUser(getSysUserId());

            int num = sysJobService.insertJob(sysJob);
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
     * 定时任务修改页
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "定时任务修改页", notes = "定时任务修改页")
    @GetMapping("/forUpdate/{jobId}")
    public String forUpdate(@PathVariable String jobId, Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> sysJobGroupDict = BaseDictUtils.getDictList("sys_job_group");
        model.addAttribute("sysJobGroupDict", sysJobGroupDict);

        SysJob sysJob = sysJobService.getOne(jobId);
        model.addAttribute("sysJob", sysJob);

        return "system/sysjob/sysjob_update";
    }


    /**
     * 任务更新
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("jobId") String jobId,
                             @RequestParam("jobName") String jobName,
                             @RequestParam("jobGroup") String jobGroup,
                             @RequestParam("invokeTarget") String invokeTarget,
                             @RequestParam("cronExpression") String cronExpression,
                             @RequestParam("status") String status,
                             @RequestParam(required = false, defaultValue = "1", value = "concurrent") String concurrent,
                             @RequestParam(required = false, defaultValue = "3", value = "misfirePolicy") String misfirePolicy) throws SchedulerException, TaskException {
        // 判断Cron表达式是否正确
        if (CronExpression.isValidExpression(cronExpression)) {
            SysJob sysJob = new SysJob();
            sysJob.setJobId(jobId);
            sysJob.setJobName(jobName);
            sysJob.setJobGroup(jobGroup);
            sysJob.setInvokeTarget(invokeTarget);
            sysJob.setCronExpression(cronExpression);
            sysJob.setStatus(status);
            sysJob.setConcurrent(concurrent);
            sysJob.setMisfirePolicy(misfirePolicy);
            sysJob.setUpdateUser(getSysUserId());

            int num = sysJobService.updateJob(sysJob);
            if (num > 0) {
                return BaseResult.success("更新任务成功！");
            } else {
                return BaseResult.failure("更新任务失败，请联系后台管理员！");
            }
        } else {
            return BaseResult.failure("请检查Cron表达式是否正确！");
        }
    }


    /**
     * Cron表达式生成器页面
     *
     * @return
     */
    @ApiOperation(value = "Cron表达式生成器页面", notes = "Cron表达式生成器页面")
    @GetMapping("/forCron")
    public String forCron(Model model,
                          @RequestParam(required = false, defaultValue = "", value = "cronExpression") String cronExpression) {
        if (logger.isInfoEnabled()) {
            logger.info("SysJobController - forCron - cronExpression：" + cronExpression);
        }
        model.addAttribute("cronExpression", cronExpression);
        return "system/sysjob/sysjob_cron";
    }


    @GetMapping("/getNextExecTime")
    @ResponseBody
    public BaseResult getNextExecTime(@RequestParam("CronExpression")String CronExpression) {
        List<String> list = CronUtils.getNextExecTime(CronExpression, 10);
        return BaseResult.success(list);
    }

}
