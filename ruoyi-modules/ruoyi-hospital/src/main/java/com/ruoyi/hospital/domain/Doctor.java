package com.ruoyi.hospital.domain;

import lombok.Data;
import java.util.Date;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 医生信息对象 hospital_doctor
 */
@Data
public class Doctor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 医生ID */
    private Long doctorId;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 所属科室ID */
    @Excel(name = "所属科室ID")
    private Long deptId;

    /** 所属科室名称 */
    @Excel(name = "所属科室名称")
    private String deptName;

    /** 职称 */
    @Excel(name = "职称")
    private String title;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
