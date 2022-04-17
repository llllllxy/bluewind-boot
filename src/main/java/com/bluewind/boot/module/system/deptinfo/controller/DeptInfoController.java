package com.bluewind.boot.module.system.deptinfo.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.deptinfo.entity.DeptInfo;
import com.bluewind.boot.module.system.deptinfo.service.DeptInfoService;

import com.bluewind.boot.module.system.permissioninfo.entity.PermissionInfo;
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
 * @date 2022-03-17 16:47
 * @description 部门管理
 **/
@Controller
@RequestMapping("/deptinfo")
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
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
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
     * 部门新增页面
     *
     * @return
     */
    @RequestMapping(value = "/forAdd", method = RequestMethod.GET)
    public String forAdd(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> dictList = DictUtils.getDictList("sysdeptinfo_status");
        model.addAttribute("dictList", dictList);
        return "system/deptinfo/add";
    }


    /**
     * 部门修改页面
     *
     * @return
     */
    @RequestMapping(value = "/forUpdate/{deptId}", method = RequestMethod.GET)
    public String forAdd(Model model, @PathVariable String deptId) {
        DeptInfo deptInfo =  deptInfoService.getOne(deptId);
        // 获取下拉栏枚举值
        List<Map<String, String>> dictList = DictUtils.getDictList("sysdeptinfo_status");
        model.addAttribute("dictList", dictList);
        model.addAttribute("deptInfo", deptInfo);
        return "system/deptinfo/update";
    }

    /**
     * 部门新增
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam("parentId") String parentId,
                          @RequestParam("ancestors") String ancestors,
                          @RequestParam("deptName") String deptName,
                          @RequestParam("leader") String leader,
                          @RequestParam("orderNum") Integer orderNum,
                          @RequestParam("status") String status,
                          @RequestParam(required = false, defaultValue = "", value = "phone") String phone,
                          @RequestParam(required = false, defaultValue = "", value = "email") String email) {
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setDeptId(IdGenerate.nextId());
        deptInfo.setParentId(parentId);
        deptInfo.setAncestors(ancestors);
        deptInfo.setDeptName(deptName);
        deptInfo.setLeader(leader);
        deptInfo.setOrderNum(orderNum);
        deptInfo.setPhone(phone);
        deptInfo.setEmail(email);
        deptInfo.setStatus(status);
        deptInfo.setDelFlag("0");
        deptInfo.setCreateUser(getSysUserId());

        int num = deptInfoService.add(deptInfo);
        if (num > 0) {
            return BaseResult.success("部门新增成功！");
        } else {
            return BaseResult.failure("部门新增失败！");
        }
    }


    /**
     * 部门更新
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("deptId") String deptId,
                             @RequestParam("parentId") String parentId,
                             @RequestParam("ancestors") String ancestors,
                             @RequestParam("deptName") String deptName,
                             @RequestParam("leader") String leader,
                             @RequestParam("status") String status,
                             @RequestParam("orderNum") Integer orderNum,
                             @RequestParam(required = false, defaultValue = "", value = "phone") String phone,
                             @RequestParam(required = false, defaultValue = "", value = "email") String email) {
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setDeptId(deptId);
        deptInfo.setParentId(parentId);
        deptInfo.setAncestors(ancestors);
        deptInfo.setDeptName(deptName);
        deptInfo.setLeader(leader);
        deptInfo.setOrderNum(orderNum);
        deptInfo.setPhone(phone);
        deptInfo.setEmail(email);
        deptInfo.setStatus(status);
        deptInfo.setUpdateUser(getSysUserId());

        int num = deptInfoService.update(deptInfo);
        if (num > 0) {
            return BaseResult.success("部门修改成功！");
        } else {
            return BaseResult.failure("部门修改失败！");
        }
    }


    /**
     * 部门删除
     *
     * @return
     */
    @RequestMapping(value = "/delete/{deptId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String deptId) {
        int num = deptInfoService.del(deptId);
        if (num > 0) {
            return BaseResult.success("删除成功");
        } else {
            return BaseResult.failure("删除失败！");
        }
    }


    /**
     * 更改部门顺序
     * @return
     */
    @RequestMapping(value="/updateSort/{deptId}/{orderNum}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult updateSort(@PathVariable String deptId,
                                 @PathVariable Integer orderNum){
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setDeptId(deptId);
        deptInfo.setOrderNum(orderNum);

        int num = deptInfoService.updateSort(deptInfo);
        if (num > 0) {
            return BaseResult.success("修改顺序成功!");
        } else {
            return BaseResult.failure("修改顺序失败!");
        }
    }

}
