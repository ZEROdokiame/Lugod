package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.CustomerApplyLog;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.factory.RemoteChannelFallbackFactory;
import com.ruoyi.system.api.factory.RemoteCustomerApplyLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteChannelService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteChannelFallbackFactory.class)
public interface RemoteChannelService
{




}
