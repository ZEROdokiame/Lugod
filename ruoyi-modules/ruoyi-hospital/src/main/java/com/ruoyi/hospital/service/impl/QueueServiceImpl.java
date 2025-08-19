package com.ruoyi.hospital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.hospital.mapper.QueueInfoMapper;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.service.IQueueService;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.mapper.PatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 叫号队列信息Service业务层处理
 *
 * @author lugod
 */
@Service
@Slf4j
public class QueueServiceImpl implements IQueueService {

    @Autowired
    private final QueueInfoMapper queueInfoMapper;

    @Autowired
    private final PatientMapper patientMapper;

    public QueueServiceImpl(QueueInfoMapper queueInfoMapper, PatientMapper patientMapper) {
        this.queueInfoMapper = queueInfoMapper;
        this.patientMapper = patientMapper;
    }

    /**
     * 查询队列信息列表
     */
    @Override
    public List<QueueInfo> selectQueueList(QueueInfo queueInfo) {
        log.info("======开始查询队列信息列表======");
        List<QueueInfo> list = queueInfoMapper.selectQueueList(queueInfo);
        log.info("======队列信息列表查询完成，共{}条记录======", list.size());
        return list;
    }

    /**
     * 新增队列信息
     */
    @Override
    public int insertQueueInfo(QueueInfo queueInfo) {
        log.info("======开始新增队列信息======");
        int rows = queueInfoMapper.insertQueueInfo(queueInfo);
        log.info("======队列信息新增完成，影响行数：{}======", rows);
        return rows;
    }

    /**
     * 修改队列信息
     */
    @Override
    public int updateQueueInfo(QueueInfo queueInfo) {
        log.info("======开始修改队列信息，队列ID：{}======", queueInfo.getQueueId());
        int rows = queueInfoMapper.updateQueueInfo(queueInfo);
        log.info("======队列信息修改完成，影响行数：{}======", rows);
        return rows;
    }

    /**
     * 删除队列信息
     */
    @Override
    public int deleteQueueInfoById(Long queueId) {
        log.info("======开始删除队列信息，队列ID：{}======", queueId);
        int rows = queueInfoMapper.deleteQueueInfoById(queueId);
        log.info("======队列信息删除完成，影响行数：{}======", rows);
        return rows;
    }

    /**
     * 批量删除队列信息
     */
    @Override
    public int deleteQueueInfoByIds(Long[] queueIds) {
        log.info("======开始批量删除队列信息，队列IDs：{}======", (Object)queueIds);
        int rows = queueInfoMapper.deleteQueueInfoByIds(queueIds);
        log.info("======队列信息批量删除完成，影响行数：{}======", rows);
        return rows;
    }

    /**
     * 查询队列信息
     */
    @Override
    public QueueInfo selectQueueById(Long queueId) {
        log.info("======开始查询队列信息，队列ID：{}======", queueId);
        QueueInfo queueInfo = queueInfoMapper.selectQueueById(queueId);
        log.info("======队列信息查询完成======");
        return queueInfo;
    }

    @Override
    public int updateQueue(QueueInfo queueInfo) {
        log.info("======开始更新队列信息======");
        return updateQueueInfo(queueInfo);
    }

    @Override
    public int insertQueue(QueueInfo queueInfo) {
        log.info("======开始新增队列信息======");
        return insertQueueInfo(queueInfo);
    }

    @Override
    public int deleteQueueByIds(Long[] queueIds) {
        log.info("======开始批量删除队列信息======");
        return deleteQueueInfoByIds(queueIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Patient takeNumber(Patient patient, Long queueId) throws Exception {
        log.info("======开始为患者取号，队列ID：{}======", queueId);

        // 获取队列信息
        QueueInfo queue = selectQueueById(queueId);
        if (queue == null) {
            throw new Exception("队列不存在");
        }
        if (!"0".equals(queue.getStatus())) {
            throw new Exception("队列已暂停或关闭");
        }

        // 生成排队号
        String queueNumber = generateQueueNumber(queue);
        patient.setQueueNumber(queueNumber);
        patient.setStatus("0"); // 设置为等待状态
        patient.setDeptId(queue.getDeptId());

        // 保存患者信息
        patientMapper.insertPatient(patient);

        log.info("======患者取号完成，排队号：{}======", queueNumber);
        return patient;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Patient callNextPatient(Long queueId) throws Exception {
        log.info("======开始叫号，队列ID：{}======", queueId);

        // 获取队列信息
        QueueInfo queue = selectQueueById(queueId);
        if (queue == null) {
            throw new Exception("队列不存在");
        }
        if (!"0".equals(queue.getStatus())) {
            throw new Exception("队列已暂停或关闭");
        }

        // 获取下一位等待的患者
        Patient query = new Patient();
        query.setDeptId(queue.getDeptId());
        query.setStatus("0"); // 等待状态
        List<Patient> waitingList = patientMapper.selectPatientList(query);

        if (waitingList.isEmpty()) {
            throw new Exception("当前没有等待的患者");
        }

        // 获取第一位等待的患者
        Patient nextPatient = waitingList.get(0);
        nextPatient.setStatus("1"); // 设置为正在就诊状态
        nextPatient.setCallTime(new Date()); // 设置叫号时间
        patientMapper.updatePatient(nextPatient);

        log.info("======叫号完成，患者ID：{}======", nextPatient.getPatientId());
        return nextPatient;
    }

    @Override
    public List<Patient> getWaitingPatients(Long queueId) {
        log.info("======开始查询等待患者列表，队列ID：{}======", queueId);

        // 获取队列信息
        QueueInfo queue = selectQueueById(queueId);
        if (queue == null) {
            return new ArrayList<>();
        }

        // 查询等待中的患者
        Patient query = new Patient();
        query.setDeptId(queue.getDeptId());
        query.setStatus("0"); // 等待状态
        List<Patient> waitingList = patientMapper.selectPatientList(query);

        log.info("======等待患者列表查询完成，共{}人======", waitingList.size());
        return waitingList;
    }

    @Override
    public int updateQueueStatus(Long queueId, String status) {
        log.info("======开始更新队列状态，队列ID：{}，状态：{}======", queueId, status);
        QueueInfo queue = new QueueInfo();
        queue.setQueueId(queueId);
        queue.setStatus(status);
        return updateQueueInfo(queue);
    }

    /**
     * 生成排队号
     */
    private String generateQueueNumber(QueueInfo queue) {
        // 生成格式：科室编号(2位) + 日期(6位) + 序号(3位)
        String dateStr = DateUtils.dateTimeNow("MMddyy");
        String prefix = String.format("%02d%s", queue.getDeptId(), dateStr);

        // 查询当天该科室最大序号
        int maxNum = patientMapper.getMaxQueueNumber(prefix);

        // 生成新的排队号
        return String.format("%s%03d", prefix, maxNum + 1);
    }
}
