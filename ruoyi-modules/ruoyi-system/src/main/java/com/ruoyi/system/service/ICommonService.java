package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.http.Channel;
import com.ruoyi.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 渠道配置Service接口
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
public interface ICommonService
{


    /**
     * H5发送验证码
     * @param phone
     * @return
     */
    AjaxResult sendSms(String phone,String smsSource);
}
