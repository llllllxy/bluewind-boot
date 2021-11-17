package com.bluewind.boot.module.sys.sysoperlog.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-03-05-13:35
 **/
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = -4516808988241959991L;

    private String logId;

    private String model;

    private String url;

    private String method;

    private String descript;

    private String ip;

    private String type;

    private Integer spendTime;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysOperLog{" +
                "logId=" + logId +
                ", model='" + model + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", descript='" + descript + '\'' +
                ", ip='" + ip + '\'' +
                ", type='" + type + '\'' +
                ", spendTime=" + spendTime +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                '}';
    }
}
