package com.ruoyi.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.RedisConstant;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.EncryptUtil;
import com.ruoyi.common.redis.service.CustomerTokenService;
import com.ruoyi.system.config.SystemConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CustomerMapper;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.system.service.ICustomerService;
import org.springframework.util.StringUtils;

/**
 * 客户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements IService<Customer>,ICustomerService {
    private final CustomerMapper customerMapper;
    private final SystemConfig systemConfig;
    private final CustomerTokenService customerTokenService;
    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public Customer selectCustomerById(Long id)
    {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询客户信息列表
     * 
     * @param customer 客户信息
     * @return 客户信息
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer)
    {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户信息
     * 
     * @param customer 客户信息
     * @return 结果
     */
    @Override
    public int insertCustomer(Customer customer)
    {
        customer.setCreateTime(DateUtils.getNowDate());
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改客户信息
     * 
     * @param customer 客户信息
     * @return 结果
     */
    @Override
    public int updateCustomer(Customer customer)
    {
        customer.setUpdateTime(DateUtils.getNowDate());
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(Long[] ids)
    {
        return customerMapper.deleteCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerById(Long id)
    {
        return customerMapper.deleteCustomerById(id);
    }

    /**
     * 通过手机号MD5查询
     * @param phoneMD5
     * @return
     */
    @Override
    public R<Customer> selectByPhoneMd5(String phoneMD5) {
        Customer one = getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getPhoneMd5, phoneMD5));
        if (one==null||one.getId()==null){
            return R.fail();
        }
        return R.ok(one);
    }

    /**
     * 通过手机号更新用户信息
     * @param customer
     * @return
     */
    @Override
    public R updateByPhoneMd5(Customer customer) {
        int update = customerMapper.update(customer, new UpdateWrapper<Customer>().lambda().eq(Customer::getPhoneMd5, customer.getPhoneMd5()));
        if (update>0){
            return R.ok();
        }
        return R.fail();
    }

    @Override
    public String getCustomerToken(String phone) {
        log.info("获取用户token,手机号:{},加密结果：{}", phone, EncryptUtil.AESencode(phone, systemConfig.getAESkey()));
        Customer customer = this.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getPhone, EncryptUtil.AESencode(phone, systemConfig.getAESkey())));
        log.info("获取用户token,用户信息:{}", customer);
        //获取到用户登陆的token
        String token = customerTokenService.getToken(customer.getId());
        if (StringUtils.isEmpty(token)) {
            //生成一个长60的token
            token = customerTokenService.generateToken(customer.getId(), customer.getPhone(), "ANDROID", customer.getChannelId());
        }
        return token;
    }

}
