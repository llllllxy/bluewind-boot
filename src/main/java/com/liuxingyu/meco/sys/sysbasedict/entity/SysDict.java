package com.liuxingyu.meco.sys.sysbasedict.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-02-22-21:58
 **/
public class SysDict {

    private Integer id;

    private String dictCode;

    private String name;

    private String descript;

    private Integer status;

    private Integer delFlag;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public void setId(Integer id) {
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDelFlag(Integer delFlag) {
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

    public Integer getId() {
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

    public Integer getStatus() {
        return status;
    }

    public Integer getDelFlag() {
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
                ", status=" + status +
                ", delFlag=" + delFlag +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }
}
