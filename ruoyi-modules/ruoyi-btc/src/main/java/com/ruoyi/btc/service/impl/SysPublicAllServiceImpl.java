package com.ruoyi.btc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.btc.domain.ComPublicHalfDto;
import com.ruoyi.btc.domain.CustomerInfoDto;
import com.ruoyi.btc.service.ISysPublicAllService;
import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Channel;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.common.core.domain.http.CustomerApplyLog;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.common.core.utils.ProbitUtil;
import com.ruoyi.common.core.utils.SecureUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteCustomerApplyLogService;
import com.ruoyi.system.api.RemoteCustomerService;
import com.ruoyi.system.api.RemoteMerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 本地文件存储
 * 
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysPublicAllServiceImpl implements ISysPublicAllService
{
    private final RemoteCustomerService remoteCustomerService;
    private final RemoteMerchantService remoteMerchantService;
    private final RemoteCustomerApplyLogService remoteCustomerApplyLogService;
    private final RedisService redisService;

    /**
     * 半流程通用撞库
     * @param comPublicHalfDto
     */
    @Override
    public AjaxResult check(ComPublicHalfDto comPublicHalfDto) {
        //校验 IP地址是否正常 渠道标识是否存在 数据是否为空

        if (StringUtils.isEmpty(comPublicHalfDto.getChannelSignature())){
            return AjaxResult.error("渠道标识不能未空");
        }
        Channel channel = redisService.getCacheObject(CacheConstants.CHANNEL_SIGN + comPublicHalfDto.getChannelSignature());
        if (channel==null||channel.getId()==null){
            return AjaxResult.error("渠道不存在");
        }
        if (StringUtils.isEmpty(comPublicHalfDto.getData())){
            return AjaxResult.error("加密数据不能为空");
        }
        //解密为customerInfoDto
        String s = SecureUtils.AesUtil.AesDecode(comPublicHalfDto.getData(), comPublicHalfDto.getChannelSignature());
        if (s==null){
            return AjaxResult.error("解密异常");
        }
        CustomerInfoDto customerInfoDto = JSONObject.parseObject(s, CustomerInfoDto.class);
        //校验数据必传参数是否未传
        String checkData = checkData(customerInfoDto);
        if (checkData!=null){
            return AjaxResult.error(checkData);
        }
        //转化字段未数据库中资质字段 并保存 用户未实名状态 一并保存用户申请记录 未申请状态
        Customer customer = new Customer();
        BeanUtil.copyProperties(customerInfoDto,customer);
        customer.setChannelId(channel.getId());
        customer.setActurlName(customerInfoDto.getNameMd5());
        customer.setFirstLoginTime(new Date());
        customer.setLastLoginTime(new Date());
        customer.setIsAuth(false);
        customer.setStatus(2);
        R<Customer> customerInfoByPhoneMd5 = remoteCustomerService.getCustomerInfoByPhoneMd5(customerInfoDto.getPhoneMd5(), SecurityConstants.INNER);
        if (customerInfoByPhoneMd5.getCode()==200){
            remoteCustomerService.updateByPhoneMd5(customer,SecurityConstants.INNER);
        }else {
            remoteCustomerService.add(customer,SecurityConstants.INNER);
        }
        //TODO 暂时不做 目前下游暂时不需要 匹配资质 造轮子 返回多个符合的商户
        List<Merchant> merchants = matchMerchant(customer);
        //结束返回上游结果
        Map<String,Boolean> re = new HashMap<>();
        if (merchants.size()>0){
            re.put("data",true);
            return AjaxResult.success(re);
        }
        re.put("data",false);
        return AjaxResult.success(re);
    }

    /**
     * 获取前筛符合的商户
     * @param customer
     */
    private List<Merchant> matchMerchant(Customer customer) {
        R<List<Merchant>> listR = remoteMerchantService.merchantList(SecurityConstants.INNER);
        if (listR.getCode()!=200){
            return new ArrayList<>();
        }
        List<Merchant> merchants = new ArrayList<>();
        for (Merchant merchant:listR.getData()) {
            //限量判定
            R<Integer> sum = remoteCustomerApplyLogService.sum(merchant.getId(), SecurityConstants.INNER);
            if (merchant.getLimitType()==1&&merchant.getLimitNum()<=sum.getData()){
                continue;
            }

            if (customer.getAge()<merchant.getAgeLimitStart()||customer.getAge()>merchant.getAgeLimitEnd()){
                continue;
            }
            if (merchant.getChannelLimitType()==1||merchant.getChannelLimitType()==2){

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

    /**
     * 校验参数
     * @param customerInfoDto
     */
    private String checkData(CustomerInfoDto customerInfoDto) {
        if (customerInfoDto.getAge()==null){
            return "年龄不能为空";
        }
        if (StringUtils.isEmpty(customerInfoDto.getPhoneMd5())){
            return "手机号MD5不能为空";
        }
        if (StringUtils.isEmpty(customerInfoDto.getCity())){
            return "城市不能为空";
        }
        if (customerInfoDto.getCityCode()==null){
            return "城市编码不能为空";
        }
        if (customerInfoDto.getSocialSecurity()==null){
            return "本地社保不能为空";
        }
        if (customerInfoDto.getAccumulationFund()==null){
            return "本地公积金不能为空";
        }
        if (customerInfoDto.getCar()==null){
            return "车产不能为空";
        }
        if (customerInfoDto.getHourse()==null){
            return "房产不能为空";
        }
        if (customerInfoDto.getGuarantee()==null){
            return "房产不能为空";
        }
        if (customerInfoDto.getZhiMa()==null){
            return "芝麻分不能为空";
        }
        if (customerInfoDto.getCareer()==null){
            return "职业不能为空";
        }
        if (customerInfoDto.getCreditCard()==null){
            return "信用卡不能为空";
        }
        if (customerInfoDto.getEducation()==null){
            return "学历不能为空";
        }
        if (customerInfoDto.getMonthlyIncome()==null){
            return "月收入不能为空";
        }
        return null;
    }

    /**
     * 半流程通用进件
     * @param comPublicHalfDto
     */
    @Override
    public AjaxResult input(ComPublicHalfDto comPublicHalfDto) {
        //校验 IP地址是否正常 渠道标识是否存在 数据是否为空
        if (StringUtils.isEmpty(comPublicHalfDto.getChannelSignature())){
            return AjaxResult.error("渠道标识不能未空");
        }
        Channel channel = redisService.getCacheObject(CacheConstants.CHANNEL_SIGN + comPublicHalfDto.getChannelSignature());
        if (channel==null||channel.getId()==null){
            return AjaxResult.error("渠道不存在");
        }
        if (StringUtils.isEmpty(comPublicHalfDto.getData())){
            return AjaxResult.error("加密数据不能为空");
        }
        //解密为customerInfoDto
        String s = SecureUtils.AesUtil.AesDecode(comPublicHalfDto.getData(), comPublicHalfDto.getChannelSignature());
        if (s==null){
            return AjaxResult.error("解密异常");
        }
        CustomerInfoDto customerInfoDto = JSONObject.parseObject(s, CustomerInfoDto.class);
        //校验数据必传参数是否未传
        String checkData = checkData(customerInfoDto);
        //转化字段未数据库中资质字段 更新 用户实名状态 一并保存用户申请记录 已申请
        if (checkData!=null){
            return AjaxResult.error(checkData);
        }
        //转化字段未数据库中资质字段 并保存 用户未实名状态 一并保存用户申请记录 未申请状态
        Customer customer = new Customer();
        BeanUtil.copyProperties(customerInfoDto,customer);
        customer.setChannelId(channel.getId());
        customer.setActurlName(customerInfoDto.getName());
        customer.setFirstLoginTime(new Date());
        customer.setLastLoginTime(new Date());
        customer.setIsAuth(true);
        customer.setStatus(1);
        R<Customer> customerInfoByPhoneMd5 = remoteCustomerService.getCustomerInfoByPhoneMd5(customerInfoDto.getPhoneMd5(), SecurityConstants.INNER);
        if (customerInfoByPhoneMd5.getCode()==200){
            remoteCustomerService.updateByPhoneMd5(customer,SecurityConstants.INNER);
        }else {
           return AjaxResult.error("今日未撞库");
        }
        //匹配资质 造轮子 返回多个符合的商户
        List<Merchant> merchants = matchMerchant(customer);
        //TODO 取排序第一的

        //返回渠道绑定的注册页拼接token
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> map = new HashMap<>();

        //TODO 下游是H5承接不了上游全流程 暂时不做
        CustomerApplyLog customerApplyLog = new CustomerApplyLog();
        customerApplyLog.setCustomerId(customerInfoByPhoneMd5.getData().getId());
        customerApplyLog.setChannelId(channel.getId());
        customerApplyLog.setMerchantId(merchants.get(0).getId());
        customerApplyLog.setOrderStatus(0l);
        remoteCustomerApplyLogService.add(customerApplyLog);
        //返回上游信息
        return AjaxResult.success(result);
    }


}
