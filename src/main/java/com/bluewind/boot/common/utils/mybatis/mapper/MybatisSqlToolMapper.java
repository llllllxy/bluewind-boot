package com.bluewind.boot.common.utils.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-05-24-19:02
 **/
@Repository
public interface MybatisSqlToolMapper {

    /**
     * 通用查询
     *
     * @return
     */
    List<Map> commonSelect(@Param("value") String value);

    /**
     * 通用插入
     *
     * @param value
     * @return
     */
    int commonInsert(@Param("value") String value);

    /**
     * 通用更新
     *
     * @param value
     * @return
     */
    int commonUpdate(@Param("value") String value);

    /**
     * 通用删除
     *
     * @param value
     * @return
     */
    int commonDelete(@Param("value") String value);

}
