package com.liuxingyu.meco.configuration.starttask;

import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.lang.StringUtils;
import com.liuxingyu.meco.common.utils.mybatis.MybatisSqlTool;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author liuxingyu01
 * @date 2021-06-12-11:22
 * @description 定义系统启动任务，用来在系统启动时将rest_key信息上传至redis
 **/
@Component
@Order(102)
public class ItfcKeyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select itfc_key,valid_period from sys_itfc_key where status = 0 and del_flag = 0";
        List<Map> result = MybatisSqlTool.selectAnySql(sql);
        result.forEach(item -> {
            String itfc_key = (String) item.get("itfc_key");
            // 获取有效期，把有效期内的也放进redis里
            String valid_period = item.get("valid_period") == null ? "" : (String) item.get("valid_period");
            // 如果有效期为空，则设置为永久，到2050年
            if (StringUtils.isBlank(valid_period)) {
                valid_period = "20500625";
            }

            StringBuffer sb = new StringBuffer();
            sb.append("select srp.sign from sys_itfc_permission srp ");
            sb.append("left join sys_itfc_key_permission srkp on srkp.itfc_permission = srp.permission_id ");
            sb.append("where srkp.itfc_key = '").append(itfc_key).append("'");
            List<Map> permissionList = MybatisSqlTool.selectAnySql(sb.toString());
            Set<String> set = new HashSet<>();
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (Map map : permissionList) {
                    String sign = Optional.ofNullable(map.get("sign")).orElse("").toString();
                    if (StringUtils.isNotBlank(sign)) {
                        set.add(sign);
                    }
                }
                redisUtil.set("itfcKey:" + itfc_key, set, -1);
                redisUtil.set("itfcPeriod:" + itfc_key, valid_period, -1);
            } else {
                redisUtil.set("itfcKey:" + itfc_key, set, -1);
                redisUtil.set("itfcPeriod:" + itfc_key, valid_period, -1);
            }
        });
    }
}
