package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.factory.RemoteMerChantFallbackFactory;
import com.ruoyi.system.api.factory.RemoteUserFallbackFactory;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteMerchantService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteMerChantFallbackFactory.class)
public interface RemoteMerchantService
{
    /**
     * 获取合适的产品 前筛
     *
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/merchant/merchantList")
    public R<List<Merchant>> merchantList(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
