package com.bluewind.boot.module.system.itfckey.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-06-12-16:55
 * @description itfc服务key实体类
 **/
public class ItfcKey implements Serializable {
    private static final long serialVersionUID = 1735583407421553598L;

    private String itfcId;

    private String status;

    private String delFlag;

    private String itfcKey;

    private String itfcKeySecret;

    private String owner;

    private String validPeriod;

    private String descript;

    private String createUser;

    private String updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    public String getItfcId() {
        return itfcId;
    }

    public void setItfcId(String itfcId) {
        this.itfcId = itfcId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getItfcKey() {
        return itfcKey;
    }

    public void setItfcKey(String itfcKey) {
        this.itfcKey = itfcKey;
    }


    public String getItfcKeySecret() {
        return itfcKeySecret;
    }

    public void setItfcKeySecret(String itfcKeySecret) {
        this.itfcKeySecret = itfcKeySecret;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
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

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }


    @Override
    public String toString() {
        return "SysRestKey{" +
                "itfcId=" + itfcId +
                ", status=" + status + '\'' +
                ", delFlag=" + delFlag + '\'' +
                ", itfcKey='" + itfcKey + '\'' +
                ", itfcKeySecret='" + itfcKeySecret + '\'' +
                ", owner='" + owner + '\'' +
                ", validPeriod='" + validPeriod + '\'' +
                ", descript='" + descript + '\'' +
                ", createUser=" + createUser +
                ", updateUser=" + updateUser +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
