package com.ruoyi.common.redis.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.constant.RedisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author:
 * @Date: 18点29分
 * @Description: 用户token管理
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerTokenService {

    private final RedisService redisService;
    /**
     * token过期时间
     */
    private static final Long EXPIRE_TIME = 30 * 24 * 60 * 60L;

    /**
     * 随机生成128位的token，包含数字、大小写字母
     *
     * @param customerId 用户id
     * @param phone      手机号
     * @param deviceType 设备类型
     * @param channelId  渠道id
     * @return token
     */
    public String generateToken(Long customerId, String phone, String deviceType, Long channelId) {
        //获取到老的token
        String oldToken = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        if (StringUtils.hasText(oldToken)) {
            //删除老的token
            this.refreshToken(oldToken);
        }
        String newToken = null;
        boolean exit = true;
        while (exit) {
            newToken = RandomUtil.randomString(128);
            if (!redisService.hasKey(RedisConstant.APP_CUSTOMER_USERNAME_KEY + newToken)) {
                exit = false;
            }
        }
        redisService.setCacheObject(RedisConstant.APP_CUSTOMER_USERNAME_KEY + newToken, phone, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.setCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId, newToken, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.setCacheObject(RedisConstant.APP_CUSTOMER_CHANNEL_KEY + newToken, channelId, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.setCacheObject(RedisConstant.APP_CUSTOMER_TOKEN_KEY + newToken, customerId, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.setCacheObject(RedisConstant.APP_DEVICE_IDENTIFICATION + newToken, deviceType.toString(), EXPIRE_TIME, TimeUnit.SECONDS);
        return newToken;
    }

    /**
     * 判断token是否过期
     *
     * @param token token
     * @return true：过期，false：未过期
     */
    public boolean isExpire(String token) {
        String customerId = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_TOKEN_KEY + token) + StrUtil.EMPTY;
        String originalToken = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        String username = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_USERNAME_KEY + token) + StrUtil.EMPTY;
        return StringUtils.isEmpty(customerId) || StringUtils.isEmpty(originalToken) || !originalToken.equals(token) || !StringUtils.hasText(username);
    }

    /**
     * 刷新token有效期
     *
     * @param token    token
     * @param booleans 是否需要退出登录
     */
    public void refreshToken(String token, Boolean... booleans) {
        boolean logOut = isLogOut(booleans);
        Long customerId = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_TOKEN_KEY + token);
        if (Objects.isNull(customerId) && logOut) {
            new Exception("登录已过期，请重新登录");
        }
        redisService.expire(RedisConstant.APP_CUSTOMER_USERNAME_KEY + token, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.expire(RedisConstant.APP_CUSTOMER_KEY + customerId, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.expire(RedisConstant.APP_CUSTOMER_CHANNEL_KEY + token, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.expire(RedisConstant.APP_CUSTOMER_TOKEN_KEY + token, EXPIRE_TIME, TimeUnit.SECONDS);
        redisService.expire(RedisConstant.APP_DEVICE_IDENTIFICATION + token, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 根据用户id刷新token有效期
     *
     * @param customerId 用户id
     * @param booleans   是否需要退出登录
     * @return token
     */
    public String refreshToken(Long customerId, Boolean... booleans) {
        boolean logOut = isLogOut(booleans);
        String token = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        if (StringUtils.isEmpty(token) && logOut) {
            new Exception("登录已过期，请重新登录");
        }
        this.refreshToken(token);
        return token;
    }

    /**
     * 刷新token有效期
     *
     * @param request 请求
     */
    public void refreshToken(HttpServletRequest request) {
        String token = getToken(request);
        this.refreshToken(token);
    }

    /**
     * 移除token
     *
     * @param token    token
     * @param booleans 是否需要退出登录
     */
    public void removeToken(String token, Boolean... booleans) {
        boolean logOut = isLogOut(booleans);
        Long customerId = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_TOKEN_KEY + token);
        if (Objects.isNull(customerId) && logOut) {
            new Exception("登录已过期，请重新登录");
        }
        redisService.deleteObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        redisService.deleteObject(RedisConstant.APP_CUSTOMER_TOKEN_KEY + token);
        redisService.deleteObject(RedisConstant.APP_CUSTOMER_USERNAME_KEY + token);
        redisService.deleteObject(RedisConstant.APP_CUSTOMER_CHANNEL_KEY + token);
        redisService.deleteObject(RedisConstant.APP_DEVICE_IDENTIFICATION + token);
    }

    /**
     * 根据用户id移除token
     *
     * @param customerId 用户id
     */
    public void removeToken(Long customerId) {
        String token = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        this.removeToken(token);
    }

    /**
     * 根据HttpServletRequest获取token
     *
     * @param request  HttpServletRequest
     * @param booleans 是否需要退出登录
     * @return token
     */
    public String getToken(HttpServletRequest request, Boolean... booleans) {
        boolean logOut = isLogOut(booleans);
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token) && logOut) {
            String requestURI = request.getRequestURI();
            log.info("登录过期重新登录, requestURI:{}, logOut:{}, token:{}", requestURI, logOut, token);
            new Exception("登录已过期，请重新登录");
        }
        return token;
    }

    /**
     * 根据用户id获取到token
     *
     * @param customerId 用户id
     * @return token
     */
    public String getToken(Long customerId) {
        String token = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return token;
    }

    /**
     * 通过token获取到用户的ID
     *
     * @param token  token
     * @param logOut 是否需要退出登录
     * @return 用户ID
     */
    public Long getCustomerId(String token, Boolean logOut) {
        Long customerId = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_TOKEN_KEY + token);
        if (Objects.isNull(customerId) && logOut) {
            new Exception("登录已过期，请重新登录");
        }
        return customerId;
    }

    /**
     * 根据HttpServletRequest获取到用户的ID
     *
     * @param request  HttpServletRequest
     * @param booleans 是否需要退出登录
     * @return 用户ID
     */
    public Long getCustomerId(HttpServletRequest request, Boolean... booleans) {
        boolean logOut = isLogOut(booleans);
        String token = getToken(request, logOut);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return getCustomerId(token, logOut);
    }


    /**
     * 根据token获取到用户的手机号 未解密
     *
     * @param token    token
     * @param booleans 是否需要退出登录
     * @return 手机号
     */
    public String getPhone(String token, Boolean... booleans) {
        boolean logOut = isLogOut(booleans);
        String phone = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_USERNAME_KEY + token);
        if (Objects.isNull(phone) && logOut) {
            new Exception("登录已过期，请重新登录");
        }
        return phone;
    }

    /**
     * 根据用户ID获取手机号码
     *
     * @param customerId 用户ID
     * @param booleans   是否需要退出登录
     * @return 手机号
     */
    public String getPhone(Long customerId, Boolean... booleans) {
        String token = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        if (Objects.isNull(token) && isLogOut(booleans)) {
            new Exception("登录已过期，请重新登录");
        }
        String phone = getPhone(token);
//        try {
//            phone = EncryptUtil.AESdecode(phone, redisService.getAppEncrypted());
//        }catch (Exception e) {
//            return phone;
//        }

        return phone;
    }

    /**
     * 根据HttpServletRequest获取到用户的手机号
     *
     * @param request HttpServletRequest
     * @return 手机号
     */
    public String getPhone(HttpServletRequest request) {
        String token = getToken(request);
        return getPhone(token);
    }

    /**
     * 根据token获取到用户的渠道ID
     *
     * @param token    token
     * @param booleans 是否需要退出登录
     * @return 渠道ID
     */
    public Long getChannelId(String token, Boolean... booleans) {
        Long channelId = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_CHANNEL_KEY + token);
        if (Objects.isNull(channelId) && isLogOut(booleans)) {
            new Exception("登录已过期，请重新登录");
        }
        return channelId;
    }

    /**
     * 根据HttpServletRequest获取到用户的渠道ID
     *
     * @param request HttpServletRequest
     * @return 渠道ID
     */
    public Long getChannelId(HttpServletRequest request, Boolean... booleans) {
        String token = getToken(request,booleans);
        return getChannelId(token,booleans);
    }

    /**
     * 根据用户ID获取渠道ID
     *
     * @param customerId 用户ID
     * @param booleans   是否需要退出登录
     * @return 渠道ID
     */
    public Long getChannelId(Long customerId, Boolean... booleans) {
        String token = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        if (Objects.isNull(token) && isLogOut(booleans)) {
            new Exception("登录已过期，请重新登录");
        }
        Long channelId = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_CHANNEL_KEY + token);
        if (Objects.isNull(channelId) && isLogOut(booleans)) {
            new Exception("登录已过期，请重新登录");
        }
        return channelId;
    }

    /**
     * 更新渠道
     * @param customerId 用户ID
     * @param channelId 渠道ID
     */
    public void setChannelId(Long customerId,Long channelId) {
        String token = getToken(customerId);
        redisService.setCacheObject(RedisConstant.APP_CUSTOMER_CHANNEL_KEY + token, channelId, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 根据token获取设备类型
     *
     * @param token    token
     * @param booleans 是否需要退出登录
     * @return 设备类型
     */
    public String getDeviceType(String token, Boolean... booleans) {
        String deviceType = redisService.getCacheObject(RedisConstant.APP_DEVICE_IDENTIFICATION + token);
        if (Objects.isNull(deviceType) && isLogOut(booleans)) {
            new Exception("登录已过期，请重新登录");
        }
        return deviceType;
    }

    /**
     * 根据HttpServletRequest获取设备类型
     *
     * @param request HttpServletRequest
     * @return 设备类型
     */
    public String getDeviceType(HttpServletRequest request,Boolean... booleans) {
        String token = getToken(request,booleans);
        return getDeviceType(token,booleans);
    }

    /**
     * 根据HttpServletRequest获取设备类型
     *
     * @param request HttpServletRequest
     * @return 设备类型
     */
    public String getDeviceTypeStr(HttpServletRequest request) {
        String token = getToken(request);
        return getDeviceType(token);
    }

    /**
     * 根据用户ID获取设备类型
     *
     * @param customerId 用户ID
     * @param booleans   是否需要退出登录
     * @return 设备类型
     */
    public String getDeviceType(Long customerId, Boolean... booleans) {
        String token = redisService.getCacheObject(RedisConstant.APP_CUSTOMER_KEY + customerId);
        if (Objects.isNull(token) && isLogOut(booleans)) {
            new Exception("登录已过期，请重新登录");
        }
        String deviceType = redisService.getCacheObject(RedisConstant.APP_DEVICE_IDENTIFICATION + token);
        if (!StringUtils.hasText(deviceType)) {
            deviceType = "ANDROID";
        }
        return deviceType;
    }

    /**
     * 是否需要退出登录
     *
     * @param booleans 参数值
     * @return 是否需要退出登录
     */
    public Boolean isLogOut(Boolean... booleans) {
        if (booleans == null || booleans.length == 0) {
            return true;
        }
        return booleans[0];
    }

}
