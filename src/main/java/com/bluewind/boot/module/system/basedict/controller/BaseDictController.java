package com.bluewind.boot.module.system.basedict.controller;

import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.basedict.entity.Dict;
import com.bluewind.boot.module.system.basedict.entity.DictDetail;
import com.bluewind.boot.module.system.basedict.service.BaseDictService;
import com.bluewind.boot.common.annotation.OperLogAround;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.consts.OperLogConst;
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
@RequestMapping("/dict")
@Api(value = "basedict控制器", description = "字典管理")
public class BaseDictController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(BaseDictController.class);

    @Autowired
    private BaseDictService baseDictService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @OperLogAround(operModul = "数据字典", operType = OperLogConst.LIST_PAGE, operDesc = "分页查询页面初始化")
    @RequestMapping(value = "/init")
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String, String>> baseDictList = DictUtils.getDictList("dict_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/basedict/list";
    }


    /**
     * 枚举管理页面分页查询
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @OperLogAround(operModul = "数据字典", operType = OperLogConst.LIST, operDesc = "分页查询")
    @ApiOperation(value = "查询全部字典信息")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("limit") Integer pageSize,
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
        List<Dict> dictList = baseDictService.getAllBaseDict(paraMap);
        PageInfo<Dict> page = new PageInfo<>(dictList);
        // 取出查询结果
        List<Dict> rows = page.getList();
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
        return "system/basedict/add";
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
        Dict dict = new Dict();
        dict.setDictId(IdGenerate.nextId());
        dict.setDictCode(dictCode);
        dict.setDescript(descript);
        dict.setName(name);
        dict.setCreateUser(getSysUserId());
        dict.setStatus("0");
        dict.setDelFlag("0");
        int num = baseDictService.addOneDict(dict);
        if (num > 0) {
            return BaseResult.success("字典新增成功！");
        } else {
            return BaseResult.failure("字典新增失败！");
        }
    }


    /**
     * 枚举删除
     */
    @RequestMapping(value = "/delete/{dictId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String dictId) {
        int num = baseDictService.deleteDict(dictId);
        if (num > 0) {
            return BaseResult.success("删除成功");
        } else {
            return BaseResult.failure("删除失败！");
        }
    }


    /**
     * 枚举禁用
     */
    @RequestMapping(value = "/forbid/{dictId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult forbid(@PathVariable String dictId) {
        int num = baseDictService.forbidDict(dictId);
        if (num > 0) {
            return BaseResult.success("禁用成功");
        } else {
            return BaseResult.failure("禁用失败！");
        }
    }


    /**
     * 枚举启用
     */
    @RequestMapping(value = "/enable/{dictId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult enable(@PathVariable String dictId) {
        int num = baseDictService.enableDict(dictId);

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
    @RequestMapping(value = "/forUpdate/{dictId}", method = RequestMethod.GET)
    public String forUpdate(@PathVariable String dictId, Model model) {
        Dict dict = baseDictService.findOneBaseDictById(dictId);
        model.addAttribute("dict", dict);
        return "system/basedict/update";
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
                             @RequestParam("dictId") String dictId) {
        Dict dict = new Dict();
        dict.setDescript(descript);
        dict.setName(name);
        dict.setDictId(dictId);
        dict.setCreateUser(getSysUserId());
        dict.setStatus("0");
        dict.setDelFlag("0");
        int num = baseDictService.updateOneDict(dict);
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
    public String forSetting(@PathVariable String dictCode, Model model) {
        model.addAttribute("dictCode", dictCode);
        return "system/basedict/detail_list";
    }


    /**
     * 字典明细列表查询
     *
     * @return
     */
    @ApiOperation(value = "查询字典明细信息")
    @ResponseBody
    @RequestMapping(value = "/detailList", method = RequestMethod.POST)
    public BaseResult detailList(@RequestParam(required = false, defaultValue = "", value = "dictCode") String dictCode) {

        Dict dict = new Dict();
        dict.setDictCode(dictCode);
        List<DictDetail> dictList = baseDictService.getBaseDictDetail(dict);

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
        return "system/basedict/detail_add";
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
        DictDetail sysDict = new DictDetail();
        sysDict.setDictDetailId(IdGenerate.nextId());
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
    @RequestMapping(value = "/deleteDetail/{dictDetailId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult deleteDetail(@PathVariable String dictDetailId) {
        int num = baseDictService.deleteDetail(dictDetailId);
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
    @RequestMapping(value = "/forUpdateDetail/{dictDetailId}", method = RequestMethod.GET)
    public String forUpdateDetail(@PathVariable String dictDetailId,
                                  Model model) {
        DictDetail dictDetail = baseDictService.findOneDictDetailById(dictDetailId);
        model.addAttribute("dictDetail", dictDetail);
        return "system/basedict/detail_update";
    }


    /**
     * 枚举明细新增
     *
     * @return
     */
    @RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateDetail(@RequestParam("code") String code,
                                   @RequestParam("dictDetailId") String dictDetailId,
                                   @RequestParam("dictCode") String dictCode,
                                   @RequestParam("name") String name) {
        DictDetail sysDict = new DictDetail();
        sysDict.setDictCode(dictCode);
        sysDict.setCode(code);
        sysDict.setName(name);
        sysDict.setDictDetailId(dictDetailId);
        sysDict.setUpdateUser(getSysUserId());
        int num = baseDictService.updateDetail(sysDict);
        if (num > 0) {
            return BaseResult.success("字典明细更新成功！");
        } else {
            return BaseResult.failure("字典明细更新失败！");
        }
    }

}
