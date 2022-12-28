package com.bluewind.boot.module.tool.dataset.generalexport.service;

import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.collect.MapUtils;
import com.bluewind.boot.common.utils.spring.SpringContextUtil;
import com.bluewind.boot.module.tool.dataset.beanmodule.IBaseBean;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.Dataset;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.DatasetLine;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.DatasetLineSqlColumn;
import com.bluewind.boot.module.tool.dataset.generalexport.mapper.GeneralExportMapper;
import com.github.benmanes.caffeine.cache.Cache;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
/**
 * @author liuxingyu01
 * @date 2022-11-24 21:49
 * @description 通用数据集工具服务层
 **/
@Service
public class GeneralExportService {
    static final Logger log = LoggerFactory.getLogger(GeneralExportService.class);

    /**
     * 数据格式--值
     */
    static final String P_VALUE = "0";
    /**
     * 数据格式--对象
     */
    static final String P_OBJECT = "1";
    /**
     * 数据格式--列表
     */
    static final String P_ARRAY = "2";

    /**
     * 数据来源--sql
     */
    static final String SOURCE_SQL = "0";
    /**
     * 数据来源--bean
     */
    static final String SOURCE_BEAN = "1";


    private final GeneralExportMapper generalExportMapper;

    // 基于构造函数的依赖注入
    public GeneralExportService(GeneralExportMapper generalExportMapper) {
        this.generalExportMapper = generalExportMapper;
    }

    @Resource(name = "caffeineDictCache")
    private Cache<String, Map<String, String>> caffeineDictCache;

    @Resource(name = "caffeineOrganCache")
    private Cache<String, String> caffeineOrganCache;


    /**
     * 根据dataId查询数据集配置信息
     *
     * @param dataId 数据集id
     * @return Dataset
     */
    public Dataset getDatasetByDataId(String dataId) {
        return generalExportMapper.getDatasetByDataId(dataId);
    }

    /**
     * 根据dataId查询数据集明细表信息
     *
     * @param dataId 数据集id
     * @return List<DatasetLine>
     */
    public List<DatasetLine> getDatasetLineByDataId(String dataId) {
        return generalExportMapper.getDatasetLineByDataId(dataId);
    }


