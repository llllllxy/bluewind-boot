package com.liuxingyu.meco.common.utils.mybatis.service;

import com.liuxingyu.meco.common.utils.mybatis.mapper.MybatisSqlToolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-21-16:15
 **/
@Service
public class MybatisSqlToolServiceImpl implements MybatisSqlToolService {

    @Autowired
    private MybatisSqlToolMapper mybatisSqlToolMapper;

    /**
     * 通用查询
     *
     * @return
     */
    public List<Map> commonSelect(String value) {
        return mybatisSqlToolMapper.commonSelect(value);
    }

    /**
     * 通用插入
     *
     * @param value
     * @return
     */
    public int commonInsert(String value) {
        return mybatisSqlToolMapper.commonInsert(value);
    }

    /**
     * 通用更新
     *
     * @param value
     * @return
     */
    public int commonUpdate(String value) {
        return mybatisSqlToolMapper.commonUpdate(value);
    }

    /**
     * 通用删除
     *
     * @param value
     * @return
     */
    public int commonDelete(String value) {
        return mybatisSqlToolMapper.commonDelete(value);
    }
}
