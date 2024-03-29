package com.bluewind.boot.module.system.itfckey.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.DateTool;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.module.system.itfckey.entity.ItfcKey;
import com.bluewind.boot.module.system.itfckey.service.ItfcKeyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @date 2021-06-12-17:00
 * @description itfc-key管理
 **/
@Controller
@RequestMapping("/itfckey")
@Api(value = "服务密钥管理控制器", tags = "服务密钥管理控制器")
public class ItfcKeyController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(ItfcKeyController.class);

    @Autowired
    private ItfcKeyService itfcKeyService;

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
        List<Map<String, String>> dictList = DictUtils.getDictList("itfc_key_status");
        model.addAttribute("dictList", dictList);
        return "system/itfckey/list";
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

        List<ItfcKey> keyList = itfcKeyService.getSysItfcKeyList(paraMap);
        keyList.forEach(item -> {
            String validPeriod = DateTool.dateFormat(item.getValidPeriod(),"yyyyMMdd","yyyy-MM-dd");
            item.setValidPeriod(validPeriod);
        });

        PageInfo<ItfcKey> pageinfo = new PageInfo<>(keyList);
        // 取出查询结果
        List<ItfcKey> rows = pageinfo.getList();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTAL, pageinfo.getTotal());

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
        return "system/itfckey/add";
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
        ItfcKey sysItfcKey = new ItfcKey();
        String itfcKey = IdGenerate.uuid();
        String itfcId = IdGenerate.nextId();
        String itfcKeySecret = StringUtils.getSecretStr(36);
        sysItfcKey.setItfcKey(itfcKey);
        sysItfcKey.setItfcId(itfcId);
        sysItfcKey.setItfcKeySecret(itfcKeySecret);
        sysItfcKey.setOwner(owner);
        validPeriod = DateTool.dateFormat(validPeriod,"yyyy-MM-dd", "yyyyMMdd");
        sysItfcKey.setValidPeriod(validPeriod);
        sysItfcKey.setDescript(descript);
        sysItfcKey.setCreateUser(getSysUserId());
        sysItfcKey.setDelFlag("0");
        sysItfcKey.setStatus("0");
        int num = itfcKeyService.addOneSysItfcKey(sysItfcKey);
        if (num > 0) {
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
    @GetMapping("/forUpdate/{itfcId}")
    public String forUpdate(Model model, @PathVariable String itfcId) {
        ItfcKey itfcKey = itfcKeyService.getOneSysItfcKey(itfcId);
        String validPeriod = DateTool.dateFormat(itfcKey.getValidPeriod(),"yyyyMMdd", "yyyy-MM-dd");
        itfcKey.setValidPeriod(validPeriod);
        model.addAttribute("itfcKey", itfcKey);
        return "system/itfckey/update";
    }


    @ApiOperation(value = "生成itfcKeySecret")
    @ResponseBody
    @GetMapping("/generateSecret")
    public BaseResult generateSecret() {
        String itfcKeySecret = StringUtils.getSecretStr(36);
        return BaseResult.success("生成KeySecret成功！", itfcKeySecret);
    }


    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("itfcId") String itfcId,
                             @RequestParam("itfcKey") String itfcKey,
                             @RequestParam("itfcKeySecret") String itfcKeySecret,
                             @RequestParam("owner") String owner,
                             @RequestParam("validPeriod") String validPeriod,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        ItfcKey sysItfcKey = new ItfcKey();
        sysItfcKey.setItfcId(itfcId);
        sysItfcKey.setOwner(owner);
        sysItfcKey.setItfcKeySecret(itfcKeySecret);
        validPeriod = DateTool.dateFormat(validPeriod,"yyyy-MM-dd", "yyyyMMdd");
        sysItfcKey.setValidPeriod(validPeriod);
        sysItfcKey.setDescript(descript);
        sysItfcKey.setUpdateUser(getSysUserId());

        int num = itfcKeyService.updateSysItfcKey(sysItfcKey);
        if (num > 0) {
            // 有效期可能会变化，所以这里删除掉redis里的缓存
            redisUtil.del(SystemConst.SYSTEM_ITFC_KEY + ":" + itfcKey);
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
    @GetMapping("/delete/{itfcId}/{itfcKey}")
    public BaseResult delete(@PathVariable String itfcId,
                             @PathVariable String itfcKey) {
        int num = itfcKeyService.deleteSysItfcKey(itfcId);
        if (num > 0) {
            redisUtil.del(SystemConst.SYSTEM_ITFC_KEY + ":" + itfcKey);

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
    @GetMapping("/forbid/{itfcId}/{itfcKey}")
    public BaseResult forbid(@PathVariable String itfcId,
                             @PathVariable String itfcKey) {
        int num = itfcKeyService.forbidSysItfcKey(itfcId);
        if (num > 0) {
            redisUtil.del(SystemConst.SYSTEM_ITFC_KEY + ":" + itfcKey);

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
    @GetMapping("/enable/{itfcId}/{itfcKey}/{validPeriod}")
    public BaseResult enable(@PathVariable String itfcId,
                             @PathVariable String itfcKey,
                             @PathVariable String validPeriod) {
        int num = itfcKeyService.enableSysItfcKey(itfcId);
        if (num > 0) {

            redisUtil.del(SystemConst.SYSTEM_ITFC_KEY + ":" + itfcKey);
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
    @GetMapping("/forAuthorize/{itfcId}")
    public String forAuthorize(Model model, @PathVariable String itfcId) {
        ItfcKey itfcKey = itfcKeyService.getOneSysItfcKey(itfcId);
        model.addAttribute("itfcKey", itfcKey);
        return "system/itfckey/auth";
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
        return itfcKeyService.listPermissionForTree(itfcKey);
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
        int num = itfcKeyService.authorize(itfcKey, permIds);
        if (num > 0) {

            redisUtil.del(SystemConst.SYSTEM_ITFC_KEY + ":" + itfcKey);
            return BaseResult.success("服务权限更新成功!");
        } else {
            return BaseResult.failure("服务权限更新失败!");
        }
    }

}
