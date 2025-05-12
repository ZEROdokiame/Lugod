package com.ruoyi.hospital.domain;

import lombok.Data;
import java.util.Date;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 患者信息对象 hospital_patient
 * 
 * @author lugod
 */
@Data
public class Patient extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 患者ID */
    private Long patientId;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientName;

    /** 性别 */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 紧急联系人 */
    @Excel(name = "紧急联系人")
    private String emergencyContact;

    /** 紧急联系电话 */
    @Excel(name = "紧急联系电话")
    private String emergencyPhone;

    /** 就诊状态 (0未就诊 1已分诊 2就诊中 3已就诊 4已取消) */
    @Excel(name = "就诊状态", readConverterExp = "0=未就诊,1=已分诊,2=就诊中,3=已就诊,4=已取消")
    private String status;

    /** 排队号码 */
    @Excel(name = "排队号码")
    private String queueNumber;

    /** 叫号时间 */
    @Excel(name = "叫号时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date callTime;

    /** 科室ID */
    private Long deptId;

    /** 医生ID */
    private Long doctorId;
}
