package com.liuxingyu.meco;

import com.liuxingyu.meco.common.utils.db.DbTypeUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

// 将spring boot自带的DataSourceAutoConfiguration禁掉，
// 因为它会读取application.yml文件的spring.datasource.*属性并自动配置单数据源

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement // 简单开启事务管理
@MapperScan(basePackages = "com.liuxingyu.meco.**.mapper")
public class MecoApplication {
    final static Logger logger = LoggerFactory.getLogger(MecoApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MecoApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        logger.info("\n----------------------------------------------------------\n\t" +
                "MecoApplication started successfully! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger-UI: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                "DbType: \t\t" + DbTypeUtil.getDbType() + "\n" +
                "----------------------------------------------------------");


        // 打印JAVA环境变量
//        Properties properties = System.getProperties();
//        Set<Object> keys = properties.keySet();
//        for (Object key : keys) {
//            if (key instanceof String) {
//                logger.info("系统参数 --> key :" + key + "  value  :" + System.getProperty((String) key));
//            }
//        }
    }

}
