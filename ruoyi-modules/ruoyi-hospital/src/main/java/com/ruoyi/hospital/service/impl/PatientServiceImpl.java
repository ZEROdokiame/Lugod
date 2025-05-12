package com.ruoyi.hospital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hospital.mapper.PatientMapper;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IPatientService;

/**
 * 患者信息Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class PatientServiceImpl implements IPatientService 
{
    @Autowired
    private PatientMapper patientMapper;

    /**
     * 查询患者信息
     * 
     * @param patientId 患者信息主键
     * @return 患者信息
     */
    @Override
    public Patient selectPatientByPatientId(Long patientId)
    {
        return patientMapper.selectPatientByPatientId(patientId);
    }

    /**
     * 查询患者信息列表
     * 
     * @param patient 患者信息
     * @return 患者信息
     */
    @Override
    public List<Patient> selectPatientList(Patient patient)
    {
        return patientMapper.selectPatientList(patient);
    }

    /**
     * 新增患者信息
     * 
     * @param patient 患者信息
     * @return 结果
     */
    @Override
    public int insertPatient(Patient patient)
    {
        return patientMapper.insertPatient(patient);
    }

    /**
     * 修改患者信息
     * 
     * @param patient 患者信息
     * @return 结果
     */
    @Override
    public int updatePatient(Patient patient)
    {
        return patientMapper.updatePatient(patient);
    }

    /**
     * 批量删除患者信息
     * 
     * @param patientIds 需要删除的患者信息主键
     * @return 结果
     */
    @Override
    public int deletePatientByPatientIds(Long[] patientIds)
    {
        return patientMapper.deletePatientByPatientIds(patientIds);
    }

    /**
     * 删除患者信息信息
     * 
     * @param patientId 患者信息主键
     * @return 结果
     */
    @Override
    public int deletePatientByPatientId(Long patientId)
    {
        return patientMapper.deletePatientByPatientId(patientId);
    }
    
    /**
     * 获取患者状态统计
     * 
     * @return 状态统计数据
     */
    @Override
    public Map<String, Long> getPatientStatusStats()
    {
        List<Map<String, Object>> statsList = patientMapper.selectPatientStatusStats();
        Map<String, Long> statsMap = new HashMap<>();
        
        // 将查询结果转换为前端所需的格式
        if (statsList != null && !statsList.isEmpty()) {
            statsMap = statsList.stream()
                .collect(Collectors.toMap(
                    map -> String.valueOf(map.get("status")),
                    map -> Long.valueOf(String.valueOf(map.get("count"))),
                    (oldValue, newValue) -> oldValue
                ));
        }
        
        return statsMap;
    }
}
