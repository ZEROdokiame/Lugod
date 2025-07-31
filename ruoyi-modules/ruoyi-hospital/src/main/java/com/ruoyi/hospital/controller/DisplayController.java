package com.ruoyi.hospital.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IQueueService;
import com.ruoyi.hospital.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 叫号显示大屏Controller
 */
@RestController
@RequestMapping("/display")
public class DisplayController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(DisplayController.class);

    @Autowired
    private IQueueService queueService;

    @Autowired
    private IPatientService patientService;

    /**
     * 获取所有科室叫号状态
     */
    @GetMapping("/status")
    public AjaxResult getDisplayStatus() {
        log.info("======开始获取所有科室叫号状态======");
        List<Map<String, Object>> result = new ArrayList<>();

        // 获取所有队列信息
        QueueInfo query = new QueueInfo();
        query.setStatus("0"); // 只查询正常状态的队列
        List<QueueInfo> queueList = queueService.selectQueueList(query);

        // 获取各队列的等待患者信息
        for (QueueInfo queue : queueList) {
            Map<String, Object> deptInfo = new HashMap<>();
            deptInfo.put("deptId", queue.getDeptId());
            deptInfo.put("deptName", queue.getDeptName());
            deptInfo.put("queueName", queue.getQueueName());

            // 获取当前就诊患者
            Patient currentQuery = new Patient();
            currentQuery.setDeptId(queue.getDeptId());
            currentQuery.setStatus("1"); // 就诊中
            List<Patient> currentList = patientService.selectPatientList(currentQuery);
            if (!currentList.isEmpty()) {
                deptInfo.put("currentPatient", currentList.get(0));
            }

            // 获取等待患者列表
            Patient waitingQuery = new Patient();
            waitingQuery.setDeptId(queue.getDeptId());
            waitingQuery.setStatus("0"); // 等待中
            List<Patient> waitingList = patientService.selectPatientList(waitingQuery);
            deptInfo.put("waitingList", waitingList);
            deptInfo.put("waitingCount", waitingList.size());

            result.add(deptInfo);
        }

        log.info("======获取叫号状态完成，共{}个科室======", result.size());
        return success(result);
    }

    /**
     * 获取指定科室叫号状态
     */
    @GetMapping("/dept/{deptId}")
    public AjaxResult getDeptDisplay(@PathVariable("deptId") Long deptId) {
        log.info("======开始获取科室{}叫号状态======", deptId);
        Map<String, Object> result = new HashMap<>();

        // 获取科室队列信息
        QueueInfo query = new QueueInfo();
        query.setDeptId(deptId);
        List<QueueInfo> queueList = queueService.selectQueueList(query);
        if (!queueList.isEmpty()) {
            QueueInfo queue = queueList.get(0);
            result.put("queueInfo", queue);

            // 获取当前就诊患者
            Patient currentQuery = new Patient();
            currentQuery.setDeptId(deptId);
            currentQuery.setStatus("1"); // 就诊中
            List<Patient> currentList = patientService.selectPatientList(currentQuery);
            if (!currentList.isEmpty()) {
                result.put("currentPatient", currentList.get(0));
            }

            // 获取等待患者列表
            Patient waitingQuery = new Patient();
            waitingQuery.setDeptId(deptId);
            waitingQuery.setStatus("0"); // 等待中
            List<Patient> waitingList = patientService.selectPatientList(waitingQuery);
            result.put("waitingList", waitingList);
            result.put("waitingCount", waitingList.size());
        }

        log.info("======获取科室叫号状态完成======");
        return success(result);
    }
}
