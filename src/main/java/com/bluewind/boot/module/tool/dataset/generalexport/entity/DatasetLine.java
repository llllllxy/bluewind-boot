package com.bluewind.boot.module.tool.dataset.generalexport.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2022-11-24 22:04
 * @description
 **/
public class DatasetLine implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 数据集id
     */
    private String dataId;

    /**
     * 子数据行号
     */
    private Integer lineNo;

    /**
     * 子数据行名称
     */
    private String lineName;

    /**
     *  子数据行说明
     */
    private String lineNote;

    /**
     *  数据格式 0:值; 1:对象; 2:列表
     */
    private String dataPattern;

    /**
     *  数据来源 0:sql; 1:bean
     */
    private String dataSource;

    /**
     *  存储spring bean id
     */
    private String beanInfo;

    /**
     *  存储SQL查询语句
     */
    private String sqlInfo;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineNote() {
        return lineNote;
    }

    public void setLineNote(String lineNote) {
        this.lineNote = lineNote;
    }

    public String getDataPattern() {
        return dataPattern;
    }

    public void setDataPattern(String dataPattern) {
        this.dataPattern = dataPattern;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getBeanInfo() {
        return beanInfo;
    }

    public void setBeanInfo(String beanInfo) {
        this.beanInfo = beanInfo;
    }

    public String getSqlInfo() {
        return sqlInfo;
    }

    public void setSqlInfo(String sqlInfo) {
        this.sqlInfo = sqlInfo;
    }

    @Override
    public String toString() {
        return "DatasetLine{" +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", dataId='" + dataId + '\'' +
                ", lineNo=" + lineNo +
                ", lineName='" + lineName + '\'' +
                ", lineNote='" + lineNote + '\'' +
                ", dataPattern='" + dataPattern + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", beanInfo='" + beanInfo + '\'' +
                ", sqlInfo='" + sqlInfo + '\'' +
                '}';
    }
}