    /**
     * 根据数据集配置生成数据
     *
     * @param datasetLines 数据集指标明细
     * @param paraInMap    请求参数信息
     * @return Map<String, Object>
     */
    public Map<String, Object> generalExportData(List<DatasetLine> datasetLines, Map<String, Object> paraInMap) {
        // 用于储存最终结果
        Map<String, Object> resultMap = new HashMap<>();

        // 使用异步工具，提高性能表现
        // 本来想用CompletableFuture的，但是线程池的大小不太好估量，所以先采取这种方式吧
        final CountDownLatch latch = new CountDownLatch(datasetLines.size());

        for (DatasetLine datasetLine : datasetLines) {
            new Thread(() -> {
                String lineName = datasetLine.getLineName();
                String dataSource = datasetLine.getDataSource();
                if (SOURCE_SQL.equals(dataSource)) {
                    Object result = this.executeSQL(datasetLine, paraInMap);
                    resultMap.put(lineName, result);
                } else if (SOURCE_BEAN.equals(dataSource)) {
                    Object result = this.executeBean(datasetLine, paraInMap);
                    resultMap.put(lineName, result);
                }
                latch.countDown();
            }).start();
        }
        // 结束异步等待，开启同步执行
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("GeneralExportService generalExportData InterruptedException :", e);
            throw new RuntimeException(e);
        }
        return resultMap;
    }


    /**
     * 执行sql
     *
     * @param datasetLine DatasetLine指标信息
     * @param paraInMap   请求参数信息
     * @return
     */
    public Object executeSQL(DatasetLine datasetLine, Map<String, Object> paraInMap) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        // 获取待执行的sql信息
        String sqlStr = datasetLine.getSqlInfo().trim();
        // 如果入参不为空，则进行替换，得到替换后的sql
        if (MapUtils.isNotEmpty(paraInMap)) {

            for (Map.Entry<String, Object> entry : paraInMap.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue() == null ? "" : entry.getValue().toString();

                if (StringUtils.isNotEmpty(key)) {
                    String singlekey = "#" + key + "#";
                    if (sqlStr.indexOf(singlekey) > -1) {
                        log.debug("GeneralExportService -- executeSQL -- singlekey:{},val:{}", singlekey, val);
                        sqlStr = sqlStr.replace(singlekey, val);
                    }

                    String multiKey = "$" + key + "$";
                    if (multiKey.indexOf(multiKey) > -1) {
                        String multiVal = val.replace(",", "','");
                        log.debug("GeneralExportService -- executeSQL -- multiKey:{},multiVal:{}", multiKey, multiVal);
                        sqlStr = sqlStr.replace(multiKey, multiVal);
                    }
                }
            }
        }
        log.debug("GeneralExportService -- executeSQL -- 未处理缺少条件的SQL内容的SQL -- sqlStr:{}", sqlStr);

        String newSqlInfo = sqlStr;
        while (newSqlInfo.indexOf("#") > -1) {
            newSqlInfo = noParSqlFilter(newSqlInfo, '#');
        }
        log.debug("GeneralExportService -- executeSQL -- 已处理占位符(#)条件的SQL内容的SQL -- newSqlInfo:{}", newSqlInfo);
        if (StringUtils.isEmpty(newSqlInfo)) {
            log.error("GeneralExportService -- executeSQL -- SQL的查询条件为空的#类型的内容替换失败");
            return null;
        }
        while (newSqlInfo.indexOf("$") > -1) {
            newSqlInfo = noParSqlFilter(newSqlInfo, '$');
        }
        log.debug("GeneralExportService -- executeSQL -- 已处理占位符($)条件的SQL内容的SQL -- newSqlInfo:{}", newSqlInfo);

        if (newSqlInfo.indexOf("#") > -1 || newSqlInfo.indexOf("$") > -1) {
            log.error("CommonDataGetServiceImpl -- executeSQL -- ERROR -- 组织数据异常[SQL中包含$或者#的占位符]:{}", newSqlInfo);
            return null;
        }
        // 校验参数是否合法，不合法的话，拒绝执行，直接返回null
        if (!filterSpecialWords(newSqlInfo)) {
            log.error("CommonDataGetServiceImpl -- executeSQL -- ERROR -- 配置异常[platform_dataset_line.sql_info的查询SQL不合法]:{}", newSqlInfo);
            return null;
        }
        log.debug("CommonDataGetServiceImpl -- executeSQL -- newSqlInfo:{}", newSqlInfo);

        // 执行sql查询
        dataList = generalExportMapper.executeSQL(newSqlInfo);
        // 判断结果集是否为空，为空的话直接返回，没必要往下进行了
        if (CollectionUtils.isEmpty(dataList)) {
            log.debug("CommonDataGetServiceImpl -- executeSQL -- 未查询到任何结果");
            return null;
        }

        String dataPattern = datasetLine.getDataPattern();
        // 列column配置信息（字典，数据权限，数字格式化）
        List<DatasetLineSqlColumn> datasetLineSqlColumns = generalExportMapper.getDatasetLineSqlColumnByDataIdAndLineNo(datasetLine);

        if (P_VALUE.equals(dataPattern)) {
            Object obj = dataList.get(0).get("value");
            // 如果取不到别名为value的值的时候，则取HashMap里第一个元素的值
            if (Objects.isNull(obj)) {
                obj =  dataList.get(0).values().toArray()[0];
            }
            return obj;
        }

        if (P_OBJECT.equals(dataPattern)) {
            Map<String, Object> dataMap = dataList.get(0);
            convertSqlColumn(datasetLineSqlColumns, dataMap);
            return dataMap;
        }


        if (P_ARRAY.equals(dataPattern)) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataMap = dataList.get(i);
                convertSqlColumn(datasetLineSqlColumns, dataMap);
            }
            return dataList;
        }

        return null;
    }


    /**
     * 执行bean
     *
     * @param datasetLine DatasetLine指标信息
     * @param paraInMap   请求参数信息
     * @return
     */
    public Object executeBean(DatasetLine datasetLine, Map<String, Object> paraInMap) {
        IBaseBean baseBeanService = SpringContextUtil.getBean(datasetLine.getBeanInfo(), IBaseBean.class);
        return baseBeanService.execute(paraInMap);
    }


    /**
     * 根据PLATFORM_DATASET_LINE_SQL_COLUMN表里的配置，对数据进行格式化和转换
     *
     * @param datasetLineSqlColumns DatasetLineSqlColumn
     * @param dataMap               待转换的数据
     */
    private void convertSqlColumn(List<DatasetLineSqlColumn> datasetLineSqlColumns, Map<String, Object> dataMap) {
        for (DatasetLineSqlColumn datasetLineSqlColumn : datasetLineSqlColumns) {

            String colKey = datasetLineSqlColumn.getColKey();
            String colDataType = datasetLineSqlColumn.getColDataType();
            int iDecimalCnt = datasetLineSqlColumn.getDecimalCnt() == null ? 0 : datasetLineSqlColumn.getDecimalCnt();
            String isDict = datasetLineSqlColumn.getIsDict();
            String dictInfo = datasetLineSqlColumn.getDictInfo();
            String isOrgan = datasetLineSqlColumn.getIsOrgan();
            String organInfo = datasetLineSqlColumn.getOrganInfo();

            if ("1".equals(colDataType)) {
                // 做数据格式的转换（数字保留几位小数）
                if (dataMap.containsKey(colKey)) {
                    BigDecimal newValue = BigDecimal.ZERO;
                    String oldValue = Optional.ofNullable(dataMap.get(colKey)).orElse("").toString();
                    if (StringUtils.isNotBlank(oldValue)) {
                        newValue = new BigDecimal(oldValue).setScale(iDecimalCnt, BigDecimal.ROUND_HALF_UP);
                    }
                    dataMap.put(colKey, newValue);
                }
            } else { // 数据字典转换，link#MM_EQUIP_LINK
                if ("1".equals(isDict)) {
                    String[] dictInfoArr = dictInfo.split("#");
                    String iDtKey = dictInfoArr[0];
                    String x1DictId = dictInfoArr[1];

                    if (StringUtils.isNotEmpty(x1DictId) && StringUtils.isNotEmpty(iDtKey)) {
                        if (dataMap.containsKey(iDtKey)) {
                            String newValue = "";
                            String oldValue = Optional.ofNullable(dataMap.get(iDtKey)).orElse("").toString();
                            if (StringUtils.isNotEmpty(oldValue)) {
                                newValue = getDictCache(x1DictId).get(oldValue);
                            }
                            dataMap.put(colKey, newValue);
                        }
                    }
                }

                if ("1".equals(isOrgan)) { // 部门编码转名称，com_id
                    String[] organInfoArr = organInfo.split("#");
                    String organIdField = organInfoArr[0];
                    String permitType = organInfoArr[1];

                    if (StringUtils.isNotEmpty(organIdField)) {
                        if (dataMap.containsKey(organIdField)) {
                            String newValue = "";
                            String oldValue = Optional.ofNullable(dataMap.get(organIdField)).orElse("").toString();
                            if (StringUtils.isNotEmpty(oldValue)) {
                                newValue = getOrganCache(oldValue, permitType);
                            }
                            dataMap.put(colKey, newValue);
                        }
                    }
                }
            }
        }
    }

    private String noParSqlFilter(String sqlInfo, char c) {
        log.debug(" -- noParSqlFilter -- start sqlInfo:{}", sqlInfo);
        try {
            if (sqlInfo.indexOf(c) == -1) {
                return sqlInfo;
            }

            String startStr = sqlInfo.substring(0, sqlInfo.indexOf(c));
            log.debug(" -- noParSqlFilter -- startStr:{}", startStr);

            int indexAnd = StringUtils.indexOfIgnoreCase(startStr, "and");  // startStr.lastIndexOf("and");
            int indexWhere = StringUtils.indexOfIgnoreCase(startStr, "where"); // startStr.lastIndexOf("where");
            if (indexAnd == -1 && indexWhere == -1) {
                log.debug(" -- noParSqlFilter -- 符号前面的where,and都不存在 -- indexAnd:{},indexWhere:{}", indexAnd, indexWhere);
                return null;
            }
            int startIndex = 0;
            if (indexAnd > indexWhere) {
                startIndex = indexAnd + 3;
            } else {
                startIndex = indexAnd + 5;
            }

            int c2Index = sqlInfo.indexOf(c, sqlInfo.indexOf(c) + 1) + 1;
            if (c2Index <= startIndex) {
                log.error(" -- noParSqlFilter -- 第二个占位符($/#)的下标小于查询条件的起始位置 -- c2Index:{}, startIndex:{}", c2Index, startIndex);
                return null;
            }

            String atomParStr = sqlInfo.substring(startIndex, c2Index);
            log.debug(" -- noParSqlFilter -- 当前查询条件 -- atomParStr:{}", atomParStr);
            int leftParenthesesCnt = atomParStr.length() - atomParStr.replace("(", "").length();
            int righParenthesesCnt = atomParStr.length() - atomParStr.replace(")", "").length();
            if (leftParenthesesCnt < righParenthesesCnt) {
                log.error(" -- noParSqlFilter -- 查询条件的中左括号比右括号少，不对 -- leftParenthesesCnt:{},righParenthesesCnt:{}", leftParenthesesCnt, righParenthesesCnt);
                return null;
            }

            int rightSearchCnt = leftParenthesesCnt - righParenthesesCnt;
            int endIndex = 0;

            int tempIndex = c2Index;
            for (int i = 0; i < rightSearchCnt; i++) {
                endIndex = sqlInfo.indexOf(")", tempIndex) + 1;
                tempIndex = endIndex;
            }
            if (endIndex < c2Index) {
                log.error(" -- noParSqlFilter -- 查询条件的中最外层的右括号下标小于第二个占位符($/#)的下标，不对 -- endIndex:{},c2Index:{}", endIndex, c2Index);
                return null;
            }

            String singleStr = sqlInfo.substring(startIndex, endIndex);

            return sqlInfo.replace(singleStr, " 1=1 ");
        } catch (Exception e) {
            log.error(" -- noParSqlFilter -- 处理SQL的无参数字段，出现异常:", e);
            return null;
        }
    }


    /**
     * 过滤sql中的特殊关键字等，防止危险sql
     *
     * @param sqlInfo
     * @return
     */
    private Boolean filterSpecialWords(String sqlInfo) {
        if (StringUtils.isEmpty(sqlInfo)) {
            return false;
        }
        if (StringUtils.indexOfIgnoreCase(sqlInfo, "create table") > -1
                || StringUtils.indexOfIgnoreCase(sqlInfo, "alter") > -1
                || StringUtils.indexOfIgnoreCase(sqlInfo, "drop") > -1
                || StringUtils.indexOfIgnoreCase(sqlInfo, "truncate") > -1
                || StringUtils.indexOfIgnoreCase(sqlInfo, "delete") > -1
                || StringUtils.indexOfIgnoreCase(sqlInfo, "update") > -1
                || StringUtils.indexOfIgnoreCase(sqlInfo, "insert") > -1) {
            return false;
        }
        if (StringUtils.indexOfIgnoreCase(sqlInfo, "select") == -1) {
            return false;
        }
        return true;
    }


    /**
     * 获取缓存，字典
     *
     * @param dictId 字典编码
     * @return
     */
    private Map<String, String> getDictCache(String dictId) {
        Map<String, String> dictMap = caffeineDictCache.getIfPresent(dictId);

        if (MapUtils.isEmpty(dictMap)) {
            dictMap = DictUtils.getDictMap(dictId);
            if (Objects.isNull(dictMap)) {
                dictMap = new HashMap<>();
            }
            caffeineDictCache.put(dictId, dictMap);
        }
        return dictMap;
    }


    /**
     * 获取缓存，数据权限
     *
     * @param organId    数据权限编码
     * @param permitType 数据权限类型
     * @return
     */
    private String getOrganCache(String organId, String permitType) {
        String organName = caffeineOrganCache.getIfPresent(permitType + "&" + organId);
        if (StringUtils.isEmpty(organName)) {
            // 数据权限系统暂未实现
            // organName = DeptTool.getDeptName(organId, getDataPermissionTypeEnum(permitType));
            if (Objects.isNull(organName)) {
                organName = "-";
            }
            caffeineOrganCache.put(permitType + "&" + organId, organName);
        }
        return organName;
    }



    /**
     * 获取枚举值--数据权限系统暂未实现，所以代码先注释掉
     *
     * @param code
     * @return
     */
//    public static DataPermissionTypeEnum getDataPermissionTypeEnum(String code) {
//        for (DataPermissionTypeEnum e : DataPermissionTypeEnum.values()) {
//            if (e.getCode().equals(code)) {
//                return e;
//            }
//        }
//        return null;
//    }
}
