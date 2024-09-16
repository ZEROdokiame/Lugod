package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.system.api.RemoteMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class RemoteMerChantFallbackFactory implements FallbackFactory<RemoteMerchantService>
{

    @Override
    public RemoteMerchantService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteMerchantService()
        {
            @Override
            public R<List<Merchant>> merchantList(String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }


        };
    }
}
