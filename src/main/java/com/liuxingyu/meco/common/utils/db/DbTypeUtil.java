package com.liuxingyu.meco.common.utils.db;

import com.liuxingyu.meco.common.enums.DbType;
import com.liuxingyu.meco.common.utils.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author liuxingyu01
 * @date 2021-04-23-15:35
 * @description 获取数据库类型工具类
 **/
public class DbTypeUtil {
    private static final Logger logger = LoggerFactory.getLogger(DbTypeUtil.class);

    public static DbType getDbType() {
        DataSource dataSource = (DataSource) SpringUtil.getBean("dataSource");
        return getDbTypeByDataSource(dataSource);
    }

    public static DbType getDbTypeByDataSource(DataSource dataSource) {
        String productName = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            productName = conn.getMetaData().getDatabaseProductName();
            logger.debug("productName of datasource  = " + productName);
        } catch (Exception e) {
            logger.error("catch get productName of datasource error", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.error("finally get productName of datasource error", e);
                }
            }
        }

        if (productName != null) {
            productName = productName.toLowerCase();
            if (productName.contains("mysql")) {
                return DbType.MySQL;
            } else if (productName.contains("oracle")) {
                return DbType.Oracle;
            } else if (productName.contains("db2")) {
                return DbType.DB2;
            } else if (productName.contains("h2")) {
                return DbType.H2;
            } else if (productName.contains("postgresql")) {
                return DbType.PostgreSQL;
            } else {
                return DbType.UnKown;
            }
        } else {
            return DbType.UnKown;
        }
    }


}
