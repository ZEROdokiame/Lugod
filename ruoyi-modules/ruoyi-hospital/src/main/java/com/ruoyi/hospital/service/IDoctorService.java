package com.ruoyi.hospital.service;

import java.util.List;
import com.ruoyi.hospital.domain.Doctor;

public interface IDoctorService {
    /**
     * 根据科室ID查询医生列表
     *
     * @param deptId 科室ID
     * @return 医生列表
     */
     List<Doctor> selectDoctorsByDeptId(Long deptId);

    /**
     * 查询医生信息
     *
     * @param doctorId 医生ID
     * @return 医生信息
     */
     Doctor selectDoctorById(Long doctorId);

    /**
     * 新增医生信息
     *
     * @param doctor 医生信息
     * @return 结果
     */
     int insertDoctor(Doctor doctor);

    /**
     * 修改医生信息
     *
     * @param doctor 医生信息
     * @return 结果
     */
     int updateDoctor(Doctor doctor);

    /**
     * 批量删除医生信息
     *
     * @param doctorIds 需要删除的医生ID数组
     * @return 结果
     */
     int deleteDoctorByIds(Long[] doctorIds);

    /**
     * 删除医生信息
     *
     * @param doctorId 医生ID
     * @return 结果
     */
     int deleteDoctorById(Long doctorId);
}
