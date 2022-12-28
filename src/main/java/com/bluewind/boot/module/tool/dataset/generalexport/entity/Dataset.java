package com.bluewind.boot.module.tool.dataset.generalexport.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2022-11-24 22:02
 * @description
 **/
public class Dataset implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 数据集id(主键id)
     */
    private String dataId;

    /**
     * 数据集名称
     */
    private String dataTitle;

    /**
     * 是否可用， 0:否;1:是
     */
    private String isMrb;

    /**
     * 备注
     */
    private String note;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getIsMrb() {
        return isMrb;
    }

    public void setIsMrb(String isMrb) {
        this.isMrb = isMrb;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", dataId='" + dataId + '\'' +
                ", dataTitle='" + dataTitle + '\'' +
                ", isMrb='" + isMrb + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

