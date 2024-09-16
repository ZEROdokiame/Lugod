package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.system.api.factory.RemoteCustomerApplyLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 用户服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteCustomerApplyLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteCustomerApplyLogFallbackFactory.class)
public interface RemoteCustomerApplyLogService
{
    /**
     * 获取商户今日已申请数
     *
     *
     * @param merchantId
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/log/sum")
    public R<Integer> sum(@PathVariable("merchantId") Long merchantId,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
