package com.liuxingyu.meco.common.aspect;

import com.liuxingyu.meco.common.annotation.DataSourceWith;
import com.liuxingyu.meco.configuration.datasource.RoutingDataSourceContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author liuxingyu01
 * @date 2021-03-10-10:42
 * @description 数据源切换切面处理类
 **/
@Aspect
@Component
public class DataSourceAspect {
    Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("@annotation(dataSourceWith)")
    public Object routingWithDataSource(ProceedingJoinPoint joinPoint, DataSourceWith dataSourceWith) throws Throwable {
        String key = dataSourceWith.value().name();
        if (StringUtils.isNotBlank(key)) {
            logger.info("DataSourceAspect - 切换到{}数据源", key);
            RoutingDataSourceContext.setRoutingDataSource(key);
        }
        try {
            return joinPoint.proceed();
        } finally { // 在执行方法之后
            // 销毁数据源(保证主数据源的准确性)
            RoutingDataSourceContext.remove();
            logger.info("DataSourceAspect - {}数据源销毁成功", key);
        }
    }
}
