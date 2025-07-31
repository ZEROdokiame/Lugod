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
    public List<HospitalDept> selectHospitalDeptList(HospitalDept hospitalDept);

    /**
     * 查询所有科室
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
     * 删除科室
     */
    public int deleteHospitalDeptById(Long deptId);

    /**
     * 批量删除科室
     */
    public int deleteHospitalDeptByIds(Long[] deptIds);
}
