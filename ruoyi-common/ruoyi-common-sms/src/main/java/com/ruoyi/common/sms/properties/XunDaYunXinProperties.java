package com.ruoyi.common.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 迅达云信短信 配置类
 */
@Data
@ConfigurationProperties(prefix = "xundayunxin")
public class XunDaYunXinProperties {
    /**
     * 请求地址
     */
    private String baseUrl = "http://47.96.236.136:7862/";
    /**
     * 账号
     */
    private String account = "932425";
    /**
     * 密码
     */
    private String password = "alDE77Gmo";

    /**
     * 虚拟接入码
     */
    private String extno = "10690367";

    /**
     * 是否启用手机号加密
     */
    private boolean encryptionEnabled;

    /**
     * 短信模板
     */
    private String template = "【信用秒租】验证码为：{code}，您正在登录信用秒租，请在3分钟内完成验证，如非本人操作，请忽略本短信。";

}
