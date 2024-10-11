package com.ruoyi.common.sms.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class SmsResponse<T> {
    private Integer status;
    private Integer balance;
    private List<T> list;
}
