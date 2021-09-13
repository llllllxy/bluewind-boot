package com.bluewind.boot.sys.index.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-01-05-19:57
 **/
public class MenuVo implements Serializable {
    private static final long serialVersionUID = 5528101080905698238L;

    private String permissionId;

    private String parentId;

    private String title;

    private String href;

    private String target;

    private List<MenuVo> child;


    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getHref() {
        return href;
    }

    public String getTarget() {
        return target;
    }

    public List<MenuVo> getChild() {
        return child;
    }

    private String icon;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setChild(List<MenuVo> child) {
        this.child = child;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
