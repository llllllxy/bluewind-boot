package com.bluewind.boot.module.system.config.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2021-04-20-22:35
 **/
public class Config implements Serializable {
    private static final long serialVersionUID = 6038162441886289729L;

    /**
     * id
     */
    private Integer id;

    /**
     * 系统名称(简称)
     */
    private String systemName;

    /**
     * 系统名称(全称)
     */
    private String systemFullName;

    /**
     * 系统logo
     */
    private String systemLogo;

    /**
     * 网站标题
     */
    private String websiteTitle;

    /**
     * 网站图标
     */
    private String websiteIcon;

    /**
     * 网站关键字
     */
    private String websiteKeywords;

    /**
     * 网站描述
     */
    private String websiteDescription;

    /**
     * 网站备案号
     */
    private String recordNo;

    /**
     * 网站版权信息
     */
    private String copyright;

    /**
     * 登录页的背景图
     */
    private String loginBackImg;

    /**
     * 系统首页链接
     */
    private String homepageHref;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemFullName() {
        return systemFullName;
    }

    public void setSystemFullName(String systemFullName) {
        this.systemFullName = systemFullName;
    }

    public String getSystemLogo() {
        return systemLogo;
    }

    public void setSystemLogo(String systemLogo) {
        this.systemLogo = systemLogo;
    }

    public String getWebsiteTitle() {
        return websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    public String getWebsiteIcon() {
        return websiteIcon;
    }

    public void setWebsiteIcon(String websiteIcon) {
        this.websiteIcon = websiteIcon;
    }

    public String getWebsiteKeywords() {
        return websiteKeywords;
    }

    public void setWebsiteKeywords(String websiteKeywords) {
        this.websiteKeywords = websiteKeywords;
    }

    public String getWebsiteDescription() {
        return websiteDescription;
    }

    public void setWebsiteDescription(String websiteDescription) {
        this.websiteDescription = websiteDescription;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLoginBackImg() {
        return loginBackImg;
    }

    public void setLoginBackImg(String loginBackImg) {
        this.loginBackImg = loginBackImg;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getHomepageHref() {
        return homepageHref;
    }

    public void setHomepageHref(String homepageHref) {
        this.homepageHref = homepageHref;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "id=" + id +
                ", systemName='" + systemName + '\'' +
                ", systemFullName='" + systemFullName + '\'' +
                ", systemLogo='" + systemLogo + '\'' +
                ", websiteTitle='" + websiteTitle + '\'' +
                ", websiteIcon='" + websiteIcon + '\'' +
                ", websiteKeywords='" + websiteKeywords + '\'' +
                ", websiteDescription='" + websiteDescription + '\'' +
                ", recordNo='" + recordNo + '\'' +
                ", copyright='" + copyright + '\'' +
                ", loginBackImg='" + loginBackImg + '\'' +
                ", createUser=" + createUser +
                ", createTime='" + createTime + '\'' +
                ", updateUser=" + updateUser +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
