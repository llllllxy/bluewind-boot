package com.bluewind.boot.module.sys.sysroleinfo.controller;

import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.module.sys.sysroleinfo.entity.SysRoleInfo;
import com.bluewind.boot.module.sys.sysroleinfo.service.SysRoleInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.sys.syspermissioninfo.service.SysPermissionInfoService;
import com.bluewind.boot.module.sys.sysrolepermission.service.SysRolePermissionService;
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
 * @date 2021-01-31-23:29
 **/
@Controller
@RequestMapping("/sysrole")
public class SysRoleInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysRoleInfoController.class);

    @Autowired
    private SysRoleInfoService sysRoleInfoService;

    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/SysRoleInfoInit", method = RequestMethod.GET)
    public String SysRoleInfoInit(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> baseDictList = DictUtils.getDictList("role_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/sysroleinfo/sysroleinfo_list";
    }


    /**
     * 角色管理页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @LogAround("角色管理页面分页查询")
    @ResponseBody
    @RequestMapping(value = "/getSysRoleInfoList", method = RequestMethod.POST)
    public BaseResult getSysRoleInfoList(@RequestParam("page") Integer pageNum,
                                         @RequestParam("limit") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "", value = "sign") String sign,
                                         @RequestParam(required = false, defaultValue = "", value = "name") String name,
                                         @RequestParam(required = false, defaultValue = "", value = "status") String status,
                                         @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                                         @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("getSysRoleInfoList -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("sign", sign);
        paraMap.put("name", name);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<SysRoleInfo> roleList = sysRoleInfoService.getSysRoleInfoList(paraMap);

        PageInfo<SysRoleInfo> pageinfo = new PageInfo<>(roleList);
        //取出查询结果
        List<SysRoleInfo> rows = pageinfo.getList();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, pageinfo.getTotal());

        return BaseResult.success(result);
    }


    /**
     * 角色新增页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "system/sysroleinfo/sysroleinfo_add";
    }


    /**
     * 角色新增
     *
     * @return
     */
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doAdd(@RequestParam(value = "name") String name,
                            @RequestParam(value = "sign") String sign,
                            @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        if (logger.isInfoEnabled()) {
            logger.info("doAdd -- name =  {}：" + name);
        }
        SysRoleInfo sysRoleInfo = new SysRoleInfo();
        sysRoleInfo.setRoleId(IdGenerate.nextId());
        sysRoleInfo.setName(name);
        sysRoleInfo.setSign(sign);
        sysRoleInfo.setDescript(descript);
        sysRoleInfo.setStatus("0");
        sysRoleInfo.setDelFlag("0");
        sysRoleInfo.setCreateUser(getSysUserId());

        int num = sysRoleInfoService.doAdd(sysRoleInfo);
        if (num > 0) {
            return BaseResult.success("新增角色成功!");
        } else {
            return BaseResult.failure("新增角色失败!");
        }
    }


    /**
     * 删除一个系统角色（这里用逻辑删除）
     * @return
     */
    @RequestMapping(value="/delete/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String roleId){
        int num = sysRoleInfoService.delete(roleId);
        if (num > 0) {
            return BaseResult.success("删除成功!");
        } else {
            return BaseResult.failure("删除失败!");
        }
    }


    /**
     * 禁用一个系统角色
     * @return
     */
    @RequestMapping(value="/forbid/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult forbid(@PathVariable String roleId){
        int num = sysRoleInfoService.forbid(roleId);
        if (num > 0) {
            return BaseResult.success("禁用成功!");
        } else {
            return BaseResult.failure("禁用失败!");
        }
    }



    /**
     * 启用一个系统角色
     * @return
     */
    @RequestMapping(value="/enable/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult enable(@PathVariable String roleId){
        int num = sysRoleInfoService.enable(roleId);
        if (num > 0) {
            return BaseResult.success("启用成功!");
        } else {
            return BaseResult.failure("启用失败!");
        }
    }



    /**
     * 角色修改页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/update/{roleId}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable String roleId) {
        SysRoleInfo sysRoleInfo = sysRoleInfoService.getOneRoleById(roleId);
        model.addAttribute("sysRoleInfo", sysRoleInfo);
        return "system/sysroleinfo/sysroleinfo_update";
    }



    /**
     * 角色更新
     *
     * @return
     */
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doUpdate(@RequestParam(value = "name") String name,
                               @RequestParam(value = "sign") String sign,
                               @RequestParam(value = "roleId") String roleId,
                               @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        if (logger.isInfoEnabled()) {
            logger.info("doUpdate -- name =  {}：" + name);
        }
        SysRoleInfo sysRoleInfo = new SysRoleInfo();
        sysRoleInfo.setRoleId(roleId);
        sysRoleInfo.setName(name);
        sysRoleInfo.setSign(sign);
        sysRoleInfo.setDescript(descript);
        sysRoleInfo.setUpdateUser(getSysUserId());

        int num = sysRoleInfoService.doUpdate(sysRoleInfo);
        if (num > 0) {
            return BaseResult.success("角色更新成功!");
        } else {
            return BaseResult.failure("角色更新失败!");
        }
    }


    /**
     * 根据角色roleId查询菜单权限树
     * @param roleId
     * @return
     */
    @RequestMapping(value = "listPermissionForTree/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public String listPermissionForTree(@PathVariable String roleId) {
        return sysPermissionInfoService.listPermissionForTree(roleId);
    }


    /**
     * 角色授权页面
     *
     * @return
     */
    @RequestMapping(value = "/authorize/{roleId}", method = RequestMethod.GET)
    public String authorize(Model model,
                            @PathVariable String roleId) {
        SysRoleInfo sysRoleInfo = sysRoleInfoService.getOneRoleById(roleId);
        model.addAttribute("sysRoleInfo", sysRoleInfo);
        return "system/sysroleinfo/sysroleinfo_auth";
    }



    /**
     * 角色权限更新
     *
     * @return
     */
    @RequestMapping(value = "/doAuthorize", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doAuthorize(@RequestParam(value = "roleId") String roleId,
                                  @RequestParam(value = "permIds") String permIds) {
        if (logger.isInfoEnabled()) {
            logger.info("doAuthorize -- permIds =  {}：" + permIds);
        }
        int num = sysRolePermissionService.doAuthorize(roleId, permIds);
        if (num > 0) {
            return BaseResult.success("菜单权限更新成功!");
        } else {
            return BaseResult.failure("菜单权限更新失败!");
        }
    }


}
