package com.liuxingyu.meco.websocket.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2021-07-28-20:12
 * @description websocket消息体
 **/
public class WebsocketVO implements Serializable {
    private static final long serialVersionUID = 5874022512884972610L;

    /**
     * 接收人
     */
    private Integer receiveUser;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(Integer receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "WebsocketVO{" +
                "receiveUser=" + receiveUser +
                ", content='" + content + '\'' +
                ", createUser=" + createUser +
                ", createUserName='" + createUserName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
