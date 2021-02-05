package org.lmt;

/**
 * 系统全局常量配置常量定义
 *
 * @author: LiaoMingtao
 * @date: 2019/12/14
 */
public final class GlobalConsts {

    /**
     * =============================== 系统 http header key定义===============================
     */
    public static final String X_CSRF_KEY = "X-CSRFToken";
    public static final String CSRF_KEY = "csrftoken";
    public static final String C_BK_TOKEN = "bk_token";
    public static final String SYS_LANGUAGE = "lang";
    public static final String USER_SESSION = "user";

    public static final String HDR_CONTENT_TYPE = "content-Type";
    public static final String HDR_LANG = "blueking-language";

    /**
     * =============================== 静态资源URL===============================
     */
    public static final String STATIC_VERSION = "STATIC_VERSION";
    public static final String APP_ID = "APP_ID";
    //APP URL
    public static final String SITE_URL = "SITE_URL";
    // 静态资源URL
    public static final String STATIC_URL = "STATIC_URL";
    public static final String PAAS_HOST = "BK_PAAS_HOST";

    /**
     * ===============================特殊字符资源===============================
     */

    public static final String SLASH = "/";
}
