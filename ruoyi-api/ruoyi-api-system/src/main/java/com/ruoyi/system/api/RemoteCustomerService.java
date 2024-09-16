package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.system.api.factory.RemoteCustomerFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteCustomerService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteCustomerFallbackFactory.class)
public interface RemoteCustomerService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param id 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/customer/{id}")
    public R<Customer> getCustomerInfoById(@PathVariable("id") Long id, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过手机号MD5查询用户信息
     *
     * @param phoneMD5 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/customer/getByMd5")
    public R<Customer> getCustomerInfoByPhoneMd5(@RequestParam("phoneMD5")String phoneMD5, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 通过手机号MD5更新用户信息
     *
     * @param customer 用户
     * @return 结果
     */
    @PostMapping("/customer/updateByPhoneMd5")
    public R updateByPhoneMd5(Customer customer ,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 新增客户信息
     * @return
     */
    @PostMapping("/customer/addNewCustomer")
    public R add(@RequestBody Customer customer,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取用户token
     * @param phone
     * @return
     */
    @GetMapping("/customer/getCustomerToken")
    public String getCustomerToken(@RequestParam("phone") String phone);
}
