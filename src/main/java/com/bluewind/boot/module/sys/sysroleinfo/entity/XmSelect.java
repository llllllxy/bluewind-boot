package com.bluewind.boot.module.sys.sysroleinfo.entity;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:44
 **/
public class XmSelect {
    private String value;
    private String name;
    // 是否选中
    private Boolean selected = true;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "XmSelect{" +
                "value=" + value +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                '}';
    }

    public String getName() {
        return name;
    }

    public Boolean getSelected() {
        return selected;
    }
}
