package com.ruoyi.hospital.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.NonNull;

/**
 * 医院科室对象 hospital_dept
 */
@Data
public class HospitalDept extends BaseEntity {

    /** 科室ID */
    private Long deptId;

    /** 科室名称 */
    @Excel(name = "科室名称")
    private String deptName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 科室负责人 */
    @Excel(name = "科室负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 科室状态（0正常 1停用） */
    @Excel(name = "科室状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
