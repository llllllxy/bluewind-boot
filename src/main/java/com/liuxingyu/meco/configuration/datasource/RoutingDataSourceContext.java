package com.liuxingyu.meco.configuration.datasource;

/**
 * @author liuxingyu01
 * @date 2021-03-10-8:32
 * @description 动态数据源上下文管理：设置数据源，获取数据源，清除数据源
 *
 * 使用方法：在执行数据库操作前加上
 *          RoutingDataSourceContext routingDataSourceContext = new RoutingDataSourceContext("slaveDataSource");
 *          执行完操作后记得关闭数据源，routingDataSourceContext.close();
 **/
public class RoutingDataSourceContext {
    // holds data source key in thread local:
    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    static final ThreadLocal<String> threadLocalDataSourceKey = new ThreadLocal<>();

    /**
     * 获得数据源的变量
     *
     * @return
     */
    public static String getDataSourceRoutingKey() {
        String key = threadLocalDataSourceKey.get();
        return key == null ? "masterDataSource" : key;
    }

    /**
     * 设置数据源
     *
     * @param key
     */
    public static void setRoutingDataSource(String key) {
        threadLocalDataSourceKey.set(key);
    }

    /**
     * 清除数据源
     */
    public static void remove() {
        threadLocalDataSourceKey.remove();
    }

}
