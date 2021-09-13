package com.bluewind.boot.sys.sysbasedict.controller;

import com.bluewind.boot.common.utils.BaseDictUtils;
import com.bluewind.boot.sys.sysbasedict.service.SysBaseDictService;
import com.bluewind.boot.common.annotation.OperLog;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.consts.OperLogConst;
import com.bluewind.boot.sys.sysbasedict.entity.SysDict;
import com.bluewind.boot.sys.sysbasedict.entity.SysDictDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.bluewind.boot.common.base.BaseController;


/**
 * @author liuxingyu01
 * @date 2020-05-29-21:49
 * @description BaseDict枚举公共类
 **/
@Controller
@RequestMapping("/sysdict")
@Api(value = "basedict控制器", description = "字典管理")
public class SysBaseDictController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysBaseDictController.class);

    @Autowired
    private SysBaseDictService baseDictService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @OperLog(operModul = "数据字典", operType = OperLogConst.LIST_PAGE, operDesc = "分页查询页面初始化")
    @RequestMapping(value = "/baseDictQureyInit")
    public String baseDictQureyInit(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> baseDictList = BaseDictUtils.getDictList("dict_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/sysbasedict/sysdict_list";
    }


    /**
     * 枚举管理页面分页查询
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @OperLog(operModul = "数据字典", operType = OperLogConst.LIST, operDesc = "分页查询")
    @ApiOperation(value = "查询全部字典信息")
    @ResponseBody
    @RequestMapping(value = "/getSysDictList", method = RequestMethod.POST)
    public BaseResult getSysDictList(@RequestParam("limit") Integer pageSize,
                                     @RequestParam("page") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "", value = "dictCode") String dictCode,
                                     @RequestParam(required = false, defaultValue = "", value = "name") String name,
                                     @RequestParam(required = false, defaultValue = "", value = "status") String status,
                                     @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                                     @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("dictCode", dictCode);
        paraMap.put("name", name);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        List<SysDict> dictList = baseDictService.getAllBaseDict(paraMap);
        PageInfo<SysDict> page = new PageInfo<>(dictList);
        // 取出查询结果
        List<SysDict> rows = page.getList();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, page.getTotal());
        return BaseResult.success(result);
    }


    /**
     * 枚举新增页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "system/sysbasedict/sysdict_add";
    }


    /**
     * 枚举新增
     *
     * @return
     */
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doAdd(@RequestParam(required = false, defaultValue = "", value = "descript") String descript,
                            @RequestParam("dictCode") String dictCode,
                            @RequestParam("name") String name) {
        SysDict sysDict = new SysDict();
        sysDict.setDictCode(dictCode);
        sysDict.setDescript(descript);
        sysDict.setName(name);
        sysDict.setCreateUser(getSysUserId());
        sysDict.setStatus(0);
        sysDict.setDelFlag(0);
        int num = baseDictService.addOneDict(sysDict);
        if (num > 0) {
            return BaseResult.success("字典新增成功！");
        } else {
            return BaseResult.failure("字典新增失败！");
        }
    }


    /**
     * 枚举删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable Integer id) {
        int num = baseDictService.deleteDict(id);

        if (num > 0) {
            return BaseResult.success("删除成功");
        } else {
            return BaseResult.failure("删除失败！");
        }
    }


    /**
     * 枚举禁用
     */
    @RequestMapping(value = "/forbid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult forbid(@PathVariable Integer id) {
        int num = baseDictService.forbidDict(id);

        if (num > 0) {
            return BaseResult.success("禁用成功");
        } else {
            return BaseResult.failure("禁用失败！");
        }
    }


    /**
     * 枚举启用
     */
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult enable(@PathVariable Integer id) {
        int num = baseDictService.enableDict(id);

        if (num > 0) {
            return BaseResult.success("启用成功");
        } else {
            return BaseResult.failure("启用失败！");
        }
    }


    /**
     * 枚举修改页面
     *
     * @return
     */
    @RequestMapping(value = "/forUpdate/{id}", method = RequestMethod.GET)
    public String forUpdate(@PathVariable Integer id,
                            Model model) {
        SysDict sysDict = baseDictService.findOneBaseDictById(id);
        model.addAttribute("sysDict", sysDict);
        return "system/sysbasedict/sysdict_update";
    }


    /**
     * 枚举修改
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam(required = false, defaultValue = "", value = "descript") String descript,
                             @RequestParam("name") String name,
                             @RequestParam("id") Integer id) {
        SysDict sysDict = new SysDict();
        sysDict.setDescript(descript);
        sysDict.setName(name);
        sysDict.setId(id);
        sysDict.setCreateUser(getSysUserId());
        sysDict.setStatus(0);
        sysDict.setDelFlag(0);
        int num = baseDictService.updateOneDict(sysDict);
        if (num > 0) {
            return BaseResult.success("字典编辑保存成功！");
        } else {
            return BaseResult.failure("字典编辑保存失败！");
        }
    }


    /**
     * 枚举配置页面
     *
     * @return
     */
    @RequestMapping(value = "/forSetting/{dictCode}", method = RequestMethod.GET)
    public String forSetting(@PathVariable String dictCode,
                             Model model) {
        model.addAttribute("dictCode", dictCode);
        return "system/sysbasedict/sysdict_detail_list";
    }


    /**
     * 字典明细列表查询
     *
     * @return
     */
    @ApiOperation(value = "查询字典明细信息")
    @ResponseBody
    @RequestMapping(value = "/getSysDictDetailList", method = RequestMethod.POST)
    public BaseResult getSysDictDetailList(@RequestParam(required = false, defaultValue = "", value = "dictCode") String dictCode) {

        SysDict sysDict = new SysDict();
        sysDict.setDictCode(dictCode);
        List<SysDictDetail> dictList = baseDictService.getBaseDictDetail(sysDict);

        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, dictList);
        result.put(RESULT_TOTLAL, dictList == null ? 0 : dictList.size());
        return BaseResult.success(result);
    }


    /**
     * 枚举明细新增页面
     *
     * @return
     */
    @RequestMapping(value = "/forAddDetail/{dictCode}", method = RequestMethod.GET)
    public String forAddDetail(@PathVariable String dictCode,
                               Model model) {
        model.addAttribute("dictCode", dictCode);
        return "system/sysbasedict/sysdict_detail_add";
    }

    /**
     * 枚举明细新增
     *
     * @return
     */
    @RequestMapping(value = "/addDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addDetail(@RequestParam("code") String code,
                                @RequestParam("dictCode") String dictCode,
                                @RequestParam("name") String name) {
        SysDictDetail sysDict = new SysDictDetail();
        sysDict.setDictCode(dictCode);
        sysDict.setCode(code);
        sysDict.setName(name);
        sysDict.setCreateUser(getSysUserId());
        int num = baseDictService.addDetail(sysDict);
        if (num > 0) {
            return BaseResult.success("字典明细新增成功！");
        } else {
            return BaseResult.failure("字典明细新增失败！");
        }
    }


    /**
     * 枚举删除
     */
    @RequestMapping(value = "/deleteDetail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult deleteDetail(@PathVariable Integer id) {
        int num = baseDictService.deleteDetail(id);
        if (num > 0) {
            return BaseResult.success("删除成功");
        } else {
            return BaseResult.failure("删除失败！");
        }
    }


    /**
     * 枚举明细修改页面
     *
     * @return
     */
    @RequestMapping(value = "/forUpdateDetail/{id}", method = RequestMethod.GET)
    public String forUpdateDetail(@PathVariable Integer id,
                                  Model model) {
        SysDictDetail sysDictDetail = baseDictService.findOneDictDetailById(id);
        model.addAttribute("sysDictDetail", sysDictDetail);
        return "system/sysbasedict/sysdict_detail_update";
    }


    /**
     * 枚举明细新增
     *
     * @return
     */
    @RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateDetail(@RequestParam("code") String code,
                                   @RequestParam("id") Integer id,
                                   @RequestParam("dictCode") String dictCode,
                                   @RequestParam("name") String name) {
        SysDictDetail sysDict = new SysDictDetail();
        sysDict.setDictCode(dictCode);
        sysDict.setCode(code);
        sysDict.setName(name);
        sysDict.setId(id);
        sysDict.setUpdateUser(getSysUserId());
        int num = baseDictService.updateDetail(sysDict);
        if (num > 0) {
            return BaseResult.success("字典明细更新成功！");
        } else {
            return BaseResult.failure("字典明细更新失败！");
        }
    }

}
