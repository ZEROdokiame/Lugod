package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.GetSumDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.CustomerApplyLog;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteCustomerApplyLogService;
import com.ruoyi.system.api.RemoteMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class RemoteCustomerApplyLogFallbackFactory implements FallbackFactory<RemoteCustomerApplyLogService>
{

    @Override
    public RemoteCustomerApplyLogService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteCustomerApplyLogService()
        {
            @Override
            public R<Integer> sum(GetSumDto getSumDto, @RequestHeader(SecurityConstants.FROM_SOURCE) String source)
            {
                return R.fail("获取商户已申请数失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> customerApply(Long customerID, String source) {
                return null;
            }

            @Override
            public AjaxResult add(CustomerApplyLog customerApplyLog) {
                return null;
            }
        };
    }
}
