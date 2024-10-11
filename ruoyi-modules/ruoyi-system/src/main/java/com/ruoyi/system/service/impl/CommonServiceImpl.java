package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.constant.RedisConstant;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.sms.component.SmsComponent;
import com.ruoyi.system.service.ICommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 渠道配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsComponent smsComponent;


    @Autowired
    private Environment environment;


    @Override
    public AjaxResult sendSms(String phone, String smsSource) {
        if (StringUtils.isEmpty(phone)) {
            return AjaxResult.error("手机号未空");
        }
        if (phone.length() != 11) {
            return AjaxResult.error("手机号长度异常");
        }
        //正式环境发送验证码 pc4位 h5注册页6位
        String code;

        // 判断是否为正式环境
        if (isProductionProfileActive()) {
            //正式环境操作
            code = smsSource.equalsIgnoreCase("h5") ? generateCode(6) : generateCode(4);
            // 发送短信
            Map<String, String> params = new HashMap<>();
            params.put(phone, code);
            smsComponent.sendP2PMsg(params);
        } else {
            // 非正式环境直接设置固定验证码
            code = smsSource.equalsIgnoreCase("h5") ? "123456" : "1234";
        }
        //放入缓存 3分钟
        redisService.setCacheObject(RedisConstant.H5_LOGIN_CACHE + phone, code, 3 * 60L, TimeUnit.SECONDS);
        return AjaxResult.success("发送成功");
    }

    /**
     * 判断当前活跃环境是否是 正式环境
     * if (isProductionProfileActive()) {
     * // 进行与生产环境相关的逻辑处理
     * } else {
     * // 进行非生产环境的处理
     * }
     *
     * @return
     */
    public boolean isProductionProfileActive() {
        // 获取当前应用的活跃环境
        String[] activeProfiles = environment.getActiveProfiles();

        // 定义线上环境列表
        List<String> proList = new ArrayList<>(Arrays.asList("prod", "pro"));

        // 检查活跃环境是否包含在 proList 中
        for (String profile : activeProfiles) {
            if (proList.contains(profile)) {
                return true; // 如果有活跃环境在 proList 中，返回 true
            }
        }
        return false; // 如果没有匹配的环境，返回 false
    }

    /**
     * 定义生成验证码的方法
     *
     * @param length 验证码长度
     * @return
     */
    private String generateCode(int length) {
        SecureRandom secureRandom = new SecureRandom();
        int bound = (int) Math.pow(10, length);
        int min = bound / 10;
        return String.valueOf(secureRandom.nextInt(bound - min) + min);
    }
}
