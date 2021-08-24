package com.liuxingyu.meco.common.scheduling;

import com.liuxingyu.meco.common.utils.DateTool;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.mybatis.MybatisSqlTool;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-29-0:21
 * @description 用来同步 id_table 在redis和mysql中的数据
 **/
@Component //使用spring管理
public class IdTableTimer {
    final static Logger logger = LoggerFactory.getLogger(IdTableTimer.class);

    @Autowired
    RedisUtil redisUtil;

    /**
     * @Schedule参数解释
     * fixedRate 表示任务执行之间的时间间隔，具体是指两次任务的开始时间间隔，即第二次任务开始时，第一次任务可能还没结束。
     * fixedDelay 表示任务执行之间的时间间隔，具体是指本次任务结束到下次任务开始之间的时间间隔。
     * initialDelay 表示首次任务启动的延迟时间。
     * 所有时间的单位都是毫秒。
     *
     * @SchedulerLock参数解释
     * name属性：必须指定，ShedLock保证具有相同name的定时任务同一时刻仅执行一次；
     * lockAtMostFor属性：任务获得锁后的最长持有时间；在正常情况下，任务执行完毕后会立即释放锁，这里的时间设置防止程序无法正常释放锁导致死锁。
     * 此外，lockAtMostFor设置的时间务必大于任务的执行时间，否则可能存在多个线程持有该锁，不能保证任务执行结果的正确性。
     * 如果未在@SchedulerLock中指定lockAtMostFor，则将使用@EnableSchedulerLock中的默认值。
     * lockAtLeastFor属性：任务获取锁后最短持有时间；在任务执行时间很短且节点之间的时钟不同步的情况下，该属性阻止任务在多个节点执行。
     */
    @Scheduled(initialDelay = 50000, fixedDelay = 50000)
    @SchedulerLock(name = "shortRunningTask", lockAtMostFor = "5s", lockAtLeastFor = "2s")
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
