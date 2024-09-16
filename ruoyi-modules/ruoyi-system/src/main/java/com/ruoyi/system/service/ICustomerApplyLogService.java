package com.ruoyi.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.CustomerApplyLog;

/**
 * 客户申请记录Service接口
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
public interface ICustomerApplyLogService extends IService<CustomerApplyLog>
{
    /**
     * 查询客户申请记录
     * 
     * @param id 客户申请记录主键
     * @return 客户申请记录
     */
    public CustomerApplyLog selectCustomerApplyLogById(Long id);

    /**
     * 查询客户申请记录列表
     * 
     * @param customerApplyLog 客户申请记录
     * @return 客户申请记录集合
     */
    public List<CustomerApplyLog> selectCustomerApplyLogList(CustomerApplyLog customerApplyLog);

    /**
     * 新增客户申请记录
     * 
     * @param customerApplyLog 客户申请记录
     * @return 结果
     */
    public int insertCustomerApplyLog(CustomerApplyLog customerApplyLog);

    /**
     * 修改客户申请记录
     * 
     * @param customerApplyLog 客户申请记录
     * @return 结果
     */
    public int updateCustomerApplyLog(CustomerApplyLog customerApplyLog);

    /**
     * 批量删除客户申请记录
     * 
     * @param ids 需要删除的客户申请记录主键集合
     * @return 结果
     */
    public int deleteCustomerApplyLogByIds(Long[] ids);

    /**
     * 删除客户申请记录信息
     * 
     * @param id 客户申请记录主键
     * @return 结果
     */
    public int deleteCustomerApplyLogById(Long id);

    /**
     * 获取当日商户已申请数
     * @param merchantId
     * @return
     */
    Integer getApplySum(Long merchantId);

    /**
     * 获取用户今日是否是否已申请
     * @param customerID
     * @return
     */
    R<Boolean> getCustomerApply(Long customerID);
}
