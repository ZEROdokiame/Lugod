package com.ruoyi.system.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplyCallbackDto {


    //订单号
    private String orderNo;

    //md5
    private String md5;

    //订单价格
    private BigDecimal price;

    //订单状态
    private Integer orderStatus;

}
