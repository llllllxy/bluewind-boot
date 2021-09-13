package com.bluewind.boot.module.sys.sysuserinfo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:15
 **/
public class SysUserInfo implements Serializable {
    private static final long serialVersionUID = 6460626203515194126L;

    private Integer id;

    private String account;

    private String password;

    private String name;

    private String phone;

    private String avatar;

    private Integer sex;

    private Integer status;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    private Integer delFlag;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return "SysUserInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", status=" + status +
                ", createUser=" + createUser +
                ", createTime='" + createTime + '\'' +
                ", updateUser=" + updateUser +
                ", updateTime='" + updateTime + '\'' +
                ", delFlag=" + delFlag +
                '}';
    }
}
