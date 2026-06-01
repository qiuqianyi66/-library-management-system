package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 * 
 * @author ruoyi
 */
public class CacheConstants
{
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 图书分类缓存 key
     */
    public static final String BOOK_TYPE_KEY = "book_type";

    /**
     * 热门图书缓存 key
     */
    public static final String HOT_BOOK_KEY = "hot_books";

    /**
     * 读者借阅状态缓存前缀 key
     */
    public static final String BORROW_STATUS_KEY = "borrow_status:";

    /**
     * 借阅配置缓存 key
     */
    public static final String CONFIG_BORROW_KEY = "sys_config:borrow";
}
