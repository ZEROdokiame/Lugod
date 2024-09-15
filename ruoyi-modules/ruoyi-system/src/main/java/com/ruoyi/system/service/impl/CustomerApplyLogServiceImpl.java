package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CustomerApplyLogMapper;
import com.ruoyi.system.domain.CustomerApplyLog;
import com.ruoyi.system.service.ICustomerApplyLogService;

/**
 * 客户申请记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class CustomerApplyLogServiceImpl implements ICustomerApplyLogService 
{
    @Autowired
    private CustomerApplyLogMapper customerApplyLogMapper;

    /**
     * 查询客户申请记录
     * 
     * @param id 客户申请记录主键
     * @return 客户申请记录
     */
    @Override
    public CustomerApplyLog selectCustomerApplyLogById(Long id)
    {
        return customerApplyLogMapper.selectCustomerApplyLogById(id);
    }

    /**
     * 查询客户申请记录列表
     * 
     * @param customerApplyLog 客户申请记录
     * @return 客户申请记录
     */
    @Override
    public List<CustomerApplyLog> selectCustomerApplyLogList(CustomerApplyLog customerApplyLog)
    {
        return customerApplyLogMapper.selectCustomerApplyLogList(customerApplyLog);
    }

    /**
     * 新增客户申请记录
     * 
     * @param customerApplyLog 客户申请记录
     * @return 结果
     */
    @Override
    public int insertCustomerApplyLog(CustomerApplyLog customerApplyLog)
    {
        customerApplyLog.setCreateTime(DateUtils.getNowDate());
        return customerApplyLogMapper.insertCustomerApplyLog(customerApplyLog);
    }

    /**
     * 修改客户申请记录
     * 
     * @param customerApplyLog 客户申请记录
     * @return 结果
     */
    @Override
    public int updateCustomerApplyLog(CustomerApplyLog customerApplyLog)
    {
        customerApplyLog.setUpdateTime(DateUtils.getNowDate());
        return customerApplyLogMapper.updateCustomerApplyLog(customerApplyLog);
    }

    /**
     * 批量删除客户申请记录
     * 
     * @param ids 需要删除的客户申请记录主键
     * @return 结果
     */
    @Override
    public int deleteCustomerApplyLogByIds(Long[] ids)
    {
        return customerApplyLogMapper.deleteCustomerApplyLogByIds(ids);
    }

    /**
     * 删除客户申请记录信息
     * 
     * @param id 客户申请记录主键
     * @return 结果
     */
    @Override
    public int deleteCustomerApplyLogById(Long id)
    {
        return customerApplyLogMapper.deleteCustomerApplyLogById(id);
    }
}
