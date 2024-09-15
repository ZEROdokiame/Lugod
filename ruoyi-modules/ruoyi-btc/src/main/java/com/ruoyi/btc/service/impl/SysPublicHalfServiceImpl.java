package com.ruoyi.btc.service.impl;

import com.ruoyi.btc.domain.ComPublicHalfDto;
import com.ruoyi.btc.service.ISysPublicHalfService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 本地文件存储
 * 
 * @author ruoyi
 */
@Primary
@Service
public class SysPublicHalfServiceImpl implements ISysPublicHalfService
{


    /**
     * 半流程通用撞库
     * @param comPublicHalfDto
     */
    @Override
    public void check(ComPublicHalfDto comPublicHalfDto) {
        //校验 IP地址是否正常 渠道标识是否存在 数据是否为空

        //解密为customerInfoDto

        //校验数据必传参数是否未传

        //转化字段未数据库中资质字段 并保存 用户未实名状态 一并保存用户申请记录 未申请状态

        //匹配资质 造轮子 返回多个符合的商户

        //结束返回上游结果

    }

    /**
     * 半流程通用进件
     * @param comPublicHalfDto
     */
    @Override
    public void input(ComPublicHalfDto comPublicHalfDto) {
        //校验 IP地址是否正常 渠道标识是否存在 数据是否为空

        //解密为customerInfoDto

        //校验数据必传参数是否未传

        //转化字段未数据库中资质字段 更新 用户实名状态 一并保存用户申请记录 已申请

        //匹配资质 造轮子 返回多个符合的商户

        //取排序第一的

        //返回渠道绑定的注册页拼接token

        //返回上游信息

    }

    /**
     * 渠道查询订单是否成功
     * @param comPublicHalfDto
     */
    @Override
    public void checkOrder(ComPublicHalfDto comPublicHalfDto) {
        //根据手机号MD5渠道标识 查询是否成功

        //失败直接失败

        //成功抽奖 按扣量比抽

        //返回是否成功
    }
}
