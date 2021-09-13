package com.bluewind.boot.sys.sysroleinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:25
 * @description 角色信息表
 **/
public class SysRoleInfo implements Serializable {
    private static final long serialVersionUID = -394893622434797995L;

    private Integer id;

    private String roleId;

    private String name;

    private String sign;

    private Integer status;

    private String descript;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public Integer getStatus() {
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

    public Integer getCreateUser() {
        return createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    private Integer createUser;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer delFlag;

    @Override
    public String toString() {
        return "SysRoleInfo{" +
                "id=" + id +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", sign='" + sign + '\'' +
                ", status=" + status +
                ", descript='" + descript + '\'' +
                ", createUser=" + createUser +
                ", updateUser=" + updateUser +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }

}
