package com.bluewind.boot.common.utils.mybatis.service;


import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-21-16:14
 **/
public interface MybatisSqlToolService {

    /**
     * 通用查询
     *
     * @return
     */
    List<Map<String, Object>> commonSelect(String value);

    /**
     * 通用插入
     *
     * @param value
     * @return
     */
    int commonInsert(String value);

    /**
     * 通用更新
     *
     * @param value
     * @return
     */
    int commonUpdate(String value);

    /**
     * 通用删除
     *
     * @param value
     * @return
     */
    int commonDelete(String value);

}
