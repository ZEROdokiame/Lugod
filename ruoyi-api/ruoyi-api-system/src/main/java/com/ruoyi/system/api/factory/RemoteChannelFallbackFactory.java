package com.ruoyi.system.api.factory;


import com.ruoyi.system.api.RemoteChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class RemoteChannelFallbackFactory implements FallbackFactory<RemoteChannelService>
{

    @Override
    public RemoteChannelService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteChannelService()
        {


        };
    }
}
