package com.ruoyi.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.hospital.service.IQueueService;
import com.ruoyi.hospital.domain.QueueInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 叫号显示屏控制器
 * 
 * @author lugod
 */
@RestController
@RequestMapping("/display")
public class DisplayController extends BaseController {
    @Autowired
    private IQueueService queueService;

    /**
     * 获取所有队列的当前叫号状态
     */
    @GetMapping("/status")
    public AjaxResult getDisplayStatus() {
        QueueInfo query = new QueueInfo();
        // 只查询正常状态的队列
        query.setStatus("0");
        List<QueueInfo> queueList = queueService.selectQueueList(query);
        
        // 构建显示数据
        Map<String, Object> displayData = new HashMap<>();
        displayData.put("queues", queueList);
        displayData.put("timestamp", System.currentTimeMillis());
        
        return success(displayData);
    }
    
    /**
     * 获取指定科室的叫号状态
     */
    @GetMapping("/dept/{deptId}")
    public AjaxResult getDeptDisplayStatus(@PathVariable("deptId") Long deptId) {
        QueueInfo query = new QueueInfo();
        query.setDeptId(deptId);
        query.setStatus("0"); // 只查询正常状态的队列
        List<QueueInfo> queueList = queueService.selectQueueList(query);
        
        // 构建显示数据
        Map<String, Object> displayData = new HashMap<>();
        displayData.put("queues", queueList);
        displayData.put("timestamp", System.currentTimeMillis());
        
        return success(displayData);
    }
}
