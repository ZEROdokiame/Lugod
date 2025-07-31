package com.ruoyi.hospital.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.hospital.mapper.PatientMapper;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 患者信息Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class PatientServiceImpl implements IPatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

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
    @Transactional(rollbackFor = Exception.class)
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
    public int updatePatient(Patient patient) {
        log.info("======开始修改患者信息======");

        // 如果是分诊操作（设置了科室），生成排队号
        if (patient.getDeptId() != null) {
            // 生成当天该科室的排队号
            String queueNumber = generateQueueNumber(patient.getDeptId());
            patient.setQueueNumber(queueNumber);
            // 设置状态为等待叫号
            patient.setStatus("0");
            log.info("======为患者分配排队号：{}======", queueNumber);
        }

        patient.setUpdateTime(DateUtils.getNowDate());
        int rows = patientMapper.updatePatient(patient);
        log.info("======患者信息修改完成，受影响的行数：{}======", rows);
        return rows;
    }

    /**
     * 生成排队号
     * 格式：科室编号(2位) + 日期(6位) + 序号(3位)
     */
    private String generateQueueNumber(Long deptId) {
        // 获取当前日期，格式：MMDDYY
        String dateStr = DateUtils.dateTimeNow("MMddyy");
        // 查询当天该科室最大序号
        String prefix = String.format("%02d%s", deptId, dateStr);
        int maxNum = patientMapper.getMaxQueueNumber(prefix);
        // 生成新的排队号
        return String.format("%s%03d", prefix, maxNum + 1);
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
