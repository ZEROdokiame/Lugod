package com.ruoyi.btc;

import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 三方调用服务
 * 
 * @author ruoyi
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RuoYiBtcApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiBtcApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  三方业务服务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
