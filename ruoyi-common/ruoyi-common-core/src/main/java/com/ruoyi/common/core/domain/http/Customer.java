package com.ruoyi.common.core.domain.http;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 客户信息对象 customer
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Data
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**渠道ID**/
    @Excel(name = "渠道ID")
    private Long channelId;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 0 男 1 女 */
    @Excel(name = "0 男 1 女")
    private Integer sex;

    @Excel(name="身份证号")
    private String idCard;

    /** 昵称 */
    @Excel(name = "昵称")
    private String name;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String acturlName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 手机号MD5 */
    @Excel(name = "手机号MD5")
    private String phoneMd5;

    /** 0 未实名 1已实名 */
    @Excel(name = "0 未实名 1已实名")
    private Boolean isAuth;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 城市编码 */
    @Excel(name = "城市编码")
    private Integer cityCode;

    /** 首次登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首次登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstLoginTime;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 最后登录IP */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录IP", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginIp;

    /** 用户状态 1正常 2异常 可继续扩展 */
    @Excel(name = "用户状态 1正常 2异常 可继续扩展")
    private Integer status;

    /** 无社保 */
    @Excel(name = "社保")
    private Integer socialSecurity;

    /** 无车 */
    @Excel(name = "车")
    private Integer car;

    /** 保单缴纳不满一年 */
    @Excel(name = "保单")
    private Integer guaranteeSlip;

    /** 初中 */
    @Excel(name = "学历")
    private Integer education;

    /** 公积金未满6个月 */
    @Excel(name = "公积金")
    private Integer accumulationFund;

    /** 本地无房 */
    @Excel(name = "房")
    private Integer hourse;

    /** 上班族 */
    @Excel(name = "职业")
    private Integer career;


    /** 花呗5000以下 */
    @Excel(name = "花呗")
    private Integer huaBei;

    /** 白条5000以下 */
    @Excel(name = "白条")
    private Integer baiTiao;

    /** 芝麻分 */
    @Excel(name = "芝麻分")
    private Integer zhiMa;


}
