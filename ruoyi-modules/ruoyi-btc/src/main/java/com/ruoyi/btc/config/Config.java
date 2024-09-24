package com.ruoyi.btc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "system")
@Data
public class Config {

    /**
     * 加密密钥
     */
    private String AESkey;
}
