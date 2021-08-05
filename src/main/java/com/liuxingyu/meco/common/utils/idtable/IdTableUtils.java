package com.liuxingyu.meco.common.utils.idtable;

import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.mybatis.MybatisSqlTool;
import com.liuxingyu.meco.common.utils.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * @author liuxingyu01
 * @date 2021-03-28-22:51
 * @description 自定义业务流水号工具类，需要先维护一个，再使用
 * <p>
 * 后需优化，可以增加一个定时器
 **/
public class IdTableUtils {
    final static Logger logger = LoggerFactory.getLogger(IdTableUtils.class);

    private static RedisUtil redisUtil;

    private static RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            Object bean = SpringUtil.getBean("redisUtil");
            if (bean == null) {
                logger.error("redisUtil bean is null!");
            }
            redisUtil = (RedisUtil) bean;
        }
        return redisUtil;
    }


    /**
     * 获取下一个整数值
     *
     * @param idCode
     * @return
     */
    public static int nextIntValue(String idCode) {
        return getNextIntIdValue(idCode);
    }


    /**
     * 获取下一长整数值
     *
     * @param idCode
     * @return
     */
    public static long nextLongValue(String idCode) {
        return getNextLongIdValue(idCode);
    }


    /**
     * 获取下一个字符串
     *
     * @param idCode
     * @return
     */
    public static String nextStringValue(String idCode) {
        return getNextStringIdValue(idCode);
    }


    /**
     * 获取下一个整形流水号值
     *
     * @param idCode
     * @return
     */
    private static int getNextIntIdValue(String idCode) {
        int maxId = 0;
        try {
            Map idT = getIdTable(idCode);
            if (idT == null) {
                throw new RuntimeException("getNextIntIdValue - 未找到ID_ID 为【" + idCode + "】的最大号记录！");
            }
            // 获取当前最大编号
            maxId = idT.get("id_value") == null ? 0 : Integer.parseInt(idT.get("id_value").toString());
            maxId++;
            // 更新最大号
            updateIdTable(maxId, idCode);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("getNextIntIdValue - 获取业务流水号[" + idCode + "]出错,Exception = {e}", e);
            }
        }
        return maxId;
    }


    /**
     * 获取长整形流水号值
     *
     * @param idCode
     * @return
     */
    private static long getNextLongIdValue(String idCode) {
        int maxId = 0;
        try {
            Map idT = getIdTable(idCode);
            if (idT == null) {
                throw new RuntimeException("getNextIntIdValue - 未找到ID_ID 为【" + idCode + "】的最大号记录！");
            }
            //获取当前最大编号
            maxId = idT.get("id_value") == null ? 0 : Integer.parseInt(idT.get("id_value").toString());
            maxId++;
            //更新最大号
            updateIdTable(maxId, idCode);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("getNextIntIdValue - 获取业务流水号[" + idCode + "]出错,Exception = {e}", e);
            }
        }
        return maxId;
    }


    /**
     * 获取字符串流水号值
     *
     * @param idCode
     * @return
     */
    private static String getNextStringIdValue(String idCode) {
        String retStr = "";
        try {
            Map idT = getIdTable(idCode);
            if (idT == null) {
                throw new RuntimeException("未找到ID_ID 为【" + idCode + "】的最大号记录！");
            }
            // 获取当前最大编号
            int maxId = idT.get("id_value") == null ? 0 : Integer.parseInt(idT.get("id_value").toString());
            maxId++;

            String hasPrefix = (String) idT.get("has_prefix");
            // 是否有前缀 0有，1没有
            String idPrefix = "";
            if ("0".equals(hasPrefix)) {
                // 前缀
                idPrefix = Optional.ofNullable(idT.get("id_prefix")).orElse("").toString();
            }
            // 是否有后缀 0有，1没有
            String hasSuffix = (String) idT.get("has_suffix");
            String idSuffix = "";
            if ("0".equals(hasSuffix)) {
                // 后缀
                idSuffix = Optional.ofNullable(idT.get("id_suffix")).orElse("").toString();
            }

            // 长度
            int idLen = idT.get("id_length") == null ? 10 : Integer.parseInt(idT.get("id_length").toString());
            int numLen = idLen - idPrefix.length() - idSuffix.length();
            if (numLen < String.valueOf(maxId).length()) {
                logger.error("getNextStringIdValue -- 编码长度不够!");
                throw new RuntimeException("编码长度不够！");
            } else {
                String maxIdStr = get0Str(maxId, numLen);
                retStr = idPrefix + maxIdStr + idSuffix;
            }
            // 更新最大号
            updateIdTable(maxId, idCode);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("getNextStringIdValue - 获取业务流水号[" + idCode + "]出错,Exception = {e}", e);
            }
        }
        return retStr;
    }


    /**
     * 获取最大号信息
     *
     * @param idCode 流水号code
     * @return
     */
    private static Map getIdTable(String idCode) {
        Map result = (Map) getRedisUtil().get(idCode);
        // 先从redis中获取，没有再去数据库里查
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            String sql = "select * from sys_id_table where id_id= '" + idCode + "'";
            result = MybatisSqlTool.selectMapAnySql(sql);
            // 放入redis中
            String id_id = (String) result.get("id_id");
            redisUtil.set(id_id, result, -1);
            return result;
        }
    }


    /**
     * 更新最大号
     *
     * @param maxId
     * @param idCode
     * @return
     */
    private static void updateIdTable(int maxId, String idCode) {
        Map result = (Map) getRedisUtil().get(idCode);
        if (result != null && !result.isEmpty()) {
            result.put("id_value", maxId);
            redisUtil.set(idCode, result, -1);
        } else {
            String sql = "update sys_id_table set id_value= " + maxId + " where id_id= '" + idCode + "'";
            MybatisSqlTool.updateAnySql(sql);
        }
    }


    /**
     * 获取指定位数的数字字符串，不足补0
     *
     * @param maxId
     * @param numLen
     * @return
     */
    private static String get0Str(long maxId, int numLen) {
        StringBuilder retStr = new StringBuilder();
        int needLen = numLen - String.valueOf(maxId).length();
        for (int i = 0; i < needLen; i++) {
            retStr.append("0");
        }
        return retStr + String.valueOf(maxId);
    }

}
