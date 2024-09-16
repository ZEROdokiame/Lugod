package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.system.api.RemoteCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class RemoteCustomerFallbackFactory implements FallbackFactory<RemoteCustomerService>
{
    @Override
    public RemoteCustomerService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteCustomerService()
        {
            @Override
            public R<Customer> getCustomerInfoById(Long id, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<Customer> getCustomerInfoByPhoneMd5(String phoneMD5, String source) {
                log.info("查询用户失败:{}",throwable.getMessage());
                return R.fail("查询用户失败:" + throwable.getMessage());
            }

            @Override
            public R updateByPhoneMd5(Customer customer, String source) {
                return R.fail("更新用户失败:" + throwable.getMessage());
            }

            @Override
            public R add(Customer customer, String source) {
                log.info("新增用户失败:{}",throwable.getMessage());
                return R.fail("新增用户失败");
            }

            @Override
            public String getCustomerToken(String phone) {
                return null;
            }
        };
    }
}
