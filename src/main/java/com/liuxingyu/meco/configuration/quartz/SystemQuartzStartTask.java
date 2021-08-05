package com.liuxingyu.meco.configuration.quartz;


import com.liuxingyu.meco.sys.sysquartz.entity.SysQuartz;
import com.liuxingyu.meco.sys.sysquartz.service.SysQuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目启动任务--启动定时任务
 * 项目启动后，自动执行状态正常的定时任务
 * @author liuxingyu01
 * @date 2021-01-17-22:23
 **/
@Component
@Order(100)
public class SystemQuartzStartTask implements CommandLineRunner {
    final static Logger log = LoggerFactory.getLogger(SystemQuartzStartTask.class);

    @Autowired
    private SysQuartzService sysQuartzService;

    @Override
    public void run(String... args) throws Exception {
        // 查询定时任务
        SysQuartz sysQuartz = new SysQuartz();
        sysQuartz.setStatus(0);
        sysQuartz.setQuartzStatus(0);

        List<SysQuartz> list = sysQuartzService.getDefaultList(sysQuartz);
        if (log.isInfoEnabled()) {
            log.info("SystemQuartzStartTask -- run -- list = {}", list);
        }

        if (null != list && !list.isEmpty()) {
            for (SysQuartz item : list) {
                // 删除定时任务
                sysQuartzService.schedulerDelete(item.getClassName().trim());
                // 添加定时任务
                sysQuartzService.schedulerAdd(item.getClassName().trim(), item.getCronExpression().trim(), item.getParam());
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info("SystemQuartzStartTask -- run -- 无启动状态的定时器可执行!");
            }
        }
    }



}
