package com.ruoyi.hospital.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.hospital.domain.Patient;

/**
 * 患者信息Service接口
 * 
 * @author ruoyi
 */
public interface IPatientService 
{
    /**
     * 查询患者信息
     * 
     * @param patientId 患者信息主键
     * @return 患者信息
     */
     Patient selectPatientByPatientId(Long patientId);

    /**
     * 查询患者信息列表
     * 
     * @param patient 患者信息
     * @return 患者信息集合
     */
     List<Patient> selectPatientList(Patient patient);

    /**
     * 新增患者信息
     * 
     * @param patient 患者信息
     * @return 结果
     */
     int insertPatient(Patient patient);

    /**
     * 修改患者信息
     * 
     * @param patient 患者信息
     * @return 结果
     */
     int updatePatient(Patient patient);

    /**
     * 批量删除患者信息
     * 
     * @param patientIds 需要删除的患者信息主键集合
     * @return 结果
     */
     int deletePatientByPatientIds(Long[] patientIds);

    /**
     * 删除患者信息信息
     *
     * @param patientId 患者信息主键
     * @return 结果
     */
     int deletePatientByPatientId(Long patientId);

    /**
     * 获取患者状态统计
     * 
     * @return 状态统计数据
     */
     Map<String, Long> getPatientStatusStats();
}
