package com.ruoyi.hospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.hospital.domain.QueueInfo;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IQueueService;

/**
 * 叫号队列管理 控制器
 * 
 * @author lugod
 */
@RestController
@RequestMapping("/queue")
public class QueueController extends BaseController {
    @Autowired
    private IQueueService queueService;

    /**
     * 查询队列列表
     */
    @RequiresPermissions("hospital:queue:list")
    @GetMapping("/list")
    public TableDataInfo list(QueueInfo queueInfo) {
        startPage();
        List<QueueInfo> list = queueService.selectQueueList(queueInfo);
        return getDataTable(list);
    }

    /**
     * 获取队列详细信息
     */
    @RequiresPermissions("hospital:queue:query")
    @GetMapping(value = "/{queueId}")
    public AjaxResult getInfo(@PathVariable("queueId") Long queueId) {
        return success(queueService.selectQueueById(queueId));
    }

    /**
     * 新增队列
     */
    @RequiresPermissions("hospital:queue:add")
    @Log(title = "叫号队列管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QueueInfo queueInfo) {
        return toAjax(queueService.insertQueue(queueInfo));
    }

    /**
     * 修改队列
     */
    @RequiresPermissions("hospital:queue:edit")
    @Log(title = "叫号队列管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QueueInfo queueInfo) {
        return toAjax(queueService.updateQueue(queueInfo));
    }

    /**
     * 删除队列
     */
    @RequiresPermissions("hospital:queue:remove")
    @Log(title = "叫号队列管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{queueIds}")
    public AjaxResult remove(@PathVariable Long[] queueIds) {
        return toAjax(queueService.deleteQueueByIds(queueIds));
    }
    
    /**
     * 患者取号
     */
    @Log(title = "患者取号", businessType = BusinessType.INSERT)
    @PostMapping("/take/{queueId}")
    public AjaxResult takeNumber(@RequestBody Patient patient, @PathVariable("queueId") Long queueId) {
        try {
            Patient result = queueService.takeNumber(patient, queueId);
            return success(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    
    /**
     * 叫号
     */
    @RequiresPermissions("hospital:queue:call")
    @Log(title = "叫号", businessType = BusinessType.UPDATE)
    @PostMapping("/call/{queueId}")
    public AjaxResult callNumber(@PathVariable("queueId") Long queueId) {
        try {
            Patient patient = queueService.callNextPatient(queueId);
            return success(patient);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    
    /**
     * 获取等待中的患者列表
     */
    @GetMapping("/waiting/{queueId}")
    public AjaxResult getWaitingPatients(@PathVariable("queueId") Long queueId) {
        List<Patient> patients = queueService.getWaitingPatients(queueId);
        return success(patients);
    }
    
    /**
     * 暂停/恢复队列
     */
    @RequiresPermissions("hospital:queue:edit")
    @Log(title = "队列状态管理", businessType = BusinessType.UPDATE)
    @PutMapping("/status/{queueId}/{status}")
    public AjaxResult updateStatus(@PathVariable("queueId") Long queueId, @PathVariable("status") String status) {
        return toAjax(queueService.updateQueueStatus(queueId, status));
    }
}
