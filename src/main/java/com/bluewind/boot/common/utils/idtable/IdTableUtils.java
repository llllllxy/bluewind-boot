package com.bluewind.boot.common.utils.idtable;

import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.common.utils.mybatis.MybatisSqlTool;
import com.bluewind.boot.common.utils.spring.SpringUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author liuxingyu01
 * @date 2021-03-28-22:51
 * @description 自定义业务流水号工具类，需要先维护一个，再使用
 * 优化参考美团Leaf，从数据库批量的获取自增ID，每次从数据库取出一个号段范围，例如 (1,100] 代表100个ID，
 * 业务服务将号段在本地生成1~100的自增ID并加载到redis，减少对数据库的频率操作
 **/
public class IdTableUtils {
    final static Logger logger = LoggerFactory.getLogger(IdTableUtils.class);

    private static final String[] AFFIX_FORMAT = {"yyyy", "yy", "MM", "dd", "HH", "mm", "ss"};

    private static final int IDTABLE_CACHE_NUMBER = 100;

    private static RedisUtil redisUtil;

    private static RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            redisUtil = SpringUtil.getBean("redisUtil");
        }
        return redisUtil;
    }


    /**
     * 获取下一个流水号字符串
     *
     * @param idCode 流水号编码
     * @return String 流水号字符串
     */
    public synchronized static String nextId(String idCode) {
        return takeNextId(idCode);
    }


    /**
     * 获取下一个流水号字符串
     *
     * @param idCode 流水号编码
     * @return String 流水号字符串
     */
    public static String takeNextId(String idCode) {
        String cacheKey = SystemConst.SYSTEM_ID_TABLE + ":" + idCode;
        // 获取list缓存的长度，判断是否大于0
        if (getRedisUtil().lGetListSize(cacheKey) > 0) {
            Object value = getRedisUtil().lLeftPop(cacheKey);
            if (value != null) {
                return value.toString();
            }
        }
        List<Object> ids = generateNextIds(idCode);
        getRedisUtil().lRightPushAll(cacheKey, ids);
        // 递归调用，直到lGetListSize > 0
        return takeNextId(idCode);
    }


    /**
     * 批类构造流水号字符串
     *
     * @param idCode 流水号编码
     * @return List<Object> 流水号列表
     */
    private static List<Object> generateNextIds(String idCode) {
        List<Object> nextIds = new ArrayList<>();
        String sql = "select * from sys_id_table where id_code= '" + idCode + "'";
        Map idTable = MybatisSqlTool.selectMapAnySql(sql);
        if (idTable != null && !idTable.isEmpty()) {
            // 获取流水号的当前值
            int idValue = idTable.get("id_value") == null ? 0 : Integer.parseInt(idTable.get("id_value").toString());
            int maxId = idValue + IDTABLE_CACHE_NUMBER;
            // 更新id_value为最新值(加上IDTABLE_CACHE_NUMBER)
            String sql2 = "update sys_id_table set id_value= " + maxId + " where id_code= '" + idCode + "'";
            MybatisSqlTool.updateAnySql(sql2);
            // 循环获取100个流水号，放入nextIds中去
            for (int i = 1; i <= IDTABLE_CACHE_NUMBER; i++) {
                int nextIdValue = idValue + i;
                String nextId = generateNextId(idTable, nextIdValue);
                nextIds.add(nextId);
            }
            return nextIds;
        } else {
            throw new RuntimeException("未找到ID_CODE 为【" + idCode + "】的最大号记录！");
        }
    }


    /**
     * 根据规则构造流水号字符串
     *
     * @param idT         流水号Map
     * @param nextIdValue 流水号当前值
     * @return String 流水号字符串
     */
    private static String generateNextId(Map idT, int nextIdValue) {
        String retStr = "";
        try {
            Date dateTime = new Date();

            // 是否有前缀 1有，0没有
            String hasPrefix = (String) idT.get("has_prefix");
            // 前缀内容
            String idPrefix = Optional.ofNullable(idT.get("id_prefix")).orElse("").toString();
            idPrefix = compoundAffix(hasPrefix, idPrefix, dateTime);

            // 是否有后缀 1有，0没有
            String hasSuffix = (String) idT.get("has_suffix");
            String idSuffix = Optional.ofNullable(idT.get("id_suffix")).orElse("").toString();
            // 后缀内容
            idSuffix = compoundAffix(hasSuffix, idSuffix, dateTime);

            // 长度
            int idLen = idT.get("id_length") == null ? 10 : Integer.parseInt(idT.get("id_length").toString());
            int numLen = idLen - idPrefix.length() - idSuffix.length();
            if (numLen < String.valueOf(nextIdValue).length()) {
                logger.error("generateNextId -- 编码长度不够，请增加编码长度!");
                throw new RuntimeException("编码长度不够，请增加编码长度!");
            } else {
                String maxIdStr = get0Str(nextIdValue, numLen);
                retStr = idPrefix + maxIdStr + idSuffix;
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("generateNextId - 获取业务流水号[" + idT + "]出错,Exception = {e}", e);
            }
        }
        return retStr;
    }


    /**
     * 获取指定位数的数字字符串，不足补0
     *
     * @param nextIdValue 数字
     * @param numLen      位数
     * @return
     */
    private static String get0Str(long nextIdValue, int numLen) {
        StringBuilder retStr = new StringBuilder();
        int needLen = numLen - String.valueOf(nextIdValue).length();
        for (int i = 0; i < needLen; i++) {
            retStr.append("0");
        }
        return retStr + String.valueOf(nextIdValue);
    }


    /**
     * 完善前缀和后缀
     *
     * @param isAffix  是否有前缀或者后缀 1有 0无
     * @param affix    前缀或者后缀内容
     * @param dateTime 当前时间
     *                 特别说明如下：
     *                 <p>假设当前时间为2019年2月25日3时11分23秒，如果前缀或后缀包含下列字符串</p>
     *                 <p>yyyy：生成的流水号将该字符串替换为2019</p>
     *                 <p>yy：生成的流水号将该字符串替换为19</p>
     *                 <p>MM：生成的流水号将该字符串替换为02</p>
     *                 <p>dd：生成的流水号将该字符串替换为25</p>
     *                 <p>HH：生成的流水号将该字符串替换为03</p>
     *                 <p>mm：生成的流水号将该字符串替换为11</p>
     *                 <p>ss：生成的流水号将该字符串替换为23</p>
     *                 <p>以上日期时间字符，yyyyMMddHHmmss，区分大小写</p>
     * @return
     */
    private static String compoundAffix(String isAffix, String affix, Date dateTime) {
        if (StringUtils.isNotBlank(isAffix) && isAffix.equals("1")) {
            if (StringUtils.isNotBlank(affix)) {
                affix = affix.trim();
                for (String format : AFFIX_FORMAT) {
                    affix = affix.replace(format, formatDate(dateTime, format));
                }
                return affix;
            }
        }
        return "";
    }


    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (date != null) {
            if (StringUtils.isBlank(pattern)) {
                pattern = "yyyy-MM-dd";
            }
            formatDate = FastDateFormat.getInstance(pattern).format(date);
        }
        return formatDate;
    }
}
