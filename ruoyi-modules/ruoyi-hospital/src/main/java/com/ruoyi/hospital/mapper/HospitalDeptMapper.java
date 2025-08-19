package com.ruoyi.hospital.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.hospital.domain.HospitalDept;

/**
 * 医院科室Mapper接口
 */
@Mapper
public interface HospitalDeptMapper {
    /**
     * 查询科室列表
     */
     List<HospitalDept> selectHospitalDeptList(HospitalDept hospitalDept);

    /**
     * 查询所有科室
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
     * 删除科室
     */
     int deleteHospitalDeptById(Long deptId);

    /**
     * 批量删除科室
     */
     int deleteHospitalDeptByIds(Long[] deptIds);
}
