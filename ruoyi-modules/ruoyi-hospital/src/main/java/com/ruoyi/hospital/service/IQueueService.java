package com.ruoyi.hospital.service;

import java.util.List;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.domain.Patient;

/**
 * 叫号队列信息Service接口
 *
 * @author lugod
 */
public interface IQueueService {
    /**
     * 查询队列信息列表
     *
     * @param queueInfo 队列信息
     * @return 队列信息集合
     */
    public List<QueueInfo> selectQueueList(QueueInfo queueInfo);

    /**
     * 新增队列信息
     *
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int insertQueueInfo(QueueInfo queueInfo);

    /**
     * 修改队列信息
     *
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int updateQueueInfo(QueueInfo queueInfo);

    /**
     * 删除队列信息
     *
     * @param queueId 队列信息主键
     * @return 结果
     */
    public int deleteQueueInfoById(Long queueId);

    /**
     * 批量删除队列信息
     *
     * @param queueIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQueueInfoByIds(Long[] queueIds);

    /**
     * 查询队列信息
     *
     * @param queueId 队列信息主键
     * @return 队列信息
     */
    public QueueInfo selectQueueById(Long queueId);

    /**
     * 更新队列信息
     *
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int updateQueue(QueueInfo queueInfo);

    /**
     * 新增队列信息
     *
     * @param queueInfo 队列信息
     * @return 结果
     */
    public int insertQueue(QueueInfo queueInfo);

    /**
     * 批量删除队列信息
     *
     * @param queueIds 需要删除的队列ID数组
     * @return 结果
     */
    public int deleteQueueByIds(Long[] queueIds);

    /**
     * 患者取号
     *
     * @param patient 患者信息
     * @param queueId 队列ID
     * @return 取号结果
     */
    public Patient takeNumber(Patient patient, Long queueId) throws Exception;

    /**
     * 叫号下一位患者
     *
     * @param queueId 队列ID
     * @return 被叫号的患者
     */
    public Patient callNextPatient(Long queueId) throws Exception;

    /**
     * 获取等待中的患者列表
     *
     * @param queueId 队列ID
     * @return 等待中的患者列表
     */
    public List<Patient> getWaitingPatients(Long queueId);

    /**
     * 更新队列状态
     *
     * @param queueId 队列ID
     * @param status 状态（0正常 1暂停 2关闭）
     * @return 结果
     */
    public int updateQueueStatus(Long queueId, String status);
}
