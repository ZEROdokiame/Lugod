package com.ruoyi.btc.domain;

import lombok.Data;

@Data
public class CustomerInfoDto {
    //手机号码（Md5）
    private String phoneMd5;
    //性别 0 男 1 女
    private Integer sex;
    //手机号码（Md5）
    private String nameMd5;
    //年龄
    private Integer age;
    //手身份证md5
    private String idCardMd5;
    //所在城市
    private String city;
    //所在城市代码
    private Integer cityCode;
    //本地社保
    private Integer socialSecurity;
    //本地公积金
    private Integer accumulationFund;
    //名下车产
    private Integer car;
    //名下房产
    private Integer hourse;
    //个人保险
    private Integer guarantee;
    //芝麻分
    private Integer zhiMa;
    //职业身份
    private Integer career;
    //信用卡（1无 2有
    private Integer creditCard;
    //学历
    private Integer education;
    //月收入
    private Integer monthlyIncome;
    //ip地址
    private String ip;
    //渠道标识
    private String channelSignature;
}
