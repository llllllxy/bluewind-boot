package com.bluewind.boot.module.sys.sysuserrole.entity;


import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:44
 **/
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -4505248235480377853L;

    private Integer id;

    private Integer userId;

    private String roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
