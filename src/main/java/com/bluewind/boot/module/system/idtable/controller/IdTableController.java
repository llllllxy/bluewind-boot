package com.bluewind.boot.module.system.idtable.controller;

import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.idtable.service.IdTableService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.module.system.idtable.entity.IdTable;
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
@RequestMapping("/idtable")
public class IdTableController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(IdTableController.class);

    @Autowired
    private IdTableService idTableService;


    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        return "system/idtable/list";
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
                           @RequestParam(required = false, defaultValue = "", value = "idCode") String idCode,
                           @RequestParam(required = false, defaultValue = "", value = "idName") String idName,
                           @RequestParam(required = false, defaultValue = "", value = "createTime") String createTime,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("SysIdTableController -- 页面大小：" + pageSize + "--页码:" + pageNum);
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("idCode", idCode);
        paraMap.put("idName", idName);
        paraMap.put("createTime", createTime);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<IdTable> idTableList = idTableService.getSysIdTableList(paraMap);

        PageInfo<IdTable> pageinfo = new PageInfo<>(idTableList);
        // 取出查询结果
        List<IdTable> rows = pageinfo.getList();
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
        return "system/idtable/add";
    }


    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam(value = "idCode") String idCode,
                          @RequestParam(value = "idName") String idName,
                          @RequestParam(value = "idLength") Integer idLength,
                          @RequestParam(value = "idValue") Integer idValue,
                          @RequestParam(required = false, defaultValue = "", value = "idPrefix") String idPrefix,
                          @RequestParam(required = false, defaultValue = "", value = "idSuffix") String idSuffix,
                          @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        Integer exist = idTableService.checkExistByIdCode(idCode);
        if (exist != null ) {
            return BaseResult.failure("新增业务流水号失败，此流水号编码已存在!");
        }

        IdTable idTable = new IdTable();
        idTable.setIdId(IdGenerate.nextId());
        idTable.setIdCode(idCode);
        idTable.setIdName(idName);
        idTable.setIdLength(idLength);
        idTable.setIdValue(idValue);
        idTable.setCreateUser(getSysUserId());
        idTable.setDescript(descript);
        idTable.setIdPrefix(idPrefix);
        idTable.setIdSuffix(idSuffix);
        if (StringUtils.isBlank(idPrefix)) {
            idTable.setHasPrefix("1"); // 没有前缀
        } else {
            idTable.setHasPrefix("0");
        }
        if (StringUtils.isBlank(idSuffix)) {
            idTable.setHasSuffix("1"); // 没有后缀
        } else {
            idTable.setHasSuffix("0");
        }

        int num = idTableService.addOneIdTable(idTable);
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
    @RequestMapping(value = "/delete/{idId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String idId) {
        int num = idTableService.deleteOne(idId);
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
    @RequestMapping(value = "/forUpdate/{idId}", method = RequestMethod.GET)
    public String forUpdate(@PathVariable String idId, Model model) {
        IdTable idTable = idTableService.getOneIdTable(idId);
        model.addAttribute("idTable", idTable);
        return "system/idtable/update";
    }


    /**
     * 更新
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam(value = "idId") String idId,
                             @RequestParam(value = "idCode") String idCode,
                             @RequestParam(value = "idName") String idName,
                             @RequestParam(value = "idLength") Integer idLength,
                             @RequestParam(value = "idValue") Integer idValue,
                             @RequestParam(required = false, defaultValue = "", value = "idPrefix") String idPrefix,
                             @RequestParam(required = false, defaultValue = "", value = "idSuffix") String idSuffix,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript) {
        IdTable idTable = new IdTable();
        idTable.setIdId(idId);
        idTable.setIdCode(idCode);
        idTable.setIdName(idName);
        idTable.setIdLength(idLength);
        idTable.setIdValue(idValue);
        idTable.setUpdateUser(getSysUserId());
        idTable.setDescript(descript);
        idTable.setIdPrefix(idPrefix);
        idTable.setIdSuffix(idSuffix);
        if (StringUtils.isBlank(idPrefix)) {
            idTable.setHasPrefix("1"); // 没有前缀
        } else {
            idTable.setHasPrefix("0");
        }
        if (StringUtils.isBlank(idSuffix)) {
            idTable.setHasSuffix("1"); // 没有后缀
        } else {
            idTable.setHasSuffix("0");
        }

        int num = idTableService.updateIdTable(idTable);
        if (num > 0) {
            return BaseResult.success("修改业务流水号成功!");
        } else {
            return BaseResult.failure("修改业务流水号失败!");
        }
    }

}
