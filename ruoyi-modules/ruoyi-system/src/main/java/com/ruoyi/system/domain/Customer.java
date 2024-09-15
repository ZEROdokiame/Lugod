package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 0 男 1 女 */
    @Excel(name = "0 男 1 女")
    private Long sex;

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
    private Long isAuth;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 城市编码 */
    @Excel(name = "城市编码")
    private Long cityCode;

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
    private Long status;

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
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setActurlName(String acturlName) 
    {
        this.acturlName = acturlName;
    }

    public String getActurlName() 
    {
        return acturlName;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setPhoneMd5(String phoneMd5) 
    {
        this.phoneMd5 = phoneMd5;
    }

    public String getPhoneMd5() 
    {
        return phoneMd5;
    }
    public void setIsAuth(Long isAuth) 
    {
        this.isAuth = isAuth;
    }

    public Long getIsAuth() 
    {
        return isAuth;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setCityCode(Long cityCode) 
    {
        this.cityCode = cityCode;
    }

    public Long getCityCode() 
    {
        return cityCode;
    }
    public void setFirstLoginTime(Date firstLoginTime) 
    {
        this.firstLoginTime = firstLoginTime;
    }

    public Date getFirstLoginTime() 
    {
        return firstLoginTime;
    }
    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }
    public void setLastLoginIp(Date lastLoginIp) 
    {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginIp() 
    {
        return lastLoginIp;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
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
            .append("age", getAge())
            .append("sex", getSex())
            .append("name", getName())
            .append("acturlName", getActurlName())
            .append("phone", getPhone())
            .append("phoneMd5", getPhoneMd5())
            .append("isAuth", getIsAuth())
            .append("city", getCity())
            .append("cityCode", getCityCode())
            .append("firstLoginTime", getFirstLoginTime())
            .append("lastLoginTime", getLastLoginTime())
            .append("lastLoginIp", getLastLoginIp())
            .append("status", getStatus())
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
            .toString();
    }
}
