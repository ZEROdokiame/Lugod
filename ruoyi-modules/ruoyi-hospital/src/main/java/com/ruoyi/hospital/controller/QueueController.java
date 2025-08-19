package com.ruoyi.hospital.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.service.IQueueService;

/**
 * 叫号队列Controller
 *
 * @author lugod
 */
@RestController
@Slf4j
@RequestMapping("/queue")
public class QueueController extends BaseController
{

    @Autowired
    private final IQueueService queueService;

    public QueueController(IQueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * 查询队列列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QueueInfo queueInfo)
    {
        startPage();
        List<QueueInfo> list = queueService.selectQueueList(queueInfo);
        return getDataTable(list);
    }

    /**
     * 获取队列详细信息
     */
    @GetMapping(value = "/{queueId}")
    public AjaxResult getInfo(@PathVariable("queueId") Long queueId)
    {
        return success(queueService.selectQueueById(queueId));
    }

    /**
     * 新增队列
     */
    @PostMapping
    public AjaxResult add(@RequestBody QueueInfo queueInfo)
    {
        return toAjax(queueService.insertQueueInfo(queueInfo));
    }

    /**
     * 修改队列
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QueueInfo queueInfo)
    {
        return toAjax(queueService.updateQueueInfo(queueInfo));
    }

    /**
     * 删除队列
     */
    @DeleteMapping("/{queueIds}")
    public AjaxResult remove(@PathVariable Long[] queueIds)
    {
        return toAjax(queueService.deleteQueueInfoByIds(queueIds));
    }

    /**
     * 修改队列状态
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody QueueInfo queueInfo)
    {
        return toAjax(queueService.updateQueueStatus(queueInfo.getQueueId(), queueInfo.getStatus()));
    }

    /**
     * 获取指定科室的等待队列
     */
    @GetMapping("/dept/{deptId}")
    public AjaxResult getDeptQueue(@PathVariable("deptId") Long deptId)
    {
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setDeptId(deptId);
        List<QueueInfo> list = queueService.selectQueueList(queueInfo);
        return success(list.isEmpty() ? null : list.get(0));
    }
}
