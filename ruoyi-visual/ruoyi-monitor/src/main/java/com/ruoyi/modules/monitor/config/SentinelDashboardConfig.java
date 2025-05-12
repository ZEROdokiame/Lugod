package com.ruoyi.modules.monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.io.File;
import java.io.IOException;

/**
 * Sentinel Dashboard 自动启动配置
 * 
 * @author ruoyi
 */
@Configuration
public class SentinelDashboardConfig {
    
    private static final Logger log = LoggerFactory.getLogger(SentinelDashboardConfig.class);
    
    @Value("${sentinel.dashboard.enabled:true}")
    private boolean enabled;
    
    @Value("${sentinel.dashboard.port:8718}")
    private int port;
    
    @Value("${sentinel.dashboard.jar:D:/Lugod/sentinel-dashboard.jar}")
    private String jarPath;
    
    private Process sentinelProcess;
    
    @PostConstruct
    public void startSentinelDashboard() {
        if (!enabled) {
            log.info("Sentinel Dashboard 自动启动已禁用");
            return;
        }
        
        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            log.warn("Sentinel Dashboard JAR文件不存在: {}", jarPath);
            return;
        }
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "java", 
                "-Dserver.port=" + port, 
                "-jar", 
                jarPath
            );
            
            processBuilder.directory(jarFile.getParentFile());
            processBuilder.redirectErrorStream(true);
            
            log.info("正在启动 Sentinel Dashboard: {}", jarPath);
            sentinelProcess = processBuilder.start();
            
            log.info("Sentinel Dashboard 已启动，端口: {}", port);
            
            // 启动一个线程来处理进程输出，防止管道堵塞
            new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    while (sentinelProcess.isAlive() && sentinelProcess.getInputStream().read(buffer) != -1) {
                        // 只需要读取输出，不需要处理
                    }
                } catch (IOException e) {
                    log.error("读取 Sentinel Dashboard 输出时发生错误", e);
                }
            }, "sentinel-output-reader").start();
            
        } catch (IOException e) {
            log.error("启动 Sentinel Dashboard 失败", e);
        }
    }
    
    @PreDestroy
    public void stopSentinelDashboard() {
        if (sentinelProcess != null && sentinelProcess.isAlive()) {
            log.info("正在关闭 Sentinel Dashboard");
            sentinelProcess.destroy();
            
            // 给进程一些时间优雅关闭
            try {
                if (!sentinelProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    sentinelProcess.destroyForcibly();
                    log.info("Sentinel Dashboard 已强制关闭");
                } else {
                    log.info("Sentinel Dashboard 已正常关闭");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                sentinelProcess.destroyForcibly();
                log.warn("关闭 Sentinel Dashboard 时被中断", e);
            }
        }
    }
}
