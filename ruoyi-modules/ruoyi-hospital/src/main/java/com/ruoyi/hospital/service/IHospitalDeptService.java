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
    public List<HospitalDept> selectHospitalDeptList(HospitalDept hospitalDept);

    /**
     * 查询所有可用科室
     */
    public List<HospitalDept> selectHospitalDeptAll();

    /**
     * 根据ID查询科室
     */
    public HospitalDept selectHospitalDeptById(Long deptId);

    /**
     * 新增科室
     */
    public int insertHospitalDept(HospitalDept dept);

    /**
     * 修改科室
     */
    public int updateHospitalDept(HospitalDept dept);

    /**
     * 删除科室信息
     */
    public int deleteHospitalDeptById(Long deptId);

    /**
     * 批量删除科室信息
     */
    public int deleteHospitalDeptByIds(Long[] deptIds);
}
