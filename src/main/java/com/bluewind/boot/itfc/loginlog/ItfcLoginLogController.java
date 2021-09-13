package com.bluewind.boot.itfc.loginlog;

import com.bluewind.boot.common.annotation.ItfcPermissions;
import com.bluewind.boot.common.base.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * @author liuxingyu01
 * @date 2021-06-11-21:19
 * @description rest服务模块测试
 **/
@Controller
@RequestMapping("/itfc")
public class ItfcLoginLogController {
    final static Logger logger = LoggerFactory.getLogger(ItfcLoginLogController.class);

    @ItfcPermissions("itfc:loginlog:getAllLoginLog")
    @RequestMapping(value = "/getAllLoginLog", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getAllLoginLog() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "我成功了");
        return BaseResult.success("访问成功", map);
    }


    @ItfcPermissions("itfc:loginlog:getSomeLoginLog")
    @RequestMapping(value = "/getSomeLoginLog", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getSomeLoginLog() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "嘿嘿嘿嘿");
        return BaseResult.success("访问成功", map);
    }

}
