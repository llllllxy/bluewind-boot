package com.bluewind.boot.module.system.sqlmonitor.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuxingyu01
 * @date 2022-08-24 16:25
 * @description sqlmonitor监控控制器
 **/
@Controller
@RequestMapping("/sqlmonitor")
@Api(value = "sqlmonitor控制器", tags = "sqlmonitor控制器")
public class SqlMonitorController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SqlMonitorController.class);

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "SSE模式获取执行sql数据")
    @LogAround("SSE模式获取执行sql数据")
    @GetMapping(value = "/getWithSse", produces = "text/event-stream;charset=UTF-8")
    @ResponseBody
    public String getWithSse() throws Exception {
        String redisKey = SystemConst.SYSTEM_SQLMONITOR + ":" + getUserKey();

        List<Object> list = new ArrayList<>();
        Long size = redisUtil.lGetListSize(redisKey);
        if (size != null && size > 0) {
            // 循环出栈，一次取出多条
            for (int i = 0; i < size; i++) {
                Object result = redisUtil.lLeftPop(redisKey);
                if (!Objects.isNull(result)) {
                    list.add(result);
                }
            }
        }

        // 参考：https://zhuanlan.zhihu.com/p/21639227
        //      https://www.jianshu.com/p/3d7b0bbf435a
        // 类型为空白，表示该行是注释，会在处理时被忽略。
        // 类型为 data，表示该行包含的是数据。以 data 开头的行可以出现多次。所有这些行都是该事件的数据。
        // 类型为 event，表示该行用来声明事件的类型。浏览器在收到数据时，会产生对应类型的事件。
        // 类型为 id，表示该行用来声明事件的标识符。
        // 类型为 retry，表示该行用来声明浏览器在连接断开之后进行再次连接之前的等待时间。

        String result = "data:" + JsonTool.toJsonString(list) + "\n"  // 消息数据
                + "retry:2000" + "\n"  // 重连时间
                + "\n\n"; // 消息结束

        return result;
    }


    @ApiOperation(value = "sqlmonitor页面初始化", notes = "sqlmonitor页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        return "system/sqlmonitor/list";
    }

}
