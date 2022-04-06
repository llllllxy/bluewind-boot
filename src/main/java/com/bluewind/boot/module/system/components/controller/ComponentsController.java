package com.bluewind.boot.module.system.components.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuxingyu01
 * @date 2021-04-27-18:43
 **/
@Api(tags = "后台管理端--组件专区")
@Controller
@RequestMapping("/components")
public class ComponentsController {


    @GetMapping("/html404")
    public String html404() {
        return "system/components/404";
    }

    @GetMapping("/html500")
    public String html500() {
        return "system/components/500";
    }

    @GetMapping("/area")
    public String area() {
        return "system/components/area";
    }

    @GetMapping("/button")
    public String button() {
        return "system/components/button";
    }

    @GetMapping("/colorSelect")
    public String colorSelect() {
        return "system/components/color-select";
    }

    @GetMapping("/editor")
    public String editor() {
        return "system/components/editor";
    }

    @GetMapping("/form")
    public String form() {
        return "system/components/form";
    }

    @GetMapping("/formStep")
    public String formStep() {
        return "system/components/form-step";
    }

    @GetMapping("/icon")
    public String icon() {
        return "system/components/icon";
    }

    @GetMapping("/iconPicker")
    public String iconPicker() {
        return "system/components/icon-picker";
    }

    @GetMapping("/layer")
    public String layer() {
        return "system/components/layer";
    }

    @GetMapping("/login1")
    public String login1() {
        return "system/components/login-1";
    }

    @GetMapping("/login2")
    public String login2() {
        return "system/components/login-2";
    }

    @GetMapping("/login3")
    public String login3() {
        return "system/components/login-3";
    }

    @GetMapping("/menu")
    public String menu() {
        return "system/components/menu";
    }

    @GetMapping("/setting")
    public String setting() {
        return "system/components/setting";
    }

    @GetMapping("/table")
    public String table() {
        return "system/components/table";
    }

    @GetMapping("/tableAdd")
    public String tableAdd() {
        return "system/components/table/add";
    }

    @GetMapping("/tableEdit")
    public String tableEdit() {
        return "system/components/table/edit";
    }

    @GetMapping("/tableSelect")
    public String tableSelect() {
        return "system/components/table-select";
    }

    @GetMapping("/upload")
    public String upload() {
        return "system/components/upload";
    }

    @GetMapping("/userPassword")
    public String userPassword() {
        return "system/components/user-password";
    }

    @GetMapping("/userSetting")
    public String userSetting() {
        return "system/components/user-setting";
    }

    @GetMapping("/welcome1")
    public String welcome1() {
        return "system/components/welcome-1";
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return "system/components/welcome-1";
    }

    @GetMapping("/welcome3")
    public String welcome3() {
        return "system/components/welcome-3";
    }

}
