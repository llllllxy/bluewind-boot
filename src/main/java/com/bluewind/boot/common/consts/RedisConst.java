package com.bluewind.boot.common.consts;

/**
 * @author liuxingyu01
 * @date 2021-02-21-14:37
 * @description redis常量类
 **/
public final class RedisConst {
    /**
     * Redis 键
     */
    public static final class Key {
        /**
         * 系统信息缓存
         */
        public static final String SYS_CONFIGURE = "sys:configure";

        /**
         * 服务监控信息
         */
        public static final String SYS_SERVER_INFO = "sys:server_info";

        /**
         * 字典信息缓存
         */
        public static final String SYS_DICT = "dict:";
    }

    /**
     * Redis 通道
     */
    public static final class Channel {

    }
}
