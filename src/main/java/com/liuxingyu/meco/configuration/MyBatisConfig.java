package com.liuxingyu.meco.configuration;

import java.io.IOException;
import java.util.*;
import javax.sql.DataSource;

import com.liuxingyu.meco.common.utils.db.DbTypeUtil;
import com.liuxingyu.meco.common.utils.lang.StringUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * @author liuxingyu01
 * @date 2021-07-03-9:00
 * @description Mybatis支持*匹配扫描包
 **/
@Configuration
@MapperScan(basePackages = "com.liuxingyu.meco.**.mapper")
public class MyBatisConfig {
    static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Autowired
    private Environment env;

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";


    public static String setTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<String> allResult = new ArrayList<String>();
        try {
            for (String aliasesPackage : typeAliasesPackage.split(",")) {
                List<String> result = new ArrayList<String>();
                aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/" + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(aliasesPackage);
                if (resources != null && resources.length > 0) {
                    MetadataReader metadataReader = null;
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            metadataReader = metadataReaderFactory.getMetadataReader(resource);
                            try {
                                result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (result.size() > 0) {
                    HashSet<String> hashResult = new HashSet<String>(result);
                    allResult.addAll(hashResult);
                }
            }
            if (allResult.size() > 0) {
                typeAliasesPackage = String.join(",", (String[]) allResult.toArray(new String[0]));
            } else {
                throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:" + typeAliasesPackage + "未找到任何包");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return typeAliasesPackage;
    }

    public Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // 可以从Environment中获取到所有的在application.yml和application.properties中配置的参数
        String mapperLocations = env.getProperty("mybatis.mapper-locations");
        String typeAliasesPackage = env.getProperty("mybatis.type-aliases-package");

        typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
        VFS.addImplClass(SpringBootVFS.class);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true); // 全局映射器启用缓存
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换，这个只会对javabean有作用，而不会影响到Map值返回
        // configuration.setUseGeneratedKeys(true); // 允许 JDBC 支持自动生成主键，这个先不设置吧，可能会对现有程序造成影响
        configuration.setDefaultExecutorType(ExecutorType.REUSE); // 配置默认的执行器
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class); // 指定 MyBatis 所用日志的具体实现

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // mybatis中的typeAliasesPackage是为了配置xml文件中resultType返回值的包位置，如果未配置请使用全包名
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(resolveMapperLocations(StringUtils.split(mapperLocations, ",")));
        sessionFactory.setConfiguration(configuration);

        // 注入mybatis全局参数dbType，用来在sql中判断数据库，使用方法如下，用if标签套着
        // <if test="'${dbType}' != null and '${dbType}' == 'oracle'">
        //     1=1
        // </if>
        Properties sqlSessionFactoryProperties = new Properties();
        String dbType = DbTypeUtil.getDbTypeByDataSource(dataSource).toString();
        logger.info("MyBatisConfig -- sqlSessionFactory -- 注入全局参数 -- dbType = {}", dbType);
        sqlSessionFactoryProperties.setProperty("dbType", dbType);

        sessionFactory.setConfigurationProperties(sqlSessionFactoryProperties);

        return sessionFactory.getObject();
    }
}
