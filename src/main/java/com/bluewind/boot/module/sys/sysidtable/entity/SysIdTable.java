package com.bluewind.boot.module.sys.sysidtable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:48
 **/
public class SysIdTable implements Serializable {
    private static final long serialVersionUID = 8766108666045790590L;

    private String idId;

    private String idCode;

    private String idName;

    private Integer idValue;

    private Integer idLength;

    private String hasPrefix;

    private String idPrefix;

    private String hasSuffix;

    private String idSuffix;

    private String descript;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    private String createUser;

    private String updateUser;

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getIdId() {
        return idId;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setIdId(String idId) {
        this.idId = idId;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Integer getIdValue() {
        return idValue;
    }

    public void setIdValue(Integer idValue) {
        this.idValue = idValue;
    }

    public Integer getIdLength() {
        return idLength;
    }

    public void setIdLength(Integer idLength) {
        this.idLength = idLength;
    }

    public String getHasPrefix() {
        return hasPrefix;
    }

    public void setHasPrefix(String hasPrefix) {
        this.hasPrefix = hasPrefix;
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public String getHasSuffix() {
        return hasSuffix;
    }

    public void setHasSuffix(String hasSuffix) {
        this.hasSuffix = hasSuffix;
    }

    public String getIdSuffix() {
        return idSuffix;
    }

    public void setIdSuffix(String idSuffix) {
        this.idSuffix = idSuffix;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "SysIdTable{" +
                "idCode=" + idCode +
                ", idId='" + idId + '\'' +
                ", idName='" + idName + '\'' +
                ", idValue=" + idValue +
                ", idLength=" + idLength +
                ", hasPrefix='" + hasPrefix + '\'' +
                ", idPrefix='" + idPrefix + '\'' +
                ", hasSuffix='" + hasSuffix + '\'' +
                ", idSuffix='" + idSuffix + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createUser=" + createUser +
                ", updateUser=" + updateUser +
                '}';
    }
}
