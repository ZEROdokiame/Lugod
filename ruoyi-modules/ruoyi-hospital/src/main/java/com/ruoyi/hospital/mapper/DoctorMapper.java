package com.ruoyi.hospital.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.hospital.domain.Doctor;

/**
 * 医生信息Mapper接口
 */
@Mapper
public interface DoctorMapper {
    /**
     * 根据科室ID查询医生列表
     */
  List<Doctor> selectDoctorsByDeptId(Long deptId);

    /**
     * 查询医生信息
     */
     Doctor selectDoctorById(Long doctorId);

    /**
     * 新增医生信息
     */
     int insertDoctor(Doctor doctor);
    /**
     * 修改医生信息
     */
     int updateDoctor(Doctor doctor);

    /**
     * 删除医生信息
     */
     int deleteDoctorById(Long doctorId);

    /**
     * 批量删除医生信息
     */
     int deleteDoctorByIds(Long[] doctorIds);
}

