package com.liuxingyu.meco.sys.sysitfcpermission.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxingyu.meco.common.base.BaseController;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.common.utils.idgen.IdGenerate;
import com.liuxingyu.meco.sys.sysitfcpermission.entity.SysItfcPermission;
import com.liuxingyu.meco.sys.sysitfcpermission.service.SysItfcPermissionService;
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
@RequestMapping("/sysitfcpermission")
@Api(value = "itfc服务权限管理控制器", tags = "itfc服务权限管理控制器")
public class SysItfcPermissionController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysItfcPermissionController.class);

    @Autowired
    private SysItfcPermissionService sysItfcPermissionService;


    /**
     * 页面初始化
     *
     * @return
     */
    @ApiOperation(value = "页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/sysitfcpermission/sysitfcpermission_list";
    }


    /**
     * 获取菜单树
     * @return
     */
    @ApiOperation(value = "获取itfc权限树")
    @RequestMapping(value = "/listTree",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listTree() {
        List<Map> resultList = sysItfcPermissionService.listTree();
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
        List<SysItfcPermission> list = sysItfcPermissionService.list(permissionId);

        PageInfo<SysItfcPermission> pageinfo = new PageInfo<>(list);
        // 取出查询结果
        List<SysItfcPermission> rows = pageinfo.getList();
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
                             @RequestParam("type") Integer type,
                             @RequestParam("name") String name,
                             @RequestParam("ifEdit") Boolean ifEdit,
                             @RequestParam(required = false, defaultValue = "", value = "sign") String sign,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        if (type == 0) {
            superior = "0";
        }
        // 如果是新增的话，则生成一个permission_id
        if (!ifEdit) {
            permission_id = IdGenerate.nextId();
        }

        SysItfcPermission sysItfcPermission = new SysItfcPermission();
        sysItfcPermission.setPermissionId(permission_id);
        sysItfcPermission.setParentId(superior);
        sysItfcPermission.setType(type);
        sysItfcPermission.setName(name);
        sysItfcPermission.setSign(sign);
        sysItfcPermission.setDescript(descript);
        sysItfcPermission.setCreateUser(getSysUserId());
        sysItfcPermission.setUpdateUser(getSysUserId());

        int num = sysItfcPermissionService.update(sysItfcPermission, ifEdit);
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
        int num = sysItfcPermissionService.delete(permissionId);
        if (num > 0) {
            return BaseResult.success("删除成功!");
        } else {
            return BaseResult.failure("删除失败!");
        }
    }

}
