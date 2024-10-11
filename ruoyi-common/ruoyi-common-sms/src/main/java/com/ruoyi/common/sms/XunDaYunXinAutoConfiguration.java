package com.ruoyi.common.sms;

import com.ruoyi.common.sms.component.SmsComponent;
import com.ruoyi.common.sms.properties.XunDaYunXinProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(XunDaYunXinProperties.class)
public class XunDaYunXinAutoConfiguration {

    @Bean
    public SmsComponent smsComponent() {
        return new SmsComponent();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
