package com.liuxingyu.meco.common.utils.io;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 资源供给类
 * @author liuxingyu01
 * @date 2020-09-12-21:19
 **/
public class ResourceUtil extends org.springframework.util.ResourceUtils{
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * 获取资源加载器（可读取jar内的文件）
     * @author ThinkGem
     */
    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * 获取ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return resourceLoader.getClassLoader();
    }

    /**
     * 获取资源加载器（可读取jar内的文件）
     */
    public static Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }

    /**
     * 获取资源文件流（用后记得关闭）
     * @param location
     * @author ThinkGem
     * @throws IOException
     */
    public static InputStream getResourceFileStream(String location) throws IOException{
        Resource resource = resourceLoader.getResource(location);
        return resource.getInputStream();
    }

    /**
     * 获取资源文件内容
     * @param location
     * @author ThinkGem
     */
    public static String getResourceFileContent(String location){
        InputStream is = null;
        try{
            is = ResourceUtil.getResourceFileStream(location);
            return IOUtils.toString(is, "UTF-8");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Spring 搜索资源文件
     * @param locationPattern
     * @author ThinkGem
     */
    public static Resource[] getResources(String locationPattern){
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(locationPattern);
            return resources;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
