package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.constant.RedisConstant;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.service.ICommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * 渠道配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class CommonServiceImpl implements ICommonService
{
    @Autowired
    private RedisService redisService;


    @Override
    public AjaxResult sendSms(String phone) {
        if (StringUtils.isEmpty(phone)){
            return AjaxResult.error("手机号未空");
        }
        if (phone.length()!=11){
            return AjaxResult.error("手机号长度异常");
        }
        //发送验证码
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(9000) + 1000;

        //发送验证码工具类

        //放入缓存 3分钟
        redisService.setCacheObject(RedisConstant.H5_LOGIN_CACHE+phone,1234,3*60l, TimeUnit.SECONDS);

        return AjaxResult.success("发送成功");
    }
}
