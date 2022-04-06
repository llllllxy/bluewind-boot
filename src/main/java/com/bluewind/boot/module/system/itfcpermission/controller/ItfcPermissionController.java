package com.bluewind.boot.module.system.itfcpermission.controller;

import com.bluewind.boot.module.system.itfcpermission.entity.ItfcPermission;
import com.bluewind.boot.module.system.itfcpermission.service.ItfcPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:00
 * @description itfc-permission管理
 **/
@Controller
@RequestMapping("/itfcpermission")
@Api(value = "itfc服务权限管理控制器", tags = "itfc服务权限管理控制器")
public class ItfcPermissionController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(ItfcPermissionController.class);

    @Autowired
    private ItfcPermissionService itfcPermissionService;


    /**
     * 页面初始化
     *
     * @return
     */
    @ApiOperation(value = "页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/itfcpermission/list";
    }


    /**
     * 获取菜单树
     * @return
     */
    @ApiOperation(value = "获取itfc权限树")
    @RequestMapping(value = "/listTree",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listTree() {
        List<Map> resultList = itfcPermissionService.listTree();
        if (resultList != null && !resultList.isEmpty()) {
            return BaseResult.success("获取菜单列表成功!", resultList);
        } else {
            return BaseResult.failure("未获取任何菜单列表!");
        }
    }



    /**
     * 获取菜单列表
     * @return
     */
    @ApiOperation(value = "获取itfc子权限列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "permissionId") String permissionId) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("list -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }
        List<ItfcPermission> list = itfcPermissionService.list(permissionId);

        PageInfo<ItfcPermission> pageinfo = new PageInfo<>(list);
        // 取出查询结果
        List<ItfcPermission> rows = pageinfo.getList();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, pageinfo.getTotal());

        return BaseResult.success(result);
    }


    /**
     * 更新权限
     * @return
     */
    @ApiOperation(value = "更新itfc权限")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam(required = false, defaultValue = "", value = "permission_id") String permission_id,
                             @RequestParam(required = false, defaultValue = "", value = "superior") String superior,
                             @RequestParam("type") String type,
                             @RequestParam("name") String name,
                             @RequestParam("ifEdit") Boolean ifEdit,
                             @RequestParam(required = false, defaultValue = "", value = "sign") String sign,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        if ("0".equals(type)) {
            superior = "0";
        }
        // 如果是新增的话，则生成一个permission_id
        if (!ifEdit) {
            permission_id = IdGenerate.nextId();
        }

        ItfcPermission itfcPermission = new ItfcPermission();
        itfcPermission.setPermissionId(permission_id);
        itfcPermission.setParentId(superior);
        itfcPermission.setType(type);
        itfcPermission.setName(name);
        itfcPermission.setSign(sign);
        itfcPermission.setDescript(descript);
        itfcPermission.setCreateUser(getSysUserId());
        itfcPermission.setUpdateUser(getSysUserId());

        int num = itfcPermissionService.update(itfcPermission, ifEdit);
        if (num > 0) {
            return BaseResult.success("更新成功!");
        } else {
            return BaseResult.failure("更新失败!");
        }
    }


    /**
     * 删除一个接口权限
     * @return
     */
    @ApiOperation(value = "删除itfc权限")
    @RequestMapping(value="/delete/{permissionId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String permissionId){
        int num = itfcPermissionService.delete(permissionId);
        if (num > 0) {
            return BaseResult.success("删除成功!");
        } else {
            return BaseResult.failure("删除失败!");
        }
    }

}
