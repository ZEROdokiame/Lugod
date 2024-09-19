package com.ruoyi.common.core.domain.http;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商户对象 merchant
 *
 * @author ruoyi
 * @date 2024-09-15
 */
@Data
public class Merchant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 商户类型 1H5 2连登 3半流程 4全流程 */
    @Excel(name = "商户类型 1H5 2连登 3半流程 4全流程")
    private Long merchantType;

    /** 商户名称 */
    @Excel(name = "商户名称")
    private String merchantName;

    /** 商户描述 */
    @Excel(name = "商户描述")
    private String merchantDescribe;

    /** 商户主体 */
    @Excel(name = "商户主体")
    private String merchantCompany;

    /** logo文件地址 */
    @Excel(name = "logo文件地址")
    private String logo;

    /** 是否上下架 */
    @Excel(name = "是否上下架")
    private Integer status;

    /** 定量数 */
    @Excel(name = "定量数")
    private Integer limitNum;

    /**注册地址**/
    private String hitUrl;

    /** 是否定量 0否 1是 */
    @Excel(name = "是否定量 0否 1是")
    private Integer limitType;

    /** 渠道限制类型 0不限 1准入 2禁入 */
    @Excel(name = "渠道限制类型 0不限 1准入 2禁入")
    private Integer channelLimitType;

    /** 渠道限制ID */
    @Excel(name = "渠道限制ID")
    private String channelLimit;

    /** 渠道限制类型 0不限 1满足其一 2满足全部 */
    @Excel(name = "渠道限制类型 0不限 1满足其一 2满足全部")
    private Integer customerInfoFilterType;

    /** 年龄限制开始 */
    @Excel(name = "年龄限制开始")
    private Integer ageLimitStart;

    /** 年龄限制结束 */
    @Excel(name = "年龄限制结束")
    private Integer ageLimitEnd;

    /** 手机号禁入号段英文逗号分隔 */
    @Excel(name = "手机号禁入号段英文逗号分隔")
    private String phoneLimit;

    /** 标签 */
    @Excel(name = "标签")
    private String label;

    /** 无社保 */
    @Excel(name = "无社保")
    private Boolean socialSecurityNo;

    /** 社保未满6个月 */
    @Excel(name = "社保未满6个月")
    private Boolean socialSecurityLow;

    /** 社保6个月以上 */
    @Excel(name = "社保6个月以上")
    private Boolean socialSecurityHigh;

    /** 无车 */
    @Excel(name = "无车")
    private Boolean carNo;

    /** 有车 */
    @Excel(name = "有车")
    private Boolean carHave;

    /** 保单缴纳不满一年 */
    @Excel(name = "保单缴纳不满一年")
    private Boolean guaranteeSlipLow;

    /** 保单缴纳一年以上 */
    @Excel(name = "保单缴纳一年以上")
    private Boolean guaranteeSlipCentre;

    /** 保单缴纳2年以上 */
    @Excel(name = "保单缴纳2年以上")
    private Boolean guaranteeSlipHigh;

    /** 初中 */
    @Excel(name = "初中")
    private Boolean educationMiddle;

    /** 高中 */
    @Excel(name = "高中")
    private Boolean educationHighSchool;

    /** 中专 */
    @Excel(name = "中专")
    private Boolean educationPolytechnic;

    /** 大专 */
    @Excel(name = "大专")
    private Boolean educationJuniorCollege;

    /** 本科 */
    @Excel(name = "本科")
    private Boolean educationUndergraduateCourse;

    /** 研究生及以上 */
    @Excel(name = "研究生及以上")
    private Boolean educationPostgraduate;

    /** 公积金未满6个月 */
    @Excel(name = "公积金未满6个月")
    private Boolean accumulationFundLow;

    /** 公积金满6个月以上 */
    @Excel(name = "公积金满6个月以上")
    private Boolean accumulationFundHigh;

    /** 本地无房 */
    @Excel(name = "本地无房")
    private Boolean hourseNo;

    /** 本地全款房 */
    @Excel(name = "本地全款房")
    private Boolean hourseFullPayment;

    /** 本地按揭 */
    @Excel(name = "本地按揭")
    private Boolean hourseMortgaging;

    /** 上班族 */
    @Excel(name = "上班族")
    private Boolean officeWorker;

    /** 公务员 */
    @Excel(name = "公务员")
    private Boolean civilServant;

    /** 私营业主 */
    @Excel(name = "私营业主")
    private Boolean privatePropertyOwners;

    /** 个体户 */
    @Excel(name = "个体户")
    private Boolean selfEmployedPerson;

    /** 其他职业 */
    @Excel(name = "其他职业")
    private Boolean otherOccupations;

    /** 花呗5000以下 */
    @Excel(name = "花呗5000以下")
    private Boolean huaBeiLow;

    /** 花呗5000-10000 */
    @Excel(name = "花呗5000-10000")
    private Boolean huaBeiMiddle;

    /** 花呗10000以上 */
    @Excel(name = "花呗10000以上")
    private Boolean huaBeiHigh;

    /** 白条5000以下 */
    @Excel(name = "白条5000以下")
    private Boolean baiTiaoLow;

    /** 白条5000-10000 */
    @Excel(name = "白条5000-10000")
    private Boolean baiTiaoMiddle;

    /** 白条10000以上 */
    @Excel(name = "白条10000以上")
    private Boolean baiTiaoHigh;

    /** 芝麻分 */
    @Excel(name = "芝麻分")
    private Integer zhiMa;

}
