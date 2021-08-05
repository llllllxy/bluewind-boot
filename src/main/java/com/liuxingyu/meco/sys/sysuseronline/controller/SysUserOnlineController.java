package com.liuxingyu.meco.sys.sysuseronline.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxingyu.meco.common.annotation.LogAround;
import com.liuxingyu.meco.common.base.BaseController;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.sys.sysloginlog.service.SysLoginLogService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuxingyu01
 * @date 2021-07-24-11:06
 **/
@Controller
@RequestMapping("/sysuseronline")
public class SysUserOnlineController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysUserOnlineController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysLoginLogService sysLoginLogService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @ApiOperation(value = "分页查询页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/sysuseronline/sysuseronline_list";
    }


    /**
     * itfc-key页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询")
    @LogAround("sysuseronline页面分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "ipaddr") String ipaddr,
                           @RequestParam(required = false, defaultValue = "", value = "account") String account) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysUserOnlineController -- list -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }

        Set<String> keysSet = redisUtil.keys( "shiro:session:");
        List<String> keysList = new ArrayList<>();
        keysSet.forEach(item -> {
            String[] strs = item.split(":");
            keysList.add(strs[2]);
        });
        if (logger.isInfoEnabled()) {
            logger.info("SysUserOnlineController - list - keysList：{}", keysList);
        }
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("ipaddr", ipaddr);
        paraMap.put("account", account);
        paraMap.put("keysList", keysList);

        List<Map> resultList = sysLoginLogService.onlineList(paraMap);
        if (logger.isInfoEnabled()) {
            logger.info("SysUserOnlineController - list - resultList：{}", resultList);
        }
        // 对时间进行格式化
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        resultList.forEach(item-> {
            Date createTime = item.get("create_time") == null ? null : (Date) item.get("create_time");
            if (createTime != null) {
                item.put("create_time", simpleDateFormat.format(createTime));
            }
        });

        PageInfo<Map> pageinfo = new PageInfo<>(resultList);
        // 取出查询结果
        List<Map> rows = pageinfo.getList();
        Map<String, Object> result = new HashMap<>();

        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, pageinfo.getTotal());

        return BaseResult.success(result);
    }


    /**
     * 强退用户
     */
    @ApiOperation(value = "强退用户")
    @GetMapping("/forceLogout/{session_id}")
    @ResponseBody
    public BaseResult forceLogout(@PathVariable String session_id) {
        redisUtil.del("shiro:session:" + session_id);
        return BaseResult.success();
    }

}
