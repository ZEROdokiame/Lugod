package com.ruoyi.btc.service;

import com.ruoyi.btc.domain.ComPublicHalfDto;

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
    void check(ComPublicHalfDto comPublicHalfDto);

    /**
     * 半流程通用进件
     * @param comPublicHalfDto
     */
    void input(ComPublicHalfDto comPublicHalfDto);

    /**
     * 渠道查询订单是否成功
     * @param comPublicHalfDto
     */
    void checkOrder(ComPublicHalfDto comPublicHalfDto);

}
