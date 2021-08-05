package com.liuxingyu.meco.sys.sysitfckey.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuxingyu.meco.common.annotation.LogAround;
import com.liuxingyu.meco.common.base.BaseController;
import com.liuxingyu.meco.common.utils.BaseDictUtils;
import com.liuxingyu.meco.common.utils.DateTool;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.idgen.IdGenerate;
import com.liuxingyu.meco.common.utils.lang.StringUtils;
import com.liuxingyu.meco.common.utils.mybatis.MybatisSqlTool;
import com.liuxingyu.meco.sys.sysitfckey.entity.SysItfcKey;
import com.liuxingyu.meco.sys.sysitfckey.service.SysItfcKeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author liuxingyu01
 * @date 2021-06-12-17:00
 * @description itfc-key管理
 **/
@Controller
@RequestMapping("/sysitfckey")
@Api(value = "服务密钥管理控制器", tags = "服务密钥管理控制器")
public class SysItfcKeyController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysItfcKeyController.class);

    @Autowired
    private SysItfcKeyService sysItfcKeyService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询页面初始化
     *
     * @return
     */
    @ApiOperation(value = "分页查询页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> dictList = BaseDictUtils.getDictList("itfc_key_status");
        model.addAttribute("dictList", dictList);

        return "system/sysitfckey/sysitfckey_list";
    }


    /**
     * itfc-key页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询")
    @LogAround("itfc-key页面分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "itfcKey") String itfcKey,
                           @RequestParam(required = false, defaultValue = "", value = "owner") String owner,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysItfcKeyController -- list -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("itfcKey", itfcKey);
        paraMap.put("owner", owner);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<SysItfcKey> keyList = sysItfcKeyService.getSysItfcKeyList(paraMap);
        keyList.forEach(item -> {
            String validPeriod = DateTool.dateFormat(item.getValidPeriod(),"yyyyMMdd","yyyy-MM-dd");
            item.setValidPeriod(validPeriod);
        });

        PageInfo<SysItfcKey> pageinfo = new PageInfo<>(keyList);
        // 取出查询结果
        List<SysItfcKey> rows = pageinfo.getList();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, pageinfo.getTotal());

        return BaseResult.success(result);
    }


    /**
     * 新增页面初始化
     *
     * @return
     */
    @ApiOperation(value = "新增页面初始化")
    @RequestMapping(value = "/forAdd", method = RequestMethod.GET)
    public String forAdd(Model model) {
        return "system/sysitfckey/sysitfckey_add";
    }


    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam("owner") String owner,
                          @RequestParam("validPeriod") String validPeriod,
                          @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        SysItfcKey sysItfcKey = new SysItfcKey();
        String itfcKey = IdGenerate.uuid();
        sysItfcKey.setItfcKey(itfcKey);
        sysItfcKey.setOwner(owner);
        validPeriod = DateTool.dateFormat(validPeriod,"yyyy-MM-dd", "yyyyMMdd");
        sysItfcKey.setValidPeriod(validPeriod);
        sysItfcKey.setDescript(descript);
        sysItfcKey.setCreateUser(getSysUserId());
        sysItfcKey.setDelFlag(0);
        sysItfcKey.setStatus(0);
        int num = sysItfcKeyService.addOneSysItfcKey(sysItfcKey);
        if (num > 0) {
            redisUtil.set("itfcKey:" + itfcKey, new HashSet<>(), -1);
            redisUtil.set("itfcPeriod:" + itfcKey, validPeriod, -1);
            return BaseResult.success("新增服务密钥成功！");
        } else {
            return BaseResult.failure("新增服务密钥，请联系后台管理员！");
        }
    }



    /**
     * 修改页
     *
     * @return
     */
    @ApiOperation(value = "修改页面初始化")
    @GetMapping("/forUpdate/{id}")
    public String forUpdate(Model model, @PathVariable Integer id) {
        SysItfcKey sysItfcKey = sysItfcKeyService.getOneSysItfcKey(id);
        String validPeriod = DateTool.dateFormat(sysItfcKey.getValidPeriod(),"yyyyMMdd", "yyyy-MM-dd");
        sysItfcKey.setValidPeriod(validPeriod);
        model.addAttribute("sysItfcKey", sysItfcKey);
        return "system/sysitfckey/sysitfckey_update";
    }


    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("id") Integer id,
                             @RequestParam("itfcKey") String itfcKey,
                             @RequestParam("owner") String owner,
                             @RequestParam("validPeriod") String validPeriod,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        SysItfcKey sysItfcKey = new SysItfcKey();
        sysItfcKey.setId(id);
        sysItfcKey.setOwner(owner);
        validPeriod = DateTool.dateFormat(validPeriod,"yyyy-MM-dd", "yyyyMMdd");
        sysItfcKey.setValidPeriod(validPeriod);
        sysItfcKey.setDescript(descript);
        sysItfcKey.setUpdateUser(getSysUserId());

        int num = sysItfcKeyService.updateSysItfcKey(sysItfcKey);
        if (num > 0) {
            // 有效期可能会变化，所以这里修改redis里的密钥有效期
            redisUtil.set("itfcPeriod:" + itfcKey, validPeriod, -1);
            return BaseResult.success("修改服务密钥成功！");
        } else {
            return BaseResult.failure("修改服务密钥，请联系后台管理员！");
        }
    }


    /**
     * 删除密钥
     *
     * @return
     */
    @ApiOperation(value = "删除", notes = "删除")
    @ResponseBody
    @GetMapping("/delete/{id}/{itfcKey}")
    public BaseResult delete(@PathVariable Integer id,
                             @PathVariable String itfcKey) {
        int num = sysItfcKeyService.deleteSysItfcKey(id);
        if (num > 0) {
            redisUtil.del("itfcKey:" + itfcKey);
            redisUtil.del("itfcPeriod:" + itfcKey);

            return BaseResult.success("修改服务密钥成功！");
        } else {
            return BaseResult.failure("修改服务密钥，请联系后台管理员！");
        }
    }


    /**
     * 停用密钥
     *
     * @return
     */
    @ApiOperation(value = "停用", notes = "停用")
    @ResponseBody
    @GetMapping("/forbid/{id}/{itfcKey}")
    public BaseResult forbid(@PathVariable Integer id,
                             @PathVariable String itfcKey) {
        int num = sysItfcKeyService.forbidSysItfcKey(id);
        if (num > 0) {
            redisUtil.del("itfcKey:" + itfcKey);
            redisUtil.del("itfcPeriod:" + itfcKey);

            return BaseResult.success("停用服务密钥成功！");
        } else {
            return BaseResult.failure("停用服务密钥，请联系后台管理员！");
        }
    }


    /**
     * 启用密钥
     *
     * @return
     */
    @ApiOperation(value = "启用", notes = "启用")
    @ResponseBody
    @GetMapping("/enable/{id}/{itfcKey}/{validPeriod}")
    public BaseResult enable(@PathVariable Integer id,
                             @PathVariable String itfcKey,
                             @PathVariable String validPeriod) {
        int num = sysItfcKeyService.enableSysItfcKey(id);
        if (num > 0) {
            String sb = "select srp.sign from sys_itfc_permission srp " +
                    "left join sys_itfc_key_permission srkp on srkp.itfc_permission = srp.permission_id " +
                    "where srkp.itfc_key = '" + itfcKey + "'";
            List<Map> permissionList = MybatisSqlTool.selectAnySql(sb);
            Set<String> set = new HashSet<>();

            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (Map map : permissionList) {
                    String sign = Optional.ofNullable(map.get("sign")).orElse("").toString();
                    if (StringUtils.isNotBlank(sign)) {
                        set.add(sign);
                    }
                }
                redisUtil.set("itfcKey:" + itfcKey, set, -1);
                redisUtil.set("itfcPeriod:" + itfcKey, validPeriod, -1);
            } else {
                redisUtil.set("itfcKey:" + itfcKey, set, -1);
                redisUtil.set("itfcPeriod:" + itfcKey, validPeriod, -1);
            }

            return BaseResult.success("启用服务密钥成功！");
        } else {
            return BaseResult.failure("启用服务密钥，请联系后台管理员！");
        }
    }


    /**
     * 服务密钥权限配置页
     *
     * @return
     */
    @ApiOperation(value = "权限配置页面初始化", notes = "权限配置页面初始化")
    @GetMapping("/forAuthorize/{id}")
    public String forAuthorize(Model model, @PathVariable Integer id) {
        SysItfcKey sysItfcKey = sysItfcKeyService.getOneSysItfcKey(id);
        model.addAttribute("sysItfcKey", sysItfcKey);
        return "system/sysitfckey/sysitfckey_auth";
    }


    /**
     * 根据密钥itfcKey查询接口权限树
     * @param itfcKey
     * @return
     */
    @ApiOperation(value = "获取权限树", notes = "获取权限树")
    @RequestMapping(value = "listPermissionForTree/{itfcKey}", method = RequestMethod.GET)
    @ResponseBody
    public String listPermissionForTree(@PathVariable String itfcKey) {
        return sysItfcKeyService.listPermissionForTree(itfcKey);
    }



    /**
     * 服务密钥权限更新
     *
     * @return
     */
    @ApiOperation(value = "权限更新", notes = "权限更新")
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult authorize(@RequestParam(value = "itfcKey") String itfcKey,
                                @RequestParam(value = "permIds") String permIds) {
        if (logger.isInfoEnabled()) {
            logger.info("SysItfcKeyController -- authorize -- permIds =  {}：" + permIds);
        }
        int num = sysItfcKeyService.authorize(itfcKey, permIds);
        if (num > 0) {

            String sb = "select srp.sign from sys_itfc_permission srp " +
                    "left join sys_itfc_key_permission srkp on srkp.itfc_permission = srp.permission_id " +
                    "where srkp.itfc_key = '" + itfcKey + "'";
            List<Map> permissionList = MybatisSqlTool.selectAnySql(sb);
            Set<String> set = new HashSet<>();

            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (Map map : permissionList) {
                    String sign = Optional.ofNullable(map.get("sign")).orElse("").toString();
                    if (StringUtils.isNotBlank(sign)) {
                        set.add(sign);
                    }
                }
                redisUtil.set("itfcKey:" + itfcKey, set, -1);
            } else {
                redisUtil.set("itfcKey:" + itfcKey, set, -1);
            }

            return BaseResult.success("服务权限更新成功!");
        } else {
            return BaseResult.failure("服务权限更新失败!");
        }
    }

}
