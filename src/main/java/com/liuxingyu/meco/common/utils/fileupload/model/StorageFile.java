package com.liuxingyu.meco.common.utils.fileupload.model;

import java.util.Date;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-05-16-9:57
 * @description 存储对象
 **/
public class StorageFile {
    private String fileId;
    private String fileName;
    private String contentType;
    private String md5;
    private long length;
    private Date uploadDate;
    private Map<String, String> metaData;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }


}
