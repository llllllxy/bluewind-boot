package com.bluewind.boot.module.sys.syspermissioninfo.controller;

import com.bluewind.boot.module.sys.syspermissioninfo.entity.SysPermissionInfo;
import com.bluewind.boot.module.sys.syspermissioninfo.service.SysPermissionInfoService;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
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
@RequestMapping("/syspermission")
public class SysPermissionInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysPermissionInfoController.class);

    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/SysPermissionInfoInit", method = RequestMethod.GET)
    public String SysRoleInfoInit() {
        return "system/syspermissioninfo/syspermissioninfo_list";
    }


    /**
     * 获取菜单列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        List<SysPermissionInfo> list = sysPermissionInfoService.list();

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
        int num = sysPermissionInfoService.forbid(permissionId);
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
        int num = sysPermissionInfoService.enable(permissionId);
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
        int num = sysPermissionInfoService.delete(permissionId);
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
        return "system/syspermissioninfo/syspermissioninfo_add";
    }


    /**
     * 根据角色roleId查询菜单权限树
     * @param type
     * @return
     */
    @RequestMapping(value = "listPermissionByType/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String listPermissionByType(@PathVariable String type) {
        return sysPermissionInfoService.listPermissionByType(type);
    }



    /**
     * 更改菜单顺序
     * @return
     */
    @RequestMapping(value="/updateSort/{permissionId}/{sort}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult updateSort(@PathVariable String permissionId,
                                 @PathVariable Integer sort){
        SysPermissionInfo sysPermissionInfo = new SysPermissionInfo();
        sysPermissionInfo.setPermissionId(permissionId);
        sysPermissionInfo.setSort(sort);
        int num = sysPermissionInfoService.updateSort(sysPermissionInfo);
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
                            @RequestParam(required = false, defaultValue = "", value = "uppermenuValue") String uppermenuValue,
                            @RequestParam(required = false, defaultValue = "", value = "href") String href,
                            @RequestParam(required = false, defaultValue = "", value = "target") String target,
                            @RequestParam(required = false, defaultValue = "", value = "icon") String icon,
                            @RequestParam(required = false, defaultValue = "", value = "descript") String descript,
                            @RequestParam("sort") Integer sort) {
        SysPermissionInfo sysPermissionInfo = new SysPermissionInfo();
        // 新增模块
        if (StringUtils.isBlank(uppermenuValue)) {
            uppermenuValue = "0";
        }
        sysPermissionInfo.setName(name);
        sysPermissionInfo.setType(type);
        sysPermissionInfo.setSign(sign);
        sysPermissionInfo.setParentId(uppermenuValue);
        sysPermissionInfo.setPermissionId(IdGenerate.nextId());
        sysPermissionInfo.setSort(sort);
        sysPermissionInfo.setHref(href);
        sysPermissionInfo.setTarget(target);
        sysPermissionInfo.setIcon(icon);
        sysPermissionInfo.setDescript(descript);
        sysPermissionInfo.setCreateUser(getSysUserId());

        int num = sysPermissionInfoService.addPermission(sysPermissionInfo);
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
        SysPermissionInfo sysPermissionInfo = sysPermissionInfoService.getOnePermission(permissionId);
        model.addAttribute("sysPermissionInfo", sysPermissionInfo);
        // 获取父级菜单名字
        SysPermissionInfo parentInfo = sysPermissionInfoService.getOnePermission(sysPermissionInfo.getParentId());

        model.addAttribute("parentName", parentInfo == null ? "" : parentInfo.getName());

        // 获取下拉栏枚举值
        List<Map<String, String>> baseDictList = DictUtils.getDictList("permission_target");
        model.addAttribute("baseDictList", baseDictList);

        return "system/syspermissioninfo/syspermissioninfo_update";
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
        SysPermissionInfo sysPermissionInfo = new SysPermissionInfo();
        sysPermissionInfo.setName(name);
        sysPermissionInfo.setSign(sign);
        sysPermissionInfo.setPermissionId(permissionId);
        sysPermissionInfo.setSort(sort);
        sysPermissionInfo.setHref(href);
        sysPermissionInfo.setTarget(target);
        sysPermissionInfo.setIcon(icon);
        sysPermissionInfo.setDescript(descript);
        sysPermissionInfo.setUpdateUser(getSysUserId());

        int num = sysPermissionInfoService.updatePermission(sysPermissionInfo);
        if (num > 0) {
            return BaseResult.success("修改菜单成功!");
        } else {
            return BaseResult.failure("修改菜单失败!");
        }
    }
}
