package com.bluewind.boot.module.system.postinfo.entity;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:30
 **/
public class PostXmSelect implements Serializable {
    private static final long serialVersionUID = 5917667015756052626L;

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
