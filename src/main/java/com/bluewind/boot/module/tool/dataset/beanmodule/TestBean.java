package com.bluewind.boot.module.tool.dataset.beanmodule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-11-25 15:30
 * @description 测试bean
 **/
@Component("testBean")
public class TestBean implements IBaseBean {

    final static Logger log = LoggerFactory.getLogger(TestBean.class);

    /**
     * 执行bean - 带参
     *
     * @return 执行bean后返回的结果信息
     */
    @Override
    public Map<String, Object> execute(Map<String, Object> parmMap) {
        log.debug("TestBean -- execute -- parmMap : {}", parmMap);

        return new HashMap<String, Object>() {{
            put("is_mrb", "1");
            put("other", "测试bean成功");
        }};
    }
}
