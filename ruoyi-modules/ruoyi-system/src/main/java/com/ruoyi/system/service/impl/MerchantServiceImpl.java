package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.RedisConstant;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.common.core.domain.http.CustomerApplyLog;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.CustomerTokenService;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.domain.dto.MerchantListDto;
import com.ruoyi.system.mapper.CustomerApplyLogMapper;
import com.ruoyi.system.mapper.CustomerMapper;
import com.ruoyi.system.service.ICustomerApplyLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MerchantMapper;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.system.service.IMerchantService;

import javax.servlet.http.HttpServletRequest;

/**
 * 商户Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IService<Merchant>,IMerchantService
{
    private final MerchantMapper merchantMapper;
    private final CustomerTokenService customerTokenService;
    private final ICustomerApplyLogService customerApplyLogService;
    private final CustomerMapper customerMapper;
    private final RedisService redisService;

    /**
     * 查询商户
     * 
     * @param id 商户主键
     * @return 商户
     */
    @Override
    public Merchant selectMerchantById(Long id)
    {
        return merchantMapper.selectMerchantById(id);
    }

    /**
     * 查询商户列表
     * 
     * @param merchant 商户
     * @return 商户
     */
    @Override
    public List<Merchant> selectMerchantList(Merchant merchant)
    {
        return merchantMapper.selectMerchantList(merchant);
    }

    /**
     * 新增商户
     * 
     * @param merchant 商户
     * @return 结果
     */
    @Override
    public int insertMerchant(Merchant merchant)
    {
        merchant.setCreateTime(DateUtils.getNowDate());
        return merchantMapper.insertMerchant(merchant);
    }

    /**
     * 修改商户
     * 
     * @param merchant 商户
     * @return 结果
     */
    @Override
    public int updateMerchant(Merchant merchant)
    {
        merchant.setUpdateTime(DateUtils.getNowDate());
        return merchantMapper.updateMerchant(merchant);
    }

    /**
     * 批量删除商户
     * 
     * @param ids 需要删除的商户主键
     * @return 结果
     */
    @Override
    public int deleteMerchantByIds(Long[] ids)
    {
        return merchantMapper.deleteMerchantByIds(ids);
    }

    /**
     * 删除商户信息
     * 
     * @param id 商户主键
     * @return 结果
     */
    @Override
    public int deleteMerchantById(Long id)
    {
        return merchantMapper.deleteMerchantById(id);
    }

    /**
     * 获取基本合适的商户
     * @return
     */
    @Override
    public R<List<Merchant>> getMerchantList() {
        List<Merchant> merchants = merchantMapper.selectList(new LambdaQueryWrapper<Merchant>().eq(Merchant::getStatus, true));
        if (CollectionUtil.isEmpty(merchants)){
            return R.fail();
        }
        return R.ok(merchants);
    }

    public List<Merchant> findAllMerchantList(){
        return merchantMapper.findAllMerchantList();
    }

    @Override
    public AjaxResult getMatchMerchantList(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        Long customerId = customerTokenService.getCustomerId(authorization, false);
        if (customerId==null){
            return AjaxResult.error("用户不存在或未登录");
        }
        Customer customer = customerMapper.selectById(customerId);
        List<Merchant> merchants = matchMerchant(customer);
        List<MerchantListDto> merchantListDtos = new ArrayList<>();
        for (Merchant merchant:merchants) {
            MerchantListDto merchantListDto = new MerchantListDto();
            merchantListDto.setMerchantName(merchant.getMerchantName());
            merchantListDto.setMerchantDescribe(merchant.getMerchantDescribe());
            merchantListDto.setMerchantUrl(merchant.getHitUrl());
            merchantListDto.setMerchantId(merchant.getId());
            merchantListDtos.add(merchantListDto);
        }
        return AjaxResult.success(merchantListDtos);
    }

    @Override
    public AjaxResult H5applyMerchant(Long merchantId, HttpServletRequest request) {
        //获取用户
        String authorization = request.getHeader("Authorization");
        Long customerId = customerTokenService.getCustomerId(authorization, false);
        Boolean aBoolean = redisService.hasKey(RedisConstant.H5_APPLY_CHECK + customerId);
        if (aBoolean){
            return AjaxResult.error("请勿重复点击");
        }
        Customer customer = customerMapper.selectById(customerId);
        Merchant merchant = merchantMapper.selectById(merchantId);
        redisService.setCacheObject(RedisConstant.H5_APPLY_CHECK+customerId,1,10l, TimeUnit.SECONDS);
        //生成订单号
        String orderNo = System.currentTimeMillis()+""+merchantId+""+customerId;
        //记录申请订单
        CustomerApplyLog customerApplyLog = new CustomerApplyLog();
        customerApplyLog.setCustomerId(customerId);
        customerApplyLog.setMerchantId(merchantId);
        customerApplyLog.setChannelId(customer.getChannelId());
        customerApplyLog.setOrderStatus(0l);
        customerApplyLog.setOrderNo(orderNo);
        customerApplyLog.setCreateTime(new Date());
        customerApplyLogService.save(customerApplyLog);
        return AjaxResult.success("点击成功","orderNo="+orderNo+"&sign="+merchant.getChannelSign());
    }

    /**
     * 获取前筛符合的商户
     * @param customer
     */
    private List<Merchant> matchMerchant(Customer customer) {
        R<List<Merchant>> listR = getMerchantList();
        if (listR.getCode()!=200){
            return new ArrayList<>();
        }
        List<Merchant> merchants = new ArrayList<>();
        for (Merchant merchant:listR.getData()) {
            //限量判定
            Integer sum = customerApplyLogService.getApplySum(merchant.getId());
            if (merchant.getLimitType()!=null&&merchant.getLimitType()==1&&merchant.getLimitNum()<=sum){
                continue;
            }

            if (merchant.getAgeLimitStart()!=null&&merchant.getAgeLimitEnd()!=null&&(customer.getAge()<merchant.getAgeLimitStart()||customer.getAge()>merchant.getAgeLimitEnd())){
                continue;
            }
            if (merchant.getChannelLimitType()!=null&&(merchant.getChannelLimitType()==1||merchant.getChannelLimitType()==2)){

                List<Long> list = Arrays.asList(merchant.getChannelLimit().split(",")).stream().map(val->Long.parseLong(val)).collect(Collectors.toList());
                if (merchant.getChannelLimitType()==1&& !list.contains(customer.getChannelId())){
                    continue;
                }
                if (merchant.getChannelLimitType()==2&& list.contains(customer.getChannelId())){
                    continue;
                }
            }
            merchants.add(merchant);
        }
        return merchants;
    }
}
