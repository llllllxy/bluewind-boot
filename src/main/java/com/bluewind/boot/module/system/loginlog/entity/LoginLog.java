package com.bluewind.boot.module.system.loginlog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-02-18-12:49
 **/
public class LoginLog implements Serializable {
    private static final long serialVersionUID = -8623198361955748757L;

    private String logId;

    private String sessionId;

    private String account;

    private String ip;

    private String descript;

    private String location;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    public String getLogId() {
        return logId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getAccount() {
        return account;
    }

    public String getIp() {
        return ip;
    }

    public String getDescript() {
        return descript;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysLoginLog{" +
                "logId=" + logId +
                ", sessionId='" + sessionId + '\'' +
                ", account='" + account + '\'' +
                ", ip='" + ip + '\'' +
                ", descript='" + descript + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
