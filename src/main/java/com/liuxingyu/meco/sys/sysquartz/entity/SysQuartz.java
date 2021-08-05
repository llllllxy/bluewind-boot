package com.liuxingyu.meco.sys.sysquartz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-01-17-22:28
 * @description 定时器实体类
 **/
public class SysQuartz implements Serializable {
    private static final long serialVersionUID = 8946468637774294266L;

    private Integer id;

    private String name;

    private String className;

    private String cronExpression;

    private String param;

    private String descript;

    private Integer quartzStatus;

    private Integer status;

    private Integer delFlag;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getQuartzStatus() {
        return quartzStatus;
    }

    public void setQuartzStatus(Integer quartzStatus) {
        this.quartzStatus = quartzStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysQuartz{" +
                "id=" + id +
                "name = " + name +
                ", className='" + className + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", param='" + param + '\'' +
                ", descript='" + descript + '\'' +
                ", quartzStatus=" + quartzStatus +
                ", status=" + status +
                ", delFlag=" + delFlag +
                ", createUser=" + createUser +
                ", createTime='" + createTime + '\'' +
                ", updateUser=" + updateUser +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
