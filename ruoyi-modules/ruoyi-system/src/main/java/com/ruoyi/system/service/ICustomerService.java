package com.ruoyi.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Customer;

/**
 * 客户信息Service接口
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
public interface ICustomerService extends IService<Customer>
{
    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    public Customer selectCustomerById(Long id);

    /**
     * 查询客户信息列表
     * 
     * @param customer 客户信息
     * @return 客户信息集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户信息
     * 
     * @param customer 客户信息
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改客户信息
     * 
     * @param customer 客户信息
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键集合
     * @return 结果
     */
    public int deleteCustomerByIds(Long[] ids);

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    public int deleteCustomerById(Long id);

    /**
     * 根据手机好MD5查询用户
     * @param phoneMD5
     * @return
     */
    R<Customer> selectByPhoneMd5(String phoneMD5);

    /**
     * 通过手机号更新用户信息
     * @param customer
     * @return
     */
    R updateByPhoneMd5(Customer customer);
}
