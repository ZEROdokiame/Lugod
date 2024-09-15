package com.ruoyi.btc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.btc.domain.ComPublicHalfDto;
import com.ruoyi.btc.domain.CustomerInfoDto;
import com.ruoyi.btc.service.ISysPublicHalfService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.common.core.utils.SecureUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 本地文件存储
 * 
 * @author ruoyi
 */
@Primary
@Service
@RequiredArgsConstructor
public class SysPublicHalfServiceImpl implements ISysPublicHalfService
{
    private final RemoteCustomerService remoteCustomerService;

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
        //匹配资质 造轮子 返回多个符合的商户


        //结束返回上游结果
        return null;
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

        //解密为customerInfoDto

        //校验数据必传参数是否未传

        //转化字段未数据库中资质字段 更新 用户实名状态 一并保存用户申请记录 已申请

        //匹配资质 造轮子 返回多个符合的商户

        //取排序第一的

        //返回渠道绑定的注册页拼接token

        //返回上游信息
        return null;
    }

    /**
     * 渠道查询订单是否成功
     * @param comPublicHalfDto
     */
    @Override
    public AjaxResult checkOrder(ComPublicHalfDto comPublicHalfDto) {
        //根据手机号MD5渠道标识 查询是否成功

        //失败直接失败

        //成功抽奖 按扣量比抽

        //返回是否成功
        return null;
    }
}
