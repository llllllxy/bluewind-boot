package com.bluewind.boot.sys.sysconfig.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.sys.sysconfig.entity.SysConfig;
import com.bluewind.boot.sys.sysconfig.service.SysConfigService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-04-21-13:09
 **/
@Api(tags = "系统配置")
@Controller
@RequestMapping("/sysconfig")
public class SysConfigController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 系统配置修改页面
     *
     * @return
     */
    @RequestMapping(value = "/forUpdate", method = RequestMethod.GET)
    public String forUpdateDetail(Model model) {
        SysConfig sysConfig = sysConfigService.getSysConfig();
        model.addAttribute("sysConfig", sysConfig);
        return "system/sysconfig/sysconfig_update";
    }


    /**
     * 系统配置修改
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestBody SysConfig sysConfig) {
        sysConfig.setUpdateUser(getSysUserId());
        int num = sysConfigService.updateSysConfig(sysConfig);
        if (num > 0) {
            return BaseResult.success("系统配置更新成功！");
        } else {
            return BaseResult.failure("系统配置更新失败！");
        }
    }


}
