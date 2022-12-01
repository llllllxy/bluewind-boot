package com.bluewind.boot.module.tool.datasync.util;

import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.common.utils.spring.SpringUtil;
import com.bluewind.boot.module.tool.datasync.mapper.DataSyncDataSourceMapper;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-08-02 14:05
 * @description JdbcUtils工具类，datasync模块专用
 **/
public class JdbcUtils {
    private static final Logger log = LoggerFactory.getLogger(JdbcUtils.class);

    private static DataSyncDataSourceMapper dataSyncDataSourceMapper;

    private static DataSyncDataSourceMapper getDataSyncDataSourceMapper() {
        if (dataSyncDataSourceMapper == null) {
            Object bean = SpringUtil.getBean("dataSyncDataSourceMapper");
            dataSyncDataSourceMapper = (DataSyncDataSourceMapper) bean;
        }
        return dataSyncDataSourceMapper;
    }

    /**
     * 使用jdbc连接数据
     */
    public static Connection getConnection(String dataSourceId) throws ClassNotFoundException, SQLException {
        Map<String, Object> dataSourceMap = getDataSyncDataSourceMapper().getDataSource(dataSourceId);

        if (log.isDebugEnabled()) {
            log.debug("获取到数据源配置："+dataSourceMap);
        }
        if (MapUtils.isNotEmpty(dataSourceMap)) {
            Class.forName(StringUtils.getString(dataSourceMap.get("db_driver")));
            return DriverManager.getConnection(StringUtils.getString(dataSourceMap.get("db_url")),
                    StringUtils.getString(dataSourceMap.get("db_user")),
                    StringUtils.getString(dataSourceMap.get("db_pwd")));
        } else {
            if (log.isDebugEnabled()) {
                log.debug("未找到["+dataSourceId+"]数据源的配置信息。");
            }
            throw new RuntimeException("未找到["+dataSourceId+"]数据源的配置信息。");
        }
    }


    /**
     * 使用jdbc执行select
     * @param con 连接
     * @param sql 待执行的sql语句
     * @return 执行结果
     */
    public static List<Map<String, Object>> selectSql(Connection con, String sql) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        try (PreparedStatement stat = con.prepareStatement(sql)) {
            ResultSet rs = stat.executeQuery();
            returnList = resultSet2List(rs);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("selectSql - 执行SELECT出错：" + sql);
            }
        }
        return returnList;
    }


    /**
     * 使用jdbc执行update，delete和insert
     * @param con 连接
     * @param sql 待执行的sql语句
     * @return 执行结果
     */
    public static int executeSql(Connection con, String sql){
        int retInt = 0;
        try (PreparedStatement stat = con.prepareStatement(sql)) {
            retInt = stat.executeUpdate();
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("executeSql - 执行更新出错：" + sql);
            }
        }
        return retInt;
    }

    /**
     *  Resultset转List
     * @param rs ResultSet
     * @return List<Map<String, Object>>
     * @throws SQLException 异常
     */
    private static List<Map<String, Object>> resultSet2List(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i).toUpperCase(), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

}
