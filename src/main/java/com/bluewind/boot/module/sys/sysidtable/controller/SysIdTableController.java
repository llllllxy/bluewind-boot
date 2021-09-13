package com.bluewind.boot.module.sys.sysidtable.controller;

import com.bluewind.boot.module.sys.sysidtable.service.SysIdTableService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.module.sys.sysidtable.entity.SysIdTable;
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
 * @date 2021-04-05-21:47
 * @description 业务流水号Controller
 **/
@Controller
@RequestMapping("/sysidtable")
public class SysIdTableController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysIdTableController.class);

    @Autowired
    private SysIdTableService sysIdTableService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/sysidtable/sysidtable_list";
    }


    /**
     * 业务流水号页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @LogAround("业务流水号分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "idId") String idId,
                           @RequestParam(required = false, defaultValue = "", value = "idName") String idName,
                           @RequestParam(required = false, defaultValue = "", value = "createTime") String createTime,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("getSysUserInfoList -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("idId", idId);
        paraMap.put("idName", idName);
        paraMap.put("createTime", createTime);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<SysIdTable> sysIdTableList = sysIdTableService.getSysIdTableList(paraMap);

        PageInfo<SysIdTable> pageinfo = new PageInfo<>(sysIdTableList);
        // 取出查询结果
        List<SysIdTable> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, total);

        return BaseResult.success(result);
    }


    /**
     * 新增页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/forAdd", method = RequestMethod.GET)
    public String forAdd() {
        return "system/sysidtable/sysidtable_add";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam(value = "idId") String idId,
                          @RequestParam(value = "idName") String idName,
                          @RequestParam(value = "idLength") Integer idLength,
                          @RequestParam(value = "idValue") Integer idValue,
                          @RequestParam(required = false, defaultValue = "", value = "idPrefix") String idPrefix,
                          @RequestParam(required = false, defaultValue = "", value = "idSuffix") String idSuffix,
                          @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        SysIdTable sysIdTable = new SysIdTable();
        sysIdTable.setIdId(idId);
        sysIdTable.setIdName(idName);
        sysIdTable.setIdLength(idLength);
        sysIdTable.setIdValue(idValue);
        sysIdTable.setCreateUser(getSysUserId());
        sysIdTable.setDescript(descript);
        sysIdTable.setIdPrefix(idPrefix);
        sysIdTable.setIdSuffix(idSuffix);
        if (StringUtils.isBlank(idPrefix)) {
            sysIdTable.setHasPrefix("1"); // 没有前缀
        } else {
            sysIdTable.setHasPrefix("0");
        }
        if (StringUtils.isBlank(idSuffix)) {
            sysIdTable.setHasSuffix("1"); // 没有后缀
        } else {
            sysIdTable.setHasSuffix("0");
        }

        int num = sysIdTableService.addOneIdTable(sysIdTable);
        if (num > 0) {
            return BaseResult.success("新增业务流水号成功!");
        } else {
            return BaseResult.failure("新增业务流水号失败!");
        }
    }


    /**
     * 删除一个系统用户（这里后面可能会改为逻辑删除）
     *
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable int id) {
        int num = sysIdTableService.deleteOne(id);
        if (num > 0) {
            return BaseResult.success("删除成功!");
        } else {
            return BaseResult.failure("删除失败!");
        }
    }


    /**
     * 新增页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/forUpdate/{id}", method = RequestMethod.GET)
    public String forUpdate(@PathVariable int id,
                            Model model) {
        SysIdTable sysIdTable = sysIdTableService.getOneIdTable(id);
        model.addAttribute("sysIdTable", sysIdTable);
        return "system/sysidtable/sysidtable_update";
    }


    /**
     * 更新
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam(value = "id") Integer id,
                             @RequestParam(value = "idId") String idId,
                             @RequestParam(value = "idName") String idName,
                             @RequestParam(value = "idLength") Integer idLength,
                             @RequestParam(value = "idValue") Integer idValue,
                             @RequestParam(required = false, defaultValue = "", value = "idPrefix") String idPrefix,
                             @RequestParam(required = false, defaultValue = "", value = "idSuffix") String idSuffix,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        SysIdTable sysIdTable = new SysIdTable();
        sysIdTable.setId(id);
        sysIdTable.setIdId(idId);
        sysIdTable.setIdName(idName);
        sysIdTable.setIdLength(idLength);
        sysIdTable.setIdValue(idValue);
        sysIdTable.setUpdateUser(getSysUserId());
        sysIdTable.setDescript(descript);
        sysIdTable.setIdPrefix(idPrefix);
        sysIdTable.setIdSuffix(idSuffix);
        if (StringUtils.isBlank(idPrefix)) {
            sysIdTable.setHasPrefix("1"); // 没有前缀
        } else {
            sysIdTable.setHasPrefix("0");
        }
        if (StringUtils.isBlank(idSuffix)) {
            sysIdTable.setHasSuffix("1"); // 没有后缀
        } else {
            sysIdTable.setHasSuffix("0");
        }

        int num = sysIdTableService.updateIdTable(sysIdTable);
        if (num > 0) {
            return BaseResult.success("修改业务流水号成功!");
        } else {
            return BaseResult.failure("修改业务流水号失败!");
        }
    }

}
