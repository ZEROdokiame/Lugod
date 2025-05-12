package com.ruoyi.hospital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IQueueService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * 叫号队列服务实现
 * 
 * @author lugod
 */
@Service
public class QueueServiceImpl implements IQueueService {
    
    // 使用内存队列存储患者信息（实际项目中应使用数据库持久化）
    private static final ConcurrentHashMap<Long, List<Patient>> queueMap = new ConcurrentHashMap<>();
    
    // 存储队列信息（实际项目中应使用数据库持久化）
    private static final ConcurrentHashMap<Long, QueueInfo> queueInfoMap = new ConcurrentHashMap<>();
    
    // 队列ID生成器（实际项目中应使用数据库自增ID）
    private static Long queueIdGenerator = 1L;
    
    // 患者ID生成器（实际项目中应使用数据库自增ID）
    private static Long patientIdGenerator = 1L;

    /**
     * 查询队列列表
     */
    @Override
    public List<QueueInfo> selectQueueList(QueueInfo queueInfo) {
        List<QueueInfo> queueList = new ArrayList<>(queueInfoMap.values());
        return queueList;
    }

    /**
     * 根据ID查询队列信息
     */
    @Override
    public QueueInfo selectQueueById(Long queueId) {
        return queueInfoMap.get(queueId);
    }

    /**
     * 创建队列
     */
    @Override
    @Transactional
    public int insertQueue(QueueInfo queueInfo) {
        queueInfo.setQueueId(queueIdGenerator++);
        queueInfo.setCreateTime(DateUtils.getNowDate());
        queueInfo.setCurrentNumber("0");
        queueInfo.setMaxNumber("0");
        queueInfo.setStatus("0"); // 默认状态为正常
        queueInfo.setStartTime(new Date());
        
        queueInfoMap.put(queueInfo.getQueueId(), queueInfo);
        queueMap.put(queueInfo.getQueueId(), new ArrayList<>());
        return 1;
    }

    /**
     * 修改队列信息
     */
    @Override
    public int updateQueue(QueueInfo queueInfo) {
        queueInfo.setUpdateTime(DateUtils.getNowDate());
        queueInfoMap.put(queueInfo.getQueueId(), queueInfo);
        return 1;
    }

    /**
     * 批量删除队列
     */
    @Override
    public int deleteQueueByIds(Long[] queueIds) {
        for (Long queueId : queueIds) {
            queueInfoMap.remove(queueId);
            queueMap.remove(queueId);
        }
        return queueIds.length;
    }

    /**
     * 删除队列信息
     */
    @Override
    public int deleteQueueById(Long queueId) {
        queueInfoMap.remove(queueId);
        queueMap.remove(queueId);
        return 1;
    }
    
    /**
     * 患者取号
     */
    @Override
    @Transactional
    public Patient takeNumber(Patient patient, Long queueId) {
        QueueInfo queue = queueInfoMap.get(queueId);
        if (queue == null) {
            throw new RuntimeException("队列不存在");
        }
        
        if (!"0".equals(queue.getStatus())) {
            throw new RuntimeException("队列已暂停或关闭，无法取号");
        }
        
        // 生成患者ID和排队号
        patient.setPatientId(patientIdGenerator++);
        patient.setCreateTime(DateUtils.getNowDate());
        patient.setStatus("0"); // 未就诊状态
        
        // 生成排队号码
        int maxNumber = Integer.parseInt(queue.getMaxNumber());
        maxNumber++;
        String queueNumber = String.format("%03d", maxNumber);
        patient.setQueueNumber(queueNumber);
        
        // 更新队列最大号码
        queue.setMaxNumber(String.valueOf(maxNumber));
        queueInfoMap.put(queueId, queue);
        
        // 将患者加入队列
        List<Patient> patientList = queueMap.get(queueId);
        patientList.add(patient);
        
        return patient;
    }
    
    /**
     * 叫号
     */
    @Override
    @Transactional
    public Patient callNextPatient(Long queueId) {
        QueueInfo queue = queueInfoMap.get(queueId);
        if (queue == null) {
            throw new RuntimeException("队列不存在");
        }
        
        if (!"0".equals(queue.getStatus())) {
            throw new RuntimeException("队列已暂停或关闭，无法叫号");
        }
        
        List<Patient> patientList = queueMap.get(queueId);
        if (patientList.isEmpty()) {
            throw new RuntimeException("队列中没有等待的患者");
        }
        
        // 获取下一位患者
        Patient nextPatient = null;
        for (Patient patient : patientList) {
            if ("0".equals(patient.getStatus())) {
                nextPatient = patient;
                break;
            }
        }
        
        if (nextPatient == null) {
            throw new RuntimeException("队列中没有等待的患者");
        }
        
        // 更新患者状态和叫号时间
        nextPatient.setStatus("1"); // 已分诊状态
        nextPatient.setCallTime(new Date());
        
        // 更新队列当前号码
        queue.setCurrentNumber(nextPatient.getQueueNumber());
        queueInfoMap.put(queueId, queue);
        
        return nextPatient;
    }
    
    /**
     * 查询等待中的患者列表
     */
    @Override
    public List<Patient> getWaitingPatients(Long queueId) {
        List<Patient> waitingList = new ArrayList<>();
        List<Patient> patientList = queueMap.get(queueId);
        
        if (patientList != null) {
            for (Patient patient : patientList) {
                if ("0".equals(patient.getStatus())) {
                    waitingList.add(patient);
                }
            }
        }
        
        return waitingList;
    }
    
    /**
     * 暂停/恢复队列
     */
    @Override
    public int updateQueueStatus(Long queueId, String status) {
        QueueInfo queue = queueInfoMap.get(queueId);
        if (queue != null) {
            queue.setStatus(status);
            queueInfoMap.put(queueId, queue);
            return 1;
        }
        return 0;
    }
}
