package com.bluewind.boot.common.utils.fileupload.api;

import com.bluewind.boot.common.utils.fileupload.model.StorageFile;
import com.bluewind.boot.common.utils.fileupload.model.StorageStreamFile;

import java.io.InputStream;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-04-22 14:36
 * @description 存储层接口
 **/
public interface StorageService {

    /**
     * 根据文件id获取文件
     *
     * @param fileId 文件ID
     * @return
     */
    StorageStreamFile findById(String fileId);

    /**
     * 根据文件id获取照片，同时裁剪照片
     * @param fileId 文件ID
     * @param width 高度
     * @param height 宽度
     * @return
     */
    // StorageStreamFile findById(String fileId, int width, int height);


    /**
     * 上传文件
     * @param is 文件输入流
     * @param filename 文件名
     * @return
     */
    StorageFile store(InputStream is, String filename);

    /**
     * 上传文件
     * @param is 文件输入流
     * @param filename 文件名
     * @param contentType 类型
     * @return
     */
    StorageFile store(InputStream is, String filename, String contentType);

    /**
     * 上传文件
     * @param is 文件输入流
     * @param filename 文件名
     * @param contentType 类型
     * @param metaData 头信息
     * @return
     */
    StorageFile store(InputStream is, String filename, String contentType, Map<String, String> metaData);


    /**
     * 上传文件
     * @param is 文件输入流
     * @param filename 文件名
     * @param metaData 头信息
     * @return
     */
    StorageFile store(InputStream is, String filename, Map<String, String> metaData);


    /**
     * 根据文件id删除文件
     *
     * @param fileId 文件ID
     * @return void
     */
    void deleteById(String fileId);

    /**
     * 根据文件id，获取临时文件访问url
     *
     * @param fileId  文件名称
     * @param expires 多久后过期（单位小时）
     * @return url
     */
    String getExpiryUrlById(String fileId, Integer expires);


    /**
     * 根据文件id，获取临时文件访问url（默认为1小时）
     *
     * @param fileId  文件名称
     * @return url
     */
    String getExpiryUrlById(String fileId);

}
