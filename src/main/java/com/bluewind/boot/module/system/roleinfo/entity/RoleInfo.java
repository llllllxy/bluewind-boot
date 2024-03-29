package com.bluewind.boot.module.system.roleinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:25
 * @description 角色信息表
 **/
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = -394893622434797995L;

    private String roleId;

    private String name;

    private String sign;

    private String status;

    private String descript;

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public String getStatus() {
        return status;
    }

    public String getDescript() {
        return descript;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    private String createUser;

    public void setName(String name) {
        this.name = name;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    private String updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String delFlag;

    @Override
    public String toString() {
        return "SysRoleInfo{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", sign='" + sign + '\'' +
                ", status=" + status + '\'' +
                ", descript='" + descript + '\'' +
                ", createUser=" + createUser +
                ", updateUser=" + updateUser +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag + '\'' +
                '}';
    }

}
