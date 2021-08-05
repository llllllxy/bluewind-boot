package com.liuxingyu.meco.common.utils.idgen;

import org.apache.commons.lang3.StringUtils;


/**
 * @author liuxingyu01
 * @date 2021-02-02-18:41
 * @description 封装各种生成唯一性ID算法的工具类.
 **/
public class IdGenerate {
    private static final IdWorker idWorker = IdWorker.getInstanceIdWorker();

    public IdGenerate() {
    }

    /**
     * 获取普通uuid（小写不带横线）
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取普通UUID（大写不带横线）
     * @return
     */
    public static String UUID() {
        return UUID.randomUUID().toString(true).toUpperCase();
    }

    /**
     * 获取高性能uuid，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 小写的UUID，去掉了横线
     */
    public static String fastuuid() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 获取高性能UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 大写的UUID，去掉了横线
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString(true);
    }

    public static String getInnerIdByLength(int length) {
        UUIDGenerator uuid = new UUIDGenerator();
        return uuid.getNextSeqId(length).toString();
    }


    /**
     * 获取雪花算法id
     *
     * @return String
     */
    public static String nextId() {
        return String.valueOf(idWorker.nextId());
    }

    /**
     * 获取雪花算法id
     *
     * @return long
     */
    public static long nextLongId() {
        return idWorker.nextId();
    }

    public static String nextCode(String code) {
        if (code == null) {
            return null;
        } else {
            String str = code.trim();
            int len = str.length() - 1;
            int lastNotNumIndex = 0;
            for (int i = len; i >= 0; --i) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    lastNotNumIndex = i;
                    break;
                }
            }
            if (str.charAt(len) >= '0' && str.charAt(len) <= '9' && lastNotNumIndex == len) {
                lastNotNumIndex = -1;
            }
            String prefix = str.substring(0, lastNotNumIndex + 1);
            String numStr = str.substring(lastNotNumIndex + 1, str.length());

            long num = Long.parseLong(numStr);
            str = prefix + StringUtils.leftPad(String.valueOf(num + 1L), numStr.length(), "0");
            return str;
        }
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            for (int i = 0, len = 10000; i < len; i++) {
                System.out.println("nextId= " + nextId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("nextId - 10000耗时: " + (System.currentTimeMillis() - start) + "ms");
    }

}
