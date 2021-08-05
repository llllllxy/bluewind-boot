package com.liuxingyu.meco.configuration.errordeal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liuxingyu01
 * @date 2020-11-12-19:40
 * @description 自定义错误页面
 **/
@Controller
@RequestMapping("/error")
public class ErrorDealController {

    //404页面
    @RequestMapping(value = "/error404", method = RequestMethod.GET)
    public String error404() {
        return "error/404";
    }

    //500页面
    @RequestMapping(value = "/error500", method = RequestMethod.GET)
    public String error500() {
        return "error/500";
    }

    //500页面
    @RequestMapping(value = "/error403", method = RequestMethod.GET)
    public String error403() {
        return "error/403";
    }
}
