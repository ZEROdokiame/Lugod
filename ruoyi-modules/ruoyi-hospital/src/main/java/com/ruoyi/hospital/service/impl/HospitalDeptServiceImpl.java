package com.ruoyi.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hospital.mapper.HospitalDeptMapper;
import com.ruoyi.hospital.domain.HospitalDept;
import com.ruoyi.hospital.service.IHospitalDeptService;

/**
 * 医院科室管理Service业务层处理
 */
@Service
public class HospitalDeptServiceImpl implements IHospitalDeptService {
    @Autowired
    private final HospitalDeptMapper hospitalDeptMapper;

    public HospitalDeptServiceImpl(HospitalDeptMapper hospitalDeptMapper) {
        this.hospitalDeptMapper = hospitalDeptMapper;
    }

    @Override
    public List<HospitalDept> selectHospitalDeptList(HospitalDept hospitalDept) {
        return hospitalDeptMapper.selectHospitalDeptList(hospitalDept);
    }

    @Override
    public List<HospitalDept> selectHospitalDeptAll() {
        return hospitalDeptMapper.selectHospitalDeptAll();
    }

    @Override
    public HospitalDept selectHospitalDeptById(Long deptId) {
        return hospitalDeptMapper.selectHospitalDeptById(deptId);
    }

    @Override
    public int insertHospitalDept(HospitalDept dept) {
        return hospitalDeptMapper.insertHospitalDept(dept);
    }

    @Override
    public int updateHospitalDept(HospitalDept dept) {
        return hospitalDeptMapper.updateHospitalDept(dept);
    }

    @Override
    public int deleteHospitalDeptById(Long deptId) {
        return hospitalDeptMapper.deleteHospitalDeptById(deptId);
    }

    @Override
    public int deleteHospitalDeptByIds(Long[] deptIds) {
        return hospitalDeptMapper.deleteHospitalDeptByIds(deptIds);
    }
}
