package com.bluewind.boot.module.tool.dataset.generalexport.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.Dataset;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.DatasetLine;
import com.bluewind.boot.module.tool.dataset.generalexport.service.GeneralExportService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author liuxingyu01
 * @date 2022-11-23 10:12
 * @description 通用数据集工具控制器
 **/
@RequestMapping("/anon")
// @RequestMapping("/anon/itfc") // 后续会用这种上下文，安全通过第三方接口过滤器来控制
@RestController
@Api(value = "通用数据集工具控制器", description = "通用数据集工具控制器")
public class GeneralExportController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(GeneralExportController.class);

    private final GeneralExportService generalExportService;

    // 构造方法注入
    public GeneralExportController(GeneralExportService generalExportService) {
        this.generalExportService = generalExportService;
    }

    /**
     * 通用数据查询<br/>
     * <p>
     * 参数：parm<br/>
     * <pre>
     *     url编码过的字符串
     *     {
     *         "key1": "value1",
     *         "key2": "value2",
     *         ...
     *     }
     * </pre>
     *
     * @param dataid 数据集ID
     * @param parm   url编码过的jsonString
     * @return Result
     */
    @ApiOperation(value = "通用数据集-数据导出")
    @RequestMapping(value = "/getdata", method = {RequestMethod.GET, RequestMethod.POST})
    @LogAround("通用数据集-数据导出 -- getdata")
    public BaseResult getdata(@RequestParam(value = "dataid") String dataid,
                              @RequestParam(required = false, value = "parm") String parm) {
        if (StringUtils.isEmpty(dataid)) {
            return BaseResult.failure("参数异常：[dataid为空]");
        }
        Dataset dataset = generalExportService.getDatasetByDataId(dataid);
        if (Objects.isNull(dataset)) {
            return BaseResult.failure("配置异常：[不存在此数据集接口]");
        }
        List<DatasetLine> datasetLines = generalExportService.getDatasetLineByDataId(dataset.getDataId());
        if (CollectionUtils.isEmpty(datasetLines)) {
            return BaseResult.failure("配置异常：[数据集接口指标明细未配置]");
        }

        Map<String, Object> parmMap = new HashMap<>();
        if (StringUtils.isNotEmpty(parm)) {
            parm = URLDecoder.decode(parm);
            try {
                parmMap = JsonTool.parseMap(parm);
            } catch (Exception e) {
                log.error("GeneralExportController -- getdata -- 解析参数parm异常：", e);
                return BaseResult.failure("参数异常：[parm格式错误]");
            }
        }
        Map<String, Object> resultMap = generalExportService.generalExportData(datasetLines, parmMap);
        return BaseResult.success("获取数据成功", resultMap);
    }
}
