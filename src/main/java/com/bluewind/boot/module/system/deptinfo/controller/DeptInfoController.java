package com.bluewind.boot.module.system.deptinfo.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.module.system.deptinfo.entity.DeptInfo;
import com.bluewind.boot.module.system.deptinfo.service.DeptInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-03-17 16:47
 * @description
 **/
@Controller
@RequestMapping("/sysdeptinfo")
public class DeptInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(DeptInfoController.class);

    @Autowired
    private DeptInfoService deptInfoService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/deptinfo/list";
    }



    /**
     * 获取部门树列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<DeptInfo> list = deptInfoService.list();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", list.size());
        resultMap.put("data", list);

        return resultMap;
    }


    /**
     * 新增页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "system/deptinfo/add";
    }

}
