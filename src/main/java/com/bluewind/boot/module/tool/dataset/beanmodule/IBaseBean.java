package com.bluewind.boot.module.tool.dataset.beanmodule;

import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-11-23 10:49
 * @description bean基础接口，所有的bean要实现此接口
 **/
public interface IBaseBean {
    /**
     * 执行bean - 带参
     *
     * @return 执行bean后返回的结果信息
     */
    Map<String, Object> execute(Map<String, Object> paramMap);
}
