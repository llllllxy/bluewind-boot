package com.liuxingyu.meco.sys.sysitfckey.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-07-01-19:54
 **/
public class ItfcPermissionTree implements Serializable {
    private static final long serialVersionUID = -8662911865469212697L;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 名称
     */
    private String title;

    /**
     * 子集
     */
    private List<ItfcPermissionTree> children;

    /**
     * 节点是否初始为选中状态
     */
    private Boolean checked = false;

    /**
     * 是节点是否为禁用状态
     */
    private Boolean disabled = false;

    /**
     * 节点是否初始展开
     */
    private boolean spread = true;

    /**
     * 链接
     */
    private String href;

    /**
     * 类型
     */
    private Integer type;


    /**
     * 菜单名称（适配xm-select）
     */
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 菜单值（适配xm-select）
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getParentId() {
        return parentId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<ItfcPermissionTree> getChildren() {
        return children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public boolean isSpread() {
        return spread;
    }

    public String getHref() {
        return href;
    }

    public Integer getType() {
        return type;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChildren(List<ItfcPermissionTree> children) {
        this.children = children;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LayuiTree{" +
                "parentId='" + parentId + '\'' +
                ", permissionId='" + permissionId + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", children=" + children +
                ", checked=" + checked +
                ", disabled=" + disabled +
                ", spread=" + spread +
                ", href='" + href + '\'' +
                ", type=" + type +
                '}';
    }
}
