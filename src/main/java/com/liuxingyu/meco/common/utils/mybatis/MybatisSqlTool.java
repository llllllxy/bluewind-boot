package com.liuxingyu.meco.common.utils.mybatis;

import com.liuxingyu.meco.common.utils.mybatis.service.MybatisSqlToolService;
import com.liuxingyu.meco.common.utils.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-05-24-19:01
 * @description Mybatis工具类，用于直接在Java代码里拼接SQL时使用
 **/
public class MybatisSqlTool {
    final static Logger logger = LoggerFactory.getLogger(MybatisSqlTool.class);

    private static MybatisSqlToolService mybatisSqlToolService;

    private static MybatisSqlToolService getMybatisSqlToolService() {
        if (mybatisSqlToolService == null) {
            mybatisSqlToolService = SpringUtil.getBean(MybatisSqlToolService.class);
        }
        return mybatisSqlToolService;
    }


    /**
     * 执行查询语句 返回一个List
     *
     * @param selectSql sql语句
     * @return
     */
    public static List<Map> selectAnySql(String selectSql) {
        List<Map> list = getMybatisSqlToolService().commonSelect(selectSql);
        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * 执行查询语句 返回一个Map
     *
     * @param selectSql sql语句
     * @return
     */
    public static Map selectMapAnySql(String selectSql) {
        List<Map> list = selectAnySql(selectSql);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return new HashMap<>();
        }
    }


    /**
     * 执行查询语句 返回一个String
     *
     * @param selectSql sql语句
     * @param key       返回String结果字段的键值
     * @return
     */
    public static String selectStringAnySql(String selectSql, String key) {
        List<Map> list = selectAnySql(selectSql);
        if (list != null && !list.isEmpty()) {
            return list.get(0).get(key) == null ? "" : (String) list.get(0).get(key);
        } else {
            return "";
        }
    }


    /**
     * 根据条件查询字段值
     *
     * @param fieldName 所要查的字段
     * @param tableName 所对应的表名
     * @param conditionName 条件字段名
     * @param conditionValue 条件的值
     * @return String
     */
    public static String selectStringByCondition(String fieldName, String tableName,
                                                 String conditionName, String conditionValue) {
        String sql = "select " + fieldName + "  from " + tableName + "  where " + conditionName
                + "  = '" + conditionValue + "'";
        List<Map> list = selectAnySql(sql);
        if (list != null && !list.isEmpty()) {
            Map map = list.get(0);
            String fieldValue = map.get(fieldName) == null ? "" : (String) map.get(fieldName);
            return fieldValue;
        } else {
            return "";
        }
    }


    /**
     * 根据条件查询结果列表
     *
     * @param tableName 所对应的表名
     * @param conditionName 条件字段名
     * @param conditionValue 条件的值
     * @return String
     */
    public static List<Map> selectByCondition(String tableName, String conditionName, String conditionValue) {
        String sql = "select * from " + tableName + " where " + conditionName
                + " = '" + conditionValue + "'";
        return selectAnySql(sql);
    }


    /**
     * 执行更新语句 返回 影响的结果行
     *
     * @param updateSql
     * @return
     */
    public static int updateAnySql(String updateSql) {
        int result = 0;
        updateSql = updateSql.replace("\t", " ");
        String[] sa = updateSql.trim().split(" ");
        if (sa.length > 0) {
            String type = sa[0].trim();
            if (logger.isInfoEnabled()) {
                logger.info("MybatisSqlTool -- updateAnySql -- type = {}", type);
            }
            if ("UPDATE".equalsIgnoreCase(type)) {
                result = getMybatisSqlToolService().commonUpdate(updateSql);
                if (logger.isDebugEnabled()) {
                    logger.debug("MybatisSqlTool -- updateSql = {}", updateSql);
                }
            } else if ("INSERT".equalsIgnoreCase(type)) {
                result = getMybatisSqlToolService().commonInsert(updateSql);
                if (logger.isDebugEnabled()) {
                    logger.debug("MybatisSqlTool -- insertSql = {}", updateSql);
                }
            } else if ("DELETE".equalsIgnoreCase(type)) {
                result = getMybatisSqlToolService().commonDelete(updateSql);
                if (logger.isDebugEnabled()) {
                    logger.debug("MybatisSqlTool -- deleteSql = {}", updateSql);
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("MybatisSqlTool -- SQL格式有误 = {}", updateSql);
                }
            }
        }
        return result;
    }


    /**
     * 执行插入语句 返回 影响的结果行
     *
     * @param insertSql
     * @return
     */
    public static int insertAnySql(String insertSql) {
        if (logger.isDebugEnabled()) {
            logger.debug("MybatisSqlTool -- insertSql = {}", insertSql);
        }
        return getMybatisSqlToolService().commonInsert(insertSql);
    }


    /**
     * 执行删除语句 返回 影响的结果行
     *
     * @param deleteSql
     * @return
     */
    public static int deleteAnySql(String deleteSql) {
        if (logger.isDebugEnabled()) {
            logger.debug("MybatisSqlTool -- deleteSql = {}", deleteSql);
        }
        return getMybatisSqlToolService().commonDelete(deleteSql);
    }

}
