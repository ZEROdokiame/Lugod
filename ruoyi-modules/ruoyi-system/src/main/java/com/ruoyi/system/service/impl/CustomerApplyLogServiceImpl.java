package com.ruoyi.system.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.LocalDateTimeUtils;
import com.ruoyi.common.core.utils.SecureUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.dto.ApplyCallback;
import com.ruoyi.system.domain.dto.ApplyCallbackDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CustomerApplyLogMapper;
import com.ruoyi.common.core.domain.http.CustomerApplyLog;
import com.ruoyi.system.service.ICustomerApplyLogService;
import org.springframework.util.StringUtils;

/**
 * 客户申请记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
@Slf4j
public class CustomerApplyLogServiceImpl extends ServiceImpl<CustomerApplyLogMapper, CustomerApplyLog> implements IService<CustomerApplyLog>,ICustomerApplyLogService
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

    /**
     * 获取当日商户已申请数
     * @param merchantId
     * @return
     */
    @Override
    public Integer getApplySum(Long merchantId) {
        Long aLong = customerApplyLogMapper.selectCount(
                new LambdaQueryWrapper<CustomerApplyLog>()
                        .eq(CustomerApplyLog::getMerchantId, merchantId)
                        .eq(CustomerApplyLog::getOrderStatus, 0)
                        .ge(CustomerApplyLog::getCreateTime, LocalDateTimeUtils.getDateFromLocalDateTime(LocalDateTimeUtils.getTodayStartTime()))
                        .le(CustomerApplyLog::getCreateTime, LocalDateTimeUtils.getDateFromLocalDateTime(LocalDateTimeUtils.getTodayEndTime())));
        return aLong.intValue();
    }

    /**
     * 获取用户今日是否已申请
     * @param customerID
     * @return
     */
    @Override
    public R<Boolean> getCustomerApply(Long customerID) {
        Long aLong = customerApplyLogMapper.selectCount(
                new LambdaQueryWrapper<CustomerApplyLog>()
                        .isNotNull(CustomerApplyLog::getMerchantId)
                        .eq(CustomerApplyLog::getOrderStatus, 0)
                        .ge(CustomerApplyLog::getCreateTime, LocalDateTimeUtils.getDateFromLocalDateTime(LocalDateTimeUtils.getTodayStartTime()))
                        .le(CustomerApplyLog::getCreateTime, LocalDateTimeUtils.getDateFromLocalDateTime(LocalDateTimeUtils.getTodayEndTime())));
        return R.ok(aLong>0);
    }

    /**
     * 申请回调
     */
    @Override
    public AjaxResult applyCallBack(ApplyCallback applyCallback) {
        String s = SecureUtils.AesUtil.AesDecode(applyCallback.getData(), applyCallback.getSign());
        if (StringUtils.isEmpty(s)){
            return AjaxResult.error("解密异常");
        }
        ApplyCallbackDto applyCallbackDto;
        try {
             applyCallbackDto = JSONObject.parseObject(s, ApplyCallbackDto.class);
             log.info("渠道：{},回调数据:{}",applyCallback.getSign(),applyCallbackDto);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("数据转换异常");
        }
        try {
            CustomerApplyLog customerApplyLog = customerApplyLogMapper.selectOne(new LambdaQueryWrapper<CustomerApplyLog>().eq(CustomerApplyLog::getOrderNo, applyCallbackDto.getOrderNo()));
            if (applyCallbackDto.getOrderStatus()!=null) {
                customerApplyLog.setOrderStatus(applyCallbackDto.getOrderStatus().longValue());
            }
            if (applyCallbackDto.getPrice()!=null){
                if (customerApplyLog.getPrice()==null){
                    customerApplyLog.setPrice(applyCallbackDto.getPrice());
                }else {
                    customerApplyLog.setPrice(customerApplyLog.getPrice().add(applyCallbackDto.getPrice()));
                }
            }
            customerApplyLogMapper.updateById(customerApplyLog);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("数据异常");
        }
        return AjaxResult.success("回调成功");
    }
}
