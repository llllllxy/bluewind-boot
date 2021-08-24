package com.liuxingyu.meco.common.consts;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:57
 * 系统常量类
 **/
public final class SystemConst {

    /**
     * 项目名称
     */
    public static final String SYSTEM_NAME = "meco-server";

    /**
     * 登录用户 令牌 Redis Key 前缀
     */
    public static final String SYSTEM_USER_KEY = "meco:user:key";

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
    public static final String SYSTEM_USER_MENU = "meco:user:menu";

    /**
     * 用户角色
     */
    public static final String SYSTEM_USER_ROLE = "meco:user:role";

    /**
     * 用户权限
     */
    public static final String SYSTEM_USER_PERMISSION = "meco:user:permission";

    /**
     * 用户登陆次数
     */
    public static final String SYSTEM_LOGIN_TIMES = "meco:logintimes";

    /**
     * itfc-key
     */
    public static final String SYSTEM_ITFC_KEY = "meco:itfckey";

    /**
     * id-table业务流水号
     */
    public static final String SYSTEM_ID_TABLE = "meco:idtable";
}
