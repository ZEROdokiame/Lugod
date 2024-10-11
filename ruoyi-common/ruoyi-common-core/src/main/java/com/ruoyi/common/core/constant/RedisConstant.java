package com.ruoyi.common.core.constant;

public class RedisConstant {

    /**
     * 用户登录缓存
     */
    public final static String APP_CUSTOMER_KEY = CacheConstants.PROJET + ":customer:key:";

    /**
     * 用户名缓存
     */
    public final static String APP_CUSTOMER_USERNAME_KEY = CacheConstants.PROJET + ":app:customer:username:key:";
    /**
     * 渠道ID缓存
     */
    public final static String APP_CUSTOMER_CHANNEL_KEY = CacheConstants.PROJET + ":app:customer:channel:key:";
    /**
     * 用户登录缓存
     */
    public final static String APP_CUSTOMER_TOKEN_KEY = CacheConstants.PROJET + ":app:customer:token:key:";

    /**
     * app用户设备标识
     */
    public final static String APP_DEVICE_IDENTIFICATION = CacheConstants.PROJET + ":app:app:device:identification:";

    /**
     * H5登录验证码
     */
    public final static String H5_LOGIN_CACHE = CacheConstants.PROJET+"H5:login:cache:";

    /**
     * H5申请幂等校验
     */
    public final static String H5_APPLY_CHECK = CacheConstants.PROJET+"H5:apply:check:";

    /**
     * 撞库幂等校验
     */
    public final static String HIT_CHECK_CACHE = CacheConstants.PROJET+"hit:check:cache:";
}
