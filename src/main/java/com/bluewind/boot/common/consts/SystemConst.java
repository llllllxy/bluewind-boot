package com.bluewind.boot.common.consts;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:57
 * 系统常量类
 **/
public final class SystemConst {

    /**
     * 项目名称
     */
    public static final String SYSTEM_NAME = "bluewind-boot";

    /**
     * 登录用户 令牌 Redis Key 前缀
     */
    public static final String SYSTEM_USER_KEY = "bluewind:user:key";

    /**
     * 登录用户cookie-key
     */
    public static final String SYSTEM_USER_TOKEN = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 用户菜单
     */
    public static final String SYSTEM_USER_MENU = "bluewind:user:menu";

    /**
     * 用户角色
     */
    public static final String SYSTEM_USER_ROLE = "bluewind:user:role";

    /**
     * 用户权限
     */
    public static final String SYSTEM_USER_PERMISSION = "bluewind:user:permission";

    /**
     * 用户登陆次数
     */
    public static final String SYSTEM_LOGIN_TIMES = "bluewind:logintimes";

    /**
     * itfc-key
     */
    public static final String SYSTEM_ITFC_KEY = "bluewind:itfckey";

    /**
     * itfc-key-permission
     */
    public static final String SYSTEM_ITFC_KEY_PERMISSION = "bluewind:itfckeypermission";

    /**
     * id-table业务流水号
     */
    public static final String SYSTEM_ID_TABLE = "bluewind:idtable";

    /**
     * 请求频率限制
     */
    public static final String SYSTEM_REQ_LIMIT = "bluewind:reqlimit";

    /**
     * 系统配置信息
     */
    public static final String SYS_CONFIGURE = "bluewind:configure";

    /**
     * 服务监控信息
     */
    public static final String SYS_SERVER_INFO = "bluewind:serverinfo";

    /**
     * 字典信息
     */
    public static final String SYS_DICT = "bluewind:dict";

    /**
     * 注册邮箱验证码信息
     */
    public static final String SYSTEM_REGISTER_EMAILCODE = "bluewind:register:code";
}
