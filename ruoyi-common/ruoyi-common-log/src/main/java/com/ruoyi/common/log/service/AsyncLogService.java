package com.ruoyi.common.log.service;

import com.ruoyi.common.core.executor.ThreadFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.system.api.RemoteLogService;
import com.ruoyi.system.api.domain.SysOperLog;

import java.util.concurrent.*;

/**
 * 异步调用日志服务
 * 
 * @author ruoyi
 */
@Service
public class AsyncLogService
{

    private static final Logger log = LoggerFactory.getLogger(AsyncLogService.class);

    private final static ExecutorService logSaveExecutorService = new ThreadPoolExecutor(2,10,10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),new ThreadFactoryImpl("AsyncLogServiceExecutorService",true),
            new RejectedExecutionHandler() {
                //丢弃任务
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    if (r instanceof AsyncLogService.RunData) {
                        SysOperLog msg = ((AsyncLogService.RunData) r).getData();
                        log.error("保存日志繁忙,丢弃日志:{}",msg);
                    }
                }
            });

    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    public void saveSysLog(SysOperLog sysOperLog) throws Exception
    {
        RunData runData = new RunData();
        runData.setData(sysOperLog);
        runData.setRunnable(()->{
            try {
                remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
            } catch (Exception e) {
                log.error("保存日志异常:{}", e.getMessage());
                throw new RuntimeException(e);
            }
        });
        logSaveExecutorService.execute(runData);
    }


    static class RunData implements Runnable {
        SysOperLog data = null;
        Runnable runnable;
        @Override
        public void run() {
            runnable.run();
        }

        public SysOperLog getData() {
            return data;
        }

        public void setData(SysOperLog data) {
            this.data = data;
        }

        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }
    }
}
