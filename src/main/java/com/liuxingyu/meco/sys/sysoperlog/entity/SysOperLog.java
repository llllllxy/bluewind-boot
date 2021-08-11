package com.liuxingyu.meco.sys.sysoperlog.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-03-05-13:35
 **/
@TableName("sys_oper_log")
@ApiModel(value = "SysOperLog对象", description = "系统操作日志表")
public class SysOperLog implements Serializable {
    private static final long serialVersionUID = -4516808988241959991L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "model")
    @TableField(value = "model")
    private String model;

    @ApiModelProperty(value = "url")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value = "method")
    @TableField(value = "method")
    private String method;

    @ApiModelProperty(value = "descript")
    @TableField(value = "descript")
    private String descript;

    @ApiModelProperty(value = "ip")
    @TableField(value = "ip")
    private String ip;

    @ApiModelProperty(value = "type")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty(value = "spendTime")
    @TableField(value = "spend_time")
    private Integer spendTime;

    @ApiModelProperty(value = "createUser")
    @TableField(value = "create_user")
    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    @TableField(value = "create_time")
    private String createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysOperLog{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", descript='" + descript + '\'' +
                ", ip='" + ip + '\'' +
                ", type='" + type + '\'' +
                ", spendTime=" + spendTime +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                '}';
    }
}
