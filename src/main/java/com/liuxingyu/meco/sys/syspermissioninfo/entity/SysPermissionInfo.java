package com.liuxingyu.meco.sys.syspermissioninfo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-02-05-22:23
 **/
public class SysPermissionInfo {

    private Integer id;

    private String permissionId;

    private String parentId;

    private String name;

    private Integer type;

    private String sign;

    private String href;

    private Integer sort;

    private String icon;

    private Integer status;

    private String target;

    private String descript;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer delFlag;

    public Integer getId() {
        return id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getSign() {
        return sign;
    }

    public String getHref() {
        return href;
    }

    public Integer getSort() {
        return sort;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTarget() {
        return target;
    }

    public String getDescript() {
        return descript;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDescript(String descript) {
        this.descript = descript;
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

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysPermissionInfo{" +
                "id=" + id +
                ", permissionId='" + permissionId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", sign='" + sign + '\'' +
                ", href='" + href + '\'' +
                ", sort=" + sort +
                ", icon='" + icon + '\'' +
                ", status=" + status +
                ", target='" + target + '\'' +
                ", descript='" + descript + '\'' +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
