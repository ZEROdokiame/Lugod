package com.ruoyi.system.domain;

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
public class Merchant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
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
    private Long logo;

    /** 是否上下架 */
    @Excel(name = "是否上下架")
    private Long status;

    /** 定量数 */
    @Excel(name = "定量数")
    private String limitNum;

    /** 是否定量 0否 1是 */
    @Excel(name = "是否定量 0否 1是")
    private Long limitType;

    /** 渠道限制类型 0不限 1准入 2禁入 */
    @Excel(name = "渠道限制类型 0不限 1准入 2禁入")
    private Long channelLimitType;

    /** 年龄限制开始 */
    @Excel(name = "年龄限制开始")
    private Long ageLimitStart;

    /** 年龄限制结束 */
    @Excel(name = "年龄限制结束")
    private Long ageLimitEnd;

    /** 手机号禁入号段英文逗号分隔 */
    @Excel(name = "手机号禁入号段英文逗号分隔")
    private String phoneLimit;

    /** 无社保 */
    @Excel(name = "无社保")
    private Long socialSecurityNo;

    /** 社保未满6个月 */
    @Excel(name = "社保未满6个月")
    private Long socialSecurityLow;

    /** 社保6个月以上 */
    @Excel(name = "社保6个月以上")
    private Long socialSecurityHigh;

    /** 无车 */
    @Excel(name = "无车")
    private Long carNo;

    /** 有车 */
    @Excel(name = "有车")
    private Long carHave;

    /** 保单缴纳不满一年 */
    @Excel(name = "保单缴纳不满一年")
    private Long guaranteeSlipLow;

    /** 保单缴纳一年以上 */
    @Excel(name = "保单缴纳一年以上")
    private Long guaranteeSlipCentre;

    /** 保单缴纳2年以上 */
    @Excel(name = "保单缴纳2年以上")
    private Long guaranteeSlipHigh;

    /** 初中 */
    @Excel(name = "初中")
    private Long educationMiddle;

    /** 高中 */
    @Excel(name = "高中")
    private Long educationHighSchool;

    /** 中专 */
    @Excel(name = "中专")
    private Long educationPolytechnic;

    /** 大专 */
    @Excel(name = "大专")
    private Long educationJuniorCollege;

    /** 本科 */
    @Excel(name = "本科")
    private Long educationUndergraduateCourse;

    /** 研究生及以上 */
    @Excel(name = "研究生及以上")
    private Long educationPostgraduate;

    /** 公积金未满6个月 */
    @Excel(name = "公积金未满6个月")
    private Long accumulationFundLow;

    /** 公积金满6个月以上 */
    @Excel(name = "公积金满6个月以上")
    private Long accumulationFundHigh;

    /** 本地无房 */
    @Excel(name = "本地无房")
    private Long hourseNo;

    /** 本地全款房 */
    @Excel(name = "本地全款房")
    private Long hourseFullPayment;

    /** 本地按揭 */
    @Excel(name = "本地按揭")
    private Long hourseMortgaging;

    /** 上班族 */
    @Excel(name = "上班族")
    private Long officeWorker;

    /** 公务员 */
    @Excel(name = "公务员")
    private Long civilServant;

    /** 私营业主 */
    @Excel(name = "私营业主")
    private Long privatePropertyOwners;

    /** 个体户 */
    @Excel(name = "个体户")
    private Long selfEmployedPerson;

    /** 其他职业 */
    @Excel(name = "其他职业")
    private Long otherOccupations;

    /** 花呗5000以下 */
    @Excel(name = "花呗5000以下")
    private Long huaBeiLow;

    /** 花呗5000-10000 */
    @Excel(name = "花呗5000-10000")
    private Long huaBeiMiddle;

    /** 花呗10000以上 */
    @Excel(name = "花呗10000以上")
    private Long huaBeiHigh;

    /** 白条5000以下 */
    @Excel(name = "白条5000以下")
    private Long baiTiaoLow;

    /** 白条5000-10000 */
    @Excel(name = "白条5000-10000")
    private Long baiTiaoMiddle;

    /** 白条10000以上 */
    @Excel(name = "白条10000以上")
    private Long baiTiaoHigh;

    /** 芝麻分 */
    @Excel(name = "芝麻分")
    private Long zhiMa;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setMerchantType(Long merchantType)
    {
        this.merchantType = merchantType;
    }

    public Long getMerchantType()
    {
        return merchantType;
    }
    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public String getMerchantName()
    {
        return merchantName;
    }
    public void setMerchantDescribe(String merchantDescribe)
    {
        this.merchantDescribe = merchantDescribe;
    }

    public String getMerchantDescribe()
    {
        return merchantDescribe;
    }
    public void setMerchantCompany(String merchantCompany)
    {
        this.merchantCompany = merchantCompany;
    }

    public String getMerchantCompany()
    {
        return merchantCompany;
    }
    public void setLogo(Long logo)
    {
        this.logo = logo;
    }

    public Long getLogo()
    {
        return logo;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setLimitNum(String limitNum)
    {
        this.limitNum = limitNum;
    }

    public String getLimitNum()
    {
        return limitNum;
    }
    public void setLimitType(Long limitType)
    {
        this.limitType = limitType;
    }

    public Long getLimitType()
    {
        return limitType;
    }
    public void setChannelLimitType(Long channelLimitType)
    {
        this.channelLimitType = channelLimitType;
    }

    public Long getChannelLimitType()
    {
        return channelLimitType;
    }
    public void setAgeLimitStart(Long ageLimitStart)
    {
        this.ageLimitStart = ageLimitStart;
    }

    public Long getAgeLimitStart()
    {
        return ageLimitStart;
    }
    public void setAgeLimitEnd(Long ageLimitEnd)
    {
        this.ageLimitEnd = ageLimitEnd;
    }

    public Long getAgeLimitEnd()
    {
        return ageLimitEnd;
    }
    public void setPhoneLimit(String phoneLimit)
    {
        this.phoneLimit = phoneLimit;
    }

    public String getPhoneLimit()
    {
        return phoneLimit;
    }
    public void setSocialSecurityNo(Long socialSecurityNo)
    {
        this.socialSecurityNo = socialSecurityNo;
    }

    public Long getSocialSecurityNo()
    {
        return socialSecurityNo;
    }
    public void setSocialSecurityLow(Long socialSecurityLow)
    {
        this.socialSecurityLow = socialSecurityLow;
    }

    public Long getSocialSecurityLow()
    {
        return socialSecurityLow;
    }
    public void setSocialSecurityHigh(Long socialSecurityHigh)
    {
        this.socialSecurityHigh = socialSecurityHigh;
    }

    public Long getSocialSecurityHigh()
    {
        return socialSecurityHigh;
    }
    public void setCarNo(Long carNo)
    {
        this.carNo = carNo;
    }

    public Long getCarNo()
    {
        return carNo;
    }
    public void setCarHave(Long carHave)
    {
        this.carHave = carHave;
    }

    public Long getCarHave()
    {
        return carHave;
    }
    public void setGuaranteeSlipLow(Long guaranteeSlipLow)
    {
        this.guaranteeSlipLow = guaranteeSlipLow;
    }

    public Long getGuaranteeSlipLow()
    {
        return guaranteeSlipLow;
    }
    public void setGuaranteeSlipCentre(Long guaranteeSlipCentre)
    {
        this.guaranteeSlipCentre = guaranteeSlipCentre;
    }

    public Long getGuaranteeSlipCentre()
    {
        return guaranteeSlipCentre;
    }
    public void setGuaranteeSlipHigh(Long guaranteeSlipHigh)
    {
        this.guaranteeSlipHigh = guaranteeSlipHigh;
    }

    public Long getGuaranteeSlipHigh()
    {
        return guaranteeSlipHigh;
    }
    public void setEducationMiddle(Long educationMiddle)
    {
        this.educationMiddle = educationMiddle;
    }

    public Long getEducationMiddle()
    {
        return educationMiddle;
    }
    public void setEducationHighSchool(Long educationHighSchool)
    {
        this.educationHighSchool = educationHighSchool;
    }

    public Long getEducationHighSchool()
    {
        return educationHighSchool;
    }
    public void setEducationPolytechnic(Long educationPolytechnic)
    {
        this.educationPolytechnic = educationPolytechnic;
    }

    public Long getEducationPolytechnic()
    {
        return educationPolytechnic;
    }
    public void setEducationJuniorCollege(Long educationJuniorCollege)
    {
        this.educationJuniorCollege = educationJuniorCollege;
    }

    public Long getEducationJuniorCollege()
    {
        return educationJuniorCollege;
    }
    public void setEducationUndergraduateCourse(Long educationUndergraduateCourse)
    {
        this.educationUndergraduateCourse = educationUndergraduateCourse;
    }

    public Long getEducationUndergraduateCourse()
    {
        return educationUndergraduateCourse;
    }
    public void setEducationPostgraduate(Long educationPostgraduate)
    {
        this.educationPostgraduate = educationPostgraduate;
    }

    public Long getEducationPostgraduate()
    {
        return educationPostgraduate;
    }
    public void setAccumulationFundLow(Long accumulationFundLow)
    {
        this.accumulationFundLow = accumulationFundLow;
    }

    public Long getAccumulationFundLow()
    {
        return accumulationFundLow;
    }
    public void setAccumulationFundHigh(Long accumulationFundHigh)
    {
        this.accumulationFundHigh = accumulationFundHigh;
    }

    public Long getAccumulationFundHigh()
    {
        return accumulationFundHigh;
    }
    public void setHourseNo(Long hourseNo)
    {
        this.hourseNo = hourseNo;
    }

    public Long getHourseNo()
    {
        return hourseNo;
    }
    public void setHourseFullPayment(Long hourseFullPayment)
    {
        this.hourseFullPayment = hourseFullPayment;
    }

    public Long getHourseFullPayment()
    {
        return hourseFullPayment;
    }
    public void setHourseMortgaging(Long hourseMortgaging)
    {
        this.hourseMortgaging = hourseMortgaging;
    }

    public Long getHourseMortgaging()
    {
        return hourseMortgaging;
    }
    public void setOfficeWorker(Long officeWorker)
    {
        this.officeWorker = officeWorker;
    }

    public Long getOfficeWorker()
    {
        return officeWorker;
    }
    public void setCivilServant(Long civilServant)
    {
        this.civilServant = civilServant;
    }

    public Long getCivilServant()
    {
        return civilServant;
    }
    public void setPrivatePropertyOwners(Long privatePropertyOwners)
    {
        this.privatePropertyOwners = privatePropertyOwners;
    }

    public Long getPrivatePropertyOwners()
    {
        return privatePropertyOwners;
    }
    public void setSelfEmployedPerson(Long selfEmployedPerson)
    {
        this.selfEmployedPerson = selfEmployedPerson;
    }

    public Long getSelfEmployedPerson()
    {
        return selfEmployedPerson;
    }
    public void setOtherOccupations(Long otherOccupations)
    {
        this.otherOccupations = otherOccupations;
    }

    public Long getOtherOccupations()
    {
        return otherOccupations;
    }
    public void setHuaBeiLow(Long huaBeiLow)
    {
        this.huaBeiLow = huaBeiLow;
    }

    public Long getHuaBeiLow()
    {
        return huaBeiLow;
    }
    public void setHuaBeiMiddle(Long huaBeiMiddle)
    {
        this.huaBeiMiddle = huaBeiMiddle;
    }

    public Long getHuaBeiMiddle()
    {
        return huaBeiMiddle;
    }
    public void setHuaBeiHigh(Long huaBeiHigh)
    {
        this.huaBeiHigh = huaBeiHigh;
    }

    public Long getHuaBeiHigh()
    {
        return huaBeiHigh;
    }
    public void setBaiTiaoLow(Long baiTiaoLow)
    {
        this.baiTiaoLow = baiTiaoLow;
    }

    public Long getBaiTiaoLow()
    {
        return baiTiaoLow;
    }
    public void setBaiTiaoMiddle(Long baiTiaoMiddle)
    {
        this.baiTiaoMiddle = baiTiaoMiddle;
    }

    public Long getBaiTiaoMiddle()
    {
        return baiTiaoMiddle;
    }
    public void setBaiTiaoHigh(Long baiTiaoHigh)
    {
        this.baiTiaoHigh = baiTiaoHigh;
    }

    public Long getBaiTiaoHigh()
    {
        return baiTiaoHigh;
    }
    public void setZhiMa(Long zhiMa)
    {
        this.zhiMa = zhiMa;
    }

    public Long getZhiMa()
    {
        return zhiMa;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("merchantType", getMerchantType())
                .append("merchantName", getMerchantName())
                .append("merchantDescribe", getMerchantDescribe())
                .append("merchantCompany", getMerchantCompany())
                .append("logo", getLogo())
                .append("status", getStatus())
                .append("limitNum", getLimitNum())
                .append("limitType", getLimitType())
                .append("channelLimitType", getChannelLimitType())
                .append("ageLimitStart", getAgeLimitStart())
                .append("ageLimitEnd", getAgeLimitEnd())
                .append("phoneLimit", getPhoneLimit())
                .append("socialSecurityNo", getSocialSecurityNo())
                .append("socialSecurityLow", getSocialSecurityLow())
                .append("socialSecurityHigh", getSocialSecurityHigh())
                .append("carNo", getCarNo())
                .append("carHave", getCarHave())
                .append("guaranteeSlipLow", getGuaranteeSlipLow())
                .append("guaranteeSlipCentre", getGuaranteeSlipCentre())
                .append("guaranteeSlipHigh", getGuaranteeSlipHigh())
                .append("educationMiddle", getEducationMiddle())
                .append("educationHighSchool", getEducationHighSchool())
                .append("educationPolytechnic", getEducationPolytechnic())
                .append("educationJuniorCollege", getEducationJuniorCollege())
                .append("educationUndergraduateCourse", getEducationUndergraduateCourse())
                .append("educationPostgraduate", getEducationPostgraduate())
                .append("accumulationFundLow", getAccumulationFundLow())
                .append("accumulationFundHigh", getAccumulationFundHigh())
                .append("hourseNo", getHourseNo())
                .append("hourseFullPayment", getHourseFullPayment())
                .append("hourseMortgaging", getHourseMortgaging())
                .append("officeWorker", getOfficeWorker())
                .append("civilServant", getCivilServant())
                .append("privatePropertyOwners", getPrivatePropertyOwners())
                .append("selfEmployedPerson", getSelfEmployedPerson())
                .append("otherOccupations", getOtherOccupations())
                .append("huaBeiLow", getHuaBeiLow())
                .append("huaBeiMiddle", getHuaBeiMiddle())
                .append("huaBeiHigh", getHuaBeiHigh())
                .append("baiTiaoLow", getBaiTiaoLow())
                .append("baiTiaoMiddle", getBaiTiaoMiddle())
                .append("baiTiaoHigh", getBaiTiaoHigh())
                .append("zhiMa", getZhiMa())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
