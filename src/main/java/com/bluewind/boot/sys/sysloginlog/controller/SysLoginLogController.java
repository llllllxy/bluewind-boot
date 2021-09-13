package com.bluewind.boot.sys.sysloginlog.controller;

import com.bluewind.boot.sys.sysloginlog.entity.SysLoginLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.annotation.DataSourceWith;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.enums.DataSourceType;
import com.bluewind.boot.common.utils.BaseDictUtils;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.sys.sysloginlog.service.SysLoginLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-19-12:47
 **/
@Controller
@RequestMapping("/sysloginlog")
public class SysLoginLogController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysLoginLogController.class);

    private SysLoginLogService sysLoginLogService;
    //  这里是基于构造函数的依赖注入，而不是@Autowired
    public SysLoginLogController(SysLoginLogService sysLoginLogService) {
        this.sysLoginLogService = sysLoginLogService;
    }


    /**
     * 页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = BaseDictUtils.getDictList("login_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/sysloginlog/sysloginlog_list";
    }


    /**
     * 登陆日志页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @DataSourceWith(DataSourceType.SLAVE)
    @LogAround("登陆日志页面分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "account") String account,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "createTime") String createTime,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {

        //查询条件得加上
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysLoginLogController -- list -- 页面大小："+pageSize+"--页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("account", account);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        paraMap.put("createTime", createTime);

        List<SysLoginLog> accounts = sysLoginLogService.list(paraMap);

        PageInfo<SysLoginLog> pageinfo = new PageInfo<>(accounts);
        //取出查询结果
        List<SysLoginLog> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, total);

        return BaseResult.success(result);
    }




}
