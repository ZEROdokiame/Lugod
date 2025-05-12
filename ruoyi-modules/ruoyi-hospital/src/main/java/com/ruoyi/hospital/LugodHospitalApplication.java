package com.ruoyi.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
/**
 * 医院系统
 *
 * @author Lugod
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class LugodHospitalApplication {
    public static void main(String[] args) {

        SpringApplication.run(LugodHospitalApplication.class,args);
        System.out.println("(♥◠‿◠)ﾉﾞ  路神医院模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "$$\\       $$$$$$$$\\  $$\\ \n"+
                "$$ |      \\____$$  | $$ | \n"+
                "$$ |          $$  /  $$ | \n"+
                "$$ |         $$  /   $$ | \n"+
                "$$ |        $$  /    $$ | \n"+
                "$$ |       $$  /     $$ | \n"+
                "$$$$$$$$\\ $$$$$$$$\\  $$$$$$$$\\ \n"+
                "\\________|\\________| \\________| \n");
    }
}