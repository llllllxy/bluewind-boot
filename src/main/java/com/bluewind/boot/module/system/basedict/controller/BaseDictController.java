package com.bluewind.boot.module.system.basedict.controller;

import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.basedict.entity.DictInfo;
import com.bluewind.boot.module.system.basedict.service.BaseDictService;
import com.bluewind.boot.common.annotation.OperLogAround;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.consts.OperLogConst;
import com.bluewind.boot.common.base.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.*;


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
                           @RequestParam(required = false, defaultValue = "", value = "dictName") String dictName,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("dictCode", dictCode);
        paraMap.put("dictName", dictName);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);
        List<DictInfo> dictList = baseDictService.getAllBaseDict(paraMap);
        PageInfo<DictInfo> page = new PageInfo<>(dictList);
        // 取出查询结果
        List<DictInfo> rows = page.getList();
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
                            @RequestParam("dictName") String dictName,
                            @RequestParam("detailData") String detailData) {
        Integer exist = baseDictService.checkExistByDictCode(dictCode);
        if (exist != null) {
            return BaseResult.failure("新增字典失败，此字典编码已存在!");
        }
        List<Map> detailDataList = JsonTool.parseArray(detailData, Map.class);

        List<DictInfo> insertDataList = new ArrayList<>();

        if (CollectionUtils.isEmpty(detailDataList)) {
            DictInfo dict = new DictInfo();
            dict.setDictId(IdGenerate.nextId());
            dict.setDictCode(dictCode);
            dict.setDictName(dictName);
            dict.setDescript(descript);
            dict.setCreateUser(getSysUserId());
            dict.setStatus("0");
            dict.setDelFlag("0");

            insertDataList.add(dict);
        } else {
            for (Map map : detailDataList) {
                DictInfo dict = new DictInfo();
                dict.setDictId(IdGenerate.nextId());
                dict.setDictCode(dictCode);
                dict.setDictName(dictName);
                dict.setDescript(descript);
                dict.setCreateUser(getSysUserId());
                dict.setStatus("0");
                dict.setDelFlag("0");
                String dictKey = Optional.ofNullable(map.get("dictKey")).orElse("").toString();
                String dictValue = Optional.ofNullable(map.get("dictValue")).orElse("").toString();
                Integer orderNum = Integer.parseInt(Optional.ofNullable(map.get("orderNum")).orElse("0").toString());

                dict.setDictKey(dictKey);
                dict.setDictValue(dictValue);
                dict.setOrderNum(orderNum);
                insertDataList.add(dict);
            }
        }

        int num = baseDictService.addDict(insertDataList);
        if (num > 0) {
            return BaseResult.success("字典新增成功！");
        } else {
            return BaseResult.failure("字典新增失败！");
        }
    }


    /**
     * 枚举删除
     *
     * @return
     */
    @RequestMapping(value = "/delete/{dictCode}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String dictCode) {
        int num = baseDictService.deleteDict(dictCode);
        if (num > 0) {
            return BaseResult.success("删除成功");
        } else {
            return BaseResult.failure("删除失败！");
        }
    }


    /**
     * 枚举禁用
     *
     * @return
     */
    @RequestMapping(value = "/forbid/{dictCode}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult forbid(@PathVariable String dictCode) {
        int num = baseDictService.forbidDict(dictCode);
        if (num > 0) {
            return BaseResult.success("禁用成功");
        } else {
            return BaseResult.failure("禁用失败！");
        }
    }


    /**
     * 枚举启用
     *
     * @return
     */
    @RequestMapping(value = "/enable/{dictCode}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult enable(@PathVariable String dictCode) {
        int num = baseDictService.enableDict(dictCode);

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
    @RequestMapping(value = "/forUpdate/{dictCode}", method = RequestMethod.GET)
    public String forUpdate(@PathVariable String dictCode, Model model) {
        List<DictInfo> dictList = baseDictService.getDictByDictCode(dictCode);
        model.addAttribute("dict", dictList.get(0));
        model.addAttribute("dictList", JsonTool.toJsonString(dictList));

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
                             @RequestParam("dictCode") String dictCode,
                             @RequestParam("dictName") String dictName,
                             @RequestParam("detailData") String detailData) {
        List<DictInfo> insertDataList = new ArrayList<>();
        List<Map> detailDataList = JsonTool.parseArray(detailData, Map.class);

        if (CollectionUtils.isEmpty(detailDataList)) {
            DictInfo dict = new DictInfo();
            dict.setDictId(IdGenerate.nextId());
            dict.setDictCode(dictCode);
            dict.setDictName(dictName);
            dict.setDescript(descript);
            dict.setCreateUser(getSysUserId());
            dict.setStatus("0");
            dict.setDelFlag("0");

            insertDataList.add(dict);
        } else {
            for (Map map : detailDataList) {
                DictInfo dict = new DictInfo();
                dict.setDictId(IdGenerate.nextId());
                dict.setDictCode(dictCode);
                dict.setDictName(dictName);
                dict.setDescript(descript);
                dict.setCreateUser(getSysUserId());
                dict.setStatus("0");
                dict.setDelFlag("0");
                String dictKey = Optional.ofNullable(map.get("dictKey")).orElse("").toString();
                String dictValue = Optional.ofNullable(map.get("dictValue")).orElse("").toString();
                Integer orderNum = Integer.parseInt(Optional.ofNullable(map.get("orderNum")).orElse("0").toString());

                dict.setDictKey(dictKey);
                dict.setDictValue(dictValue);
                dict.setOrderNum(orderNum);
                insertDataList.add(dict);
            }
        }
        int num = baseDictService.updateDict(dictCode, insertDataList);

        if (num > 0) {
            return BaseResult.success("字典编辑保存成功！");
        } else {
            return BaseResult.failure("字典编辑保存失败！");
        }
    }

}
