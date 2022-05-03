package com.bluewind.boot.common.utils.storage.util;

import com.bluewind.boot.common.utils.spring.SpringUtil;
import com.bluewind.boot.common.utils.storage.api.StorageService;
import com.bluewind.boot.common.utils.storage.model.StorageFile;
import com.bluewind.boot.common.utils.storage.model.StorageStreamFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-05-03 14:48
 * @description 通用文件上传静态工具类
 **/
public class StorageUtil {
    private static final Logger logger = LoggerFactory.getLogger(StorageUtil.class);

    private static StorageService storageService;

    private static StorageService getStorageService() {
        if (storageService == null) {
            storageService = SpringUtil.getBean(StorageService.class);
        }
        return storageService;
    }


    /**
     * 上传文件
     *
     * @param targetFile
     * @return
     */
    public static String uploadFile(File targetFile) {
        try {
            InputStream input = new FileInputStream(targetFile);
            String fileName = targetFile.getName();
            StorageFile storageFile = getStorageService().store(input, fileName);
            return storageFile.getFileId();
        } catch (Exception e) {
            logger.error("StorageUtil - 上传文件失败", e);
        }

        return null;
    }


    /**
     * 上传文件
     *
     * @param targetFile
     * @param param
     * @return
     */
    public static String uploadFile(File targetFile, Map<String, String> param) {
        try {
            InputStream input = new FileInputStream(targetFile);
            String fileName = targetFile.getName();
            StorageFile storageFile = getStorageService().store(input, fileName, param);
            return storageFile.getFileId();
        } catch (Exception e) {
            logger.error("StorageUtil - 上传文件失败", e);
        }

        return null;
    }



    /**
     * 上传文件
     *
     * @return
     */
    public static String uploadFile(InputStream inputStream, String fileName) {
        try {
            StorageFile storageFile = getStorageService().store(inputStream, fileName);
            return storageFile.getFileId();
        } catch (Exception e) {
            logger.error("StorageUtil - 上传文件失败", e);
        }

        return null;
    }

    /**
     * 上传文件
     *
     * @return
     */
    public static String uploadFile(InputStream inputStream, String fileName, Map<String, String> metaData) {
        try {
            StorageFile storageFile = getStorageService().store(inputStream, fileName, metaData);
            return storageFile.getFileId();
        } catch (Exception e) {
            logger.error("StorageUtil - 上传文件失败", e);
        }

        return null;
    }


    /**
     * 删除文件
     *
     * @return
     */
    public static String deleteFile(String fileId) {
        try {
            getStorageService().deleteById(fileId);
            return fileId;
        } catch (Exception e) {
            logger.error("StorageUtil - 读取文件失败", e);
        }

        return null;
    }


    /**
     * 根据文件id返回文件读入流
     *
     * @param fileId
     * @return
     */
    public static byte[] getFileByteById(String fileId) {
        StorageStreamFile storageStreamFile = getStorageService().findById(fileId);
        if (storageStreamFile == null) {
            return null;
        }
        try {
            // 无法判断IOUtils.toByteArray会不会自动帮我关闭流，所以在finally里手动关了一遍（关闭多次也不会报错）
            return IOUtils.toByteArray(storageStreamFile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("StorageUtil - 读取文件失败", e);
        } finally {
            IOUtils.closeQuietly(storageStreamFile.getInputStream());
        }
        return null;
    }


    /**
     * 根据文件id返回文件读入流
     *
     * @param fileId
     * @return
     */
    public static InputStream getFileInputStreamById(String fileId) {
        try {
            StorageStreamFile storageStreamFile = getStorageService().findById(fileId);
            return storageStreamFile.getInputStream();
        } catch (Exception e) {
            logger.error("StorageUtil - 读取文件失败", e);
        }
        return null;
    }


    /**
     * 根据文件id返回文件存储对象
     *
     * @param fileId
     * @return
     */
    public static StorageStreamFile getStorageStreamFileById(String fileId) {
        try {
            return getStorageService().findById(fileId);
        } catch (Exception e) {
            logger.error("StorageUtil - 读取文件失败", e);
        }
        return null;
    }

}
