package com.ruoyi.hospital.service;

import java.util.List;
import com.ruoyi.hospital.domain.HospitalDept;

/**
 * 医院科室管理Service接口
 */
public interface IHospitalDeptService {
    /**
     * 查询科室列表
     */
     List<HospitalDept> selectHospitalDeptList(HospitalDept hospitalDept);

    /**
     * 查询所有可用科室
     */
     List<HospitalDept> selectHospitalDeptAll();

    /**
     * 根据ID查询科室
     */
     HospitalDept selectHospitalDeptById(Long deptId);

    /**
     * 新增科室
     */
     int insertHospitalDept(HospitalDept dept);

    /**
     * 修改科室
     */
     int updateHospitalDept(HospitalDept dept);

    /**
     * 删除科室信息
     */
     int deleteHospitalDeptById(Long deptId);

    /**
     * 批量删除科室信息
     */
     int deleteHospitalDeptByIds(Long[] deptIds);
}
