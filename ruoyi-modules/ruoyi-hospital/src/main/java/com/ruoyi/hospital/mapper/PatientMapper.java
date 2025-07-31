package com.ruoyi.hospital.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.hospital.domain.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 患者信息Mapper接口
 * 
 * @author ruoyi
 */
@Mapper
public interface PatientMapper 
{
    /**
     * 查询患者信息
     * 
     * @param patientId 患者信息主键
     * @return 患者信息
     */
    public Patient selectPatientByPatientId(Long patientId);

    /**
     * 查询患者信息列表
     * 
     * @param patient 患者信息
     * @return 患者信息集合
     */
    public List<Patient> selectPatientList(Patient patient);

    /**
     * 新增患者信息
     * 
     * @param patient 患者信息
     * @return 结果
     */
    public int insertPatient(Patient patient);

    /**
     * 修改患者信息
     * 
     * @param patient 患者信息
     * @return 结果
     */
    public int updatePatient(Patient patient);

    /**
     * 删除患者信息
     * 
     * @param patientId 患者信息主键
     * @return 结果
     */
    public int deletePatientByPatientId(Long patientId);

    /**
     * 批量删除患者信息
     * 
     * @param patientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePatientByPatientIds(Long[] patientIds);
    
    /**
     * 统计患者状态数据
     * 
     * @return 患者状态统计数据
     */
    public List<Map<String, Object>> selectPatientStatusStats();

    /**
     * 获取指定前缀的最大排队号
     * @param prefix 排队号前缀（科室编号+日期）
     * @return 最大序号
     */
    public int getMaxQueueNumber(@Param("prefix") String prefix);
}
