package com.liuxingyu.meco.common.utils.idtable;

import com.liuxingyu.meco.common.utils.DateTool;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.mybatis.MybatisSqlTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-29-0:21
 * @description 用来同步 id_table 在redis和mysql中的数据
 **/
@Component //使spring管理
@EnableScheduling //定时任务注解
public class IdTableTimer {
    final static Logger logger = LoggerFactory.getLogger(IdTableTimer.class);

    @Autowired
    RedisUtil redisUtil;

    /**
     * fixedRate 表示任务执行之间的时间间隔，具体是指两次任务的开始时间间隔，即第二次任务开始时，第一次任务可能还没结束。
     * fixedDelay 表示任务执行之间的时间间隔，具体是指本次任务结束到下次任务开始之间的时间间隔。
     * initialDelay 表示首次任务启动的延迟时间。
     * 所有时间的单位都是毫秒。
     */
    @Scheduled(initialDelay = 50000, fixedDelay = 50000)
    public void uploadIdTables() {
        if (logger.isInfoEnabled()) {
            logger.info("IdTableTimer - 开始同步id_table数据 -- 从redis同步到mysql " + DateTool.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        }

        String sql = "select * from sys_id_table";
        List<Map> result = MybatisSqlTool.selectAnySql(sql);
        result.forEach(item -> {
            String id_id = (String) item.get("id_id");
            Map map = (Map) redisUtil.get(id_id);
            int id_value = map.get("id_value") == null ? 0 : Integer.parseInt(map.get("id_value").toString());
            String sql2 = "update sys_id_table set id_value = " + id_value + " where id_id = '" + id_id + "'";
            MybatisSqlTool.updateAnySql(sql2);
        });
    }
}
