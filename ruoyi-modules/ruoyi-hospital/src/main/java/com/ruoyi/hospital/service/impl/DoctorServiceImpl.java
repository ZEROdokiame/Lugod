package com.ruoyi.hospital.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hospital.mapper.DoctorMapper;
import com.ruoyi.hospital.domain.Doctor;
import com.ruoyi.hospital.service.IDoctorService;

@Service
public class DoctorServiceImpl implements IDoctorService {
    @Autowired
    private final DoctorMapper doctorMapper;

    public DoctorServiceImpl(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public List<Doctor> selectDoctorsByDeptId(Long deptId) {
        return doctorMapper.selectDoctorsByDeptId(deptId);
    }

    @Override
    public Doctor selectDoctorById(Long doctorId) {
        return doctorMapper.selectDoctorById(doctorId);
    }

    @Override
    public int insertDoctor(Doctor doctor) {
        return doctorMapper.insertDoctor(doctor);
    }

    @Override
    public int updateDoctor(Doctor doctor) {
        return doctorMapper.updateDoctor(doctor);
    }

    @Override
    public int deleteDoctorByIds(Long[] doctorIds) {
        return doctorMapper.deleteDoctorByIds(doctorIds);
    }

    @Override
    public int deleteDoctorById(Long doctorId) {
        return doctorMapper.deleteDoctorById(doctorId);
    }
}
