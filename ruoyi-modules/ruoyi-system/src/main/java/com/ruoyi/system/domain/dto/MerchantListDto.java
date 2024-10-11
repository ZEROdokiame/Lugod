package com.ruoyi.system.domain.dto;

import lombok.Data;

@Data
public class MerchantListDto {

    //商户名
    private String merchantName;

    //商户跳转地址
    private String merchantUrl;

    //商户描述
    private String merchantDescribe;

    //商户ID
    private Long merchantId;
}
