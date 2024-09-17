package com.ruoyi.system.domain.dto;

import lombok.Data;

@Data
public class MerchantListDto {

    //商户名
    String merchantName;

    //商户跳转地址
    String merchantUrl;

    //商户描述
    String merchantDescribe;
}
