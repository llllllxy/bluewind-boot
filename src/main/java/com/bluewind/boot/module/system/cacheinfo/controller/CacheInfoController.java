package com.bluewind.boot.module.system.cacheinfo.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author liuxingyu01
 * @date 2022-04-25 13:04
 * @description redis缓存监控
 **/
@Api(value = "redis缓存监控", tags = "redis缓存监控", description = "redis缓存监控")
@Controller
@RequestMapping("/cacheinfo")
public class CacheInfoController extends BaseController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/init")
    public String init() throws Exception {
        return "system/cacheinfo/index";
    }

    @GetMapping("/getInfo")
    @ResponseBody
    public BaseResult getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return BaseResult.success("获取缓存数据成功！", result);
    }
}
