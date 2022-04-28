package com.bluewind.boot.module.system.permissioninfo.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.permissioninfo.entity.PermissionInfo;
import com.bluewind.boot.module.system.permissioninfo.service.PermissionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-07-16:58
 **/
@Controller
@RequestMapping("/permission")
public class PermissionInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(PermissionInfoController.class);

    @Autowired
    private PermissionInfoService permissionInfoService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/permissioninfo/list";
    }


    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<PermissionInfo> list = permissionInfoService.list();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", list.size());
        resultMap.put("data", list);

        return resultMap;
    }


    /**
     * 禁用一个系统菜单
     * @return
     */
    @RequestMapping(value="/forbid/{permissionId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult forbid(@PathVariable String permissionId){
        int num = permissionInfoService.forbid(permissionId);
        if (num > 0) {
            return BaseResult.success("禁用成功!");
        } else {
            return BaseResult.failure("禁用失败!");
        }
    }



    /**
     * 启用一个系统菜单
     * @return
     */
    @RequestMapping(value="/enable/{permissionId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult enable(@PathVariable String permissionId){
        int num = permissionInfoService.enable(permissionId);
        if (num > 0) {
            return BaseResult.success("启用成功!");
        } else {
            return BaseResult.failure("启用失败!");
        }
    }


    /**
     * 逻辑删除一个系统菜单
     * @return
     */
    @RequestMapping(value="/delete/{permissionId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String permissionId){
        int num = permissionInfoService.delete(permissionId);
        if (num > 0) {
            return BaseResult.success("删除成功!");
        } else {
            return BaseResult.failure("删除失败!");
        }
    }


    /**
     * 新增菜单
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "system/permissioninfo/add";
    }


    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping(value = "/listPermission", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listPermission() {
        List<Map<String, Object>> result = permissionInfoService.listPermission();
        return BaseResult.success("获取菜单列表成功!", result);
    }

    /**
     * 更改菜单顺序
     * @return
     */
    @RequestMapping(value="/updateSort/{permissionId}/{sort}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult updateSort(@PathVariable String permissionId,
                                 @PathVariable Integer sort){
        PermissionInfo permissionInfo = new PermissionInfo();
        permissionInfo.setPermissionId(permissionId);
        permissionInfo.setSort(sort);
        int num = permissionInfoService.updateSort(permissionInfo);
        if (num > 0) {
            return BaseResult.success("修改顺序成功!");
        } else {
            return BaseResult.failure("修改顺序失败!");
        }
    }



    /**
     * 新增
     * @return
     */
    @RequestMapping(value="/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doAdd(@RequestParam("type") String type,
                            @RequestParam("name") String name,
                            @RequestParam("sign") String sign,
                            @RequestParam(required = false, defaultValue = "", value = "parentId") String parentId,
                            @RequestParam(required = false, defaultValue = "", value = "href") String href,
                            @RequestParam(required = false, defaultValue = "", value = "target") String target,
                            @RequestParam(required = false, defaultValue = "", value = "icon") String icon,
                            @RequestParam(required = false, defaultValue = "", value = "descript") String descript,
                            @RequestParam("sort") Integer sort) {
        PermissionInfo permissionInfo = new PermissionInfo();
        // 新增模块
        if (StringUtils.isBlank(parentId)) {
            parentId = "0";
        }
        permissionInfo.setName(name);
        permissionInfo.setType(type);
        permissionInfo.setSign(sign);
        permissionInfo.setParentId(parentId);
        permissionInfo.setPermissionId(IdGenerate.nextId());
        permissionInfo.setSort(sort);
        permissionInfo.setHref(href);
        permissionInfo.setTarget(target);
        permissionInfo.setIcon(icon);
        permissionInfo.setDescript(descript);
        permissionInfo.setCreateUser(getSysUserId());

        int num = permissionInfoService.addPermission(permissionInfo);
        if (num > 0) {
            return BaseResult.success("新增菜单成功!");
        } else {
            return BaseResult.failure("新增菜单失败!");
        }
    }


    /**
     * 修改菜单
     *
     * @return
     */
    @RequestMapping(value = "/update/{permissionId}", method = RequestMethod.GET)
    public String update(Model model,
                         @PathVariable String permissionId) {
        PermissionInfo permissionInfo = permissionInfoService.getOnePermission(permissionId);
        model.addAttribute("permissionInfo", permissionInfo);
        // 获取父级菜单名字
        PermissionInfo parentInfo = permissionInfoService.getOnePermission(permissionInfo.getParentId());

        model.addAttribute("parentName", parentInfo == null ? "" : parentInfo.getName());

        // 获取下拉栏枚举值
        List<Map<String, String>> baseDictList = DictUtils.getDictList("permission_target");
        model.addAttribute("baseDictList", baseDictList);

        return "system/permissioninfo/update";
    }



    /**
     * 修改
     * @return
     */
    @RequestMapping(value="/doUpdate", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doUpdate(@RequestParam("name") String name,
                               @RequestParam("sign") String sign,
                               @RequestParam("permissionId") String permissionId,
                               @RequestParam(required = false, defaultValue = "", value = "href") String href,
                               @RequestParam(required = false, defaultValue = "", value = "target") String target,
                               @RequestParam(required = false, defaultValue = "", value = "icon") String icon,
                               @RequestParam(required = false, defaultValue = "", value = "descript") String descript,
                               @RequestParam("sort") Integer sort) {
        PermissionInfo permissionInfo = new PermissionInfo();
        permissionInfo.setName(name);
        permissionInfo.setSign(sign);
        permissionInfo.setPermissionId(permissionId);
        permissionInfo.setSort(sort);
        permissionInfo.setHref(href);
        permissionInfo.setTarget(target);
        permissionInfo.setIcon(icon);
        permissionInfo.setDescript(descript);
        permissionInfo.setUpdateUser(getSysUserId());

        int num = permissionInfoService.updatePermission(permissionInfo);
        if (num > 0) {
            return BaseResult.success("修改菜单成功!");
        } else {
            return BaseResult.failure("修改菜单失败!");
        }
    }
}
