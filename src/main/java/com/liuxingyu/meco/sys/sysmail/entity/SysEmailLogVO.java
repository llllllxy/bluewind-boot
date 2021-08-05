package com.liuxingyu.meco.sys.sysmail.entity;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:47
 **/
public class SysEmailLogVO extends SysEmailLog {
    private static final long serialVersionUID = 8949343311668109530L;

    private String content;

    private String createUserName;

    public String template;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "SysEmailLogVO{" +
                "content='" + content + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", template='" + template + '\'' +
                '}';
    }
}
