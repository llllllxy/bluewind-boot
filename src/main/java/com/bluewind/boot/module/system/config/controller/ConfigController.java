package com.bluewind.boot.module.system.config.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.module.system.config.entity.Config;
import com.bluewind.boot.module.system.config.service.ConfigService;
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
public class ConfigController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigService configService;

    /**
     * 系统配置修改页面
     *
     * @return
     */
    @RequestMapping(value = "/forUpdate", method = RequestMethod.GET)
    public String forUpdateDetail(Model model) {
        Config config = configService.getSysConfig();
        model.addAttribute("config", config);
        return "system/config/index";
    }


    /**
     * 系统配置修改
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestBody Config config) {
        config.setUpdateUser(getSysUserId());
        int num = configService.updateSysConfig(config);
        if (num > 0) {
            return BaseResult.success("系统配置更新成功！");
        } else {
            return BaseResult.failure("系统配置更新失败！");
        }
    }


}
