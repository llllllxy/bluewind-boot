package com.liuxingyu.meco.sys.syscomponents.controller;

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
@RequestMapping("/syscomponents")
public class ComponentsController {


    @GetMapping("/html404")
    public String html404() {
        return "system/syscomponents/404";
    }

    @GetMapping("/html500")
    public String html500() {
        return "system/syscomponents/500";
    }

    @GetMapping("/area")
    public String area() {
        return "system/syscomponents/area";
    }

    @GetMapping("/button")
    public String button() {
        return "system/syscomponents/button";
    }

    @GetMapping("/colorSelect")
    public String colorSelect() {
        return "system/syscomponents/color-select";
    }

    @GetMapping("/editor")
    public String editor() {
        return "system/syscomponents/editor";
    }

    @GetMapping("/form")
    public String form() {
        return "system/syscomponents/form";
    }

    @GetMapping("/formStep")
    public String formStep() {
        return "system/syscomponents/form-step";
    }

    @GetMapping("/icon")
    public String icon() {
        return "system/syscomponents/icon";
    }

    @GetMapping("/iconPicker")
    public String iconPicker() {
        return "system/syscomponents/icon-picker";
    }

    @GetMapping("/layer")
    public String layer() {
        return "system/syscomponents/layer";
    }

    @GetMapping("/login1")
    public String login1() {
        return "system/syscomponents/login-1";
    }

    @GetMapping("/login2")
    public String login2() {
        return "system/syscomponents/login-2";
    }

    @GetMapping("/login3")
    public String login3() {
        return "system/syscomponents/login-3";
    }

    @GetMapping("/menu")
    public String menu() {
        return "system/syscomponents/menu";
    }

    @GetMapping("/setting")
    public String setting() {
        return "system/syscomponents/setting";
    }

    @GetMapping("/table")
    public String table() {
        return "system/syscomponents/table";
    }

    @GetMapping("/tableAdd")
    public String tableAdd() {
        return "system/syscomponents/table/add";
    }

    @GetMapping("/tableEdit")
    public String tableEdit() {
        return "system/syscomponents/table/edit";
    }

    @GetMapping("/tableSelect")
    public String tableSelect() {
        return "system/syscomponents/table-select";
    }

    @GetMapping("/upload")
    public String upload() {
        return "system/syscomponents/upload";
    }

    @GetMapping("/userPassword")
    public String userPassword() {
        return "system/syscomponents/user-password";
    }

    @GetMapping("/userSetting")
    public String userSetting() {
        return "system/syscomponents/user-setting";
    }

    @GetMapping("/welcome1")
    public String welcome1() {
        return "system/syscomponents/welcome-1";
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return "system/syscomponents/welcome-1";
    }

    @GetMapping("/welcome3")
    public String welcome3() {
        return "system/syscomponents/welcome-3";
    }

}
