package com.liuxingyu.meco.configuration.starttask;

import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.mybatis.MybatisSqlTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-29-0:00
 * @description 定义系统启动任务，用来在系统启动时将id_table信息上传至redis
 **/
@Component
@Order(101)
public class IdTableCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select * from sys_id_table";
        List<Map> result = MybatisSqlTool.selectAnySql(sql);
        result.forEach(item -> {
            String id_id = (String) item.get("id_id");
            redisUtil.set(id_id, item, -1);
        });
    }
}
