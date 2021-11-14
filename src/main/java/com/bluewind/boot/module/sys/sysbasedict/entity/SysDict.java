package com.bluewind.boot.module.sys.sysbasedict.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-02-22-21:58
 **/
public class SysDict {

    private String id;

    private String dictCode;

    private String name;

    private String descript;

    private String status;

    private String delFlag;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public String getName() {
        return name;
    }

    public String getDescript() {
        return descript;
    }

    public String getStatus() {
        return status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return "SysDict{" +
                "id=" + id +
                ", dictCode='" + dictCode + '\'' +
                ", name='" + name + '\'' +
                ", descript='" + descript + '\'' +
                ", status=" + status + '\'' +
                ", delFlag=" + delFlag + '\'' +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }
}
