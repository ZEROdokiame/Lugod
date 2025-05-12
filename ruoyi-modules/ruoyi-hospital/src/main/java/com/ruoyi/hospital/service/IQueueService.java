package com.ruoyi.hospital.service;

import java.util.List;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.domain.Patient;

/**
 * 叫号队列服务接口
 * 
 * @author lugod
 */
public interface IQueueService {
    /**
     * 查询队列列表
     * 
     * @param queueInfo 队列信息
     * @return 队列集合
     */
    public List<QueueInfo> selectQueueList(QueueInfo queueInfo);

    /**
     * 根据ID查询队列信息
     * 
     * @param queueId 队列ID
     * @return 队列信息
     */
    public QueueInfo selectQueueById(Long queueId);

    /**
     * 创建队列
     * 
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int insertQueue(QueueInfo queueInfo);

    /**
     * 修改队列信息
     * 
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int updateQueue(QueueInfo queueInfo);

    /**
     * 批量删除队列
     * 
     * @param queueIds 需要删除的队列ID
     * @return 结果
     */
    public int deleteQueueByIds(Long[] queueIds);

    /**
     * 删除队列信息
     * 
     * @param queueId 队列ID
     * @return 结果
     */
    public int deleteQueueById(Long queueId);
    
    /**
     * 患者取号
     * 
     * @param patient 患者信息
     * @param queueId 队列ID
     * @return 取号结果（包含排队号码）
     */
    public Patient takeNumber(Patient patient, Long queueId);
    
    /**
     * 叫号
     * 
     * @param queueId 队列ID
     * @return 下一位患者信息
     */
    public Patient callNextPatient(Long queueId);
    
    /**
     * 查询等待中的患者列表
     * 
     * @param queueId 队列ID
     * @return 等待患者列表
     */
    public List<Patient> getWaitingPatients(Long queueId);
    
    /**
     * 暂停/恢复队列
     * 
     * @param queueId 队列ID
     * @param status 队列状态（0正常 1暂停）
     * @return 结果
     */
    public int updateQueueStatus(Long queueId, String status);
}
