package com.ruoyi.btc.service;

import com.ruoyi.btc.domain.ComPublicHalfDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件上传接口
 * 
 * @author ruoyi
 */
public interface ISysPublicHalfService
{

    /**
     * 半流程通用撞库接口
     * @param comPublicHalfDto
     */
    AjaxResult check(ComPublicHalfDto comPublicHalfDto);

    /**
     * 半流程通用进件
     * @param comPublicHalfDto
     */
    AjaxResult input(ComPublicHalfDto comPublicHalfDto);

    /**
     * 渠道查询订单是否成功
     * @param
     */
    AjaxResult checkOrder(String phoneMd5, String channelSign);

}
