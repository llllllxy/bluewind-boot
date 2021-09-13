package com.bluewind.boot.sys.sysserverinfo.controller;

import com.bluewind.boot.sys.sysserverinfo.entity.ServerInfo;
import com.bluewind.boot.common.consts.RedisConst;
import com.bluewind.boot.common.utils.DateTool;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-21-14:32
 * @description 服务器监控
 **/
@Api(tags = "服务器监控")
@Controller
@RequestMapping("/sysserverinfo")
public class SysServerInfoController {
    final static Logger logger = LoggerFactory.getLogger(SysServerInfoController.class);

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 页面初始化
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/sysserverinfo/serverinfo";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult list() {
        Object object = redisUtil.get(RedisConst.Key.SYS_SERVER_INFO);
        if (null != object) {
            logger.info("SysServerInfoController - list - 从redis获取缓存成功");
            return BaseResult.success(JsonTool.getMapFromJsonString(object.toString()));
        }
        ServerInfo serverInfo = new ServerInfo();
        try {
            serverInfo.copyTo();
            // 放在redis里面，60秒过期时间
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("data", serverInfo);
            infoMap.put("updateTime", DateTool.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            redisUtil.set(RedisConst.Key.SYS_SERVER_INFO, JsonTool.mapToJsonString(infoMap), 120);
            return BaseResult.success(infoMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.failure("获取服务信息失败");
    }


}
