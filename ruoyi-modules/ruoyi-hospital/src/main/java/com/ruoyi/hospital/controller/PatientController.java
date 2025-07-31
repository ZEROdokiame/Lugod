package com.ruoyi.hospital.controller;

import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IPatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 患者信息Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/patient")
public class PatientController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private IPatientService patientService;

    /**
     * 查询患者信息列表
     */
    //@RequiresPermissions("hospital:patient:list")
    @GetMapping("/list")
    public TableDataInfo list(Patient patient)
    {
        log.info("======开始查询患者信息列表======");
        startPage();
        List<Patient> list = patientService.selectPatientList(patient);
        TableDataInfo dataTable = getDataTable(list);
        log.info("======患者信息列表查询完成，共返回 {} 条记录======", list.size());
        return dataTable;
    }

    /**
     * 导出患者信息列表
     */
    //@RequiresPermissions("hospital:patient:export")
    @Log(title = "患者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Patient patient)
    {
        log.info("======开始导出患者信息列表======");
        List<Patient> list = patientService.selectPatientList(patient);
        ExcelUtil<Patient> util = new ExcelUtil<Patient>(Patient.class);
        util.exportExcel(response, list, "患者信息数据");
        log.info("======患者信息列表导出完成，共导出 {} 条记录======", list.size());
    }

    /**
     * 获取患者信息详细信息
     */
    //@RequiresPermissions("hospital:patient:query")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId)
    {
        log.info("======开始查询患者详细信息，患者ID：{}======", patientId);
        AjaxResult result = success(patientService.selectPatientByPatientId(patientId));
        log.info("======患者详细信息查询完成======");
        return result;
    }

    /**
     * 新增患者信息
     */
   // @RequiresPermissions("hospital:patient:add")
    @Log(title = "患者信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Patient patient)
    {
        log.info("======开始新增患者信息======");
        int rows = patientService.insertPatient(patient);
        log.info("======患者信息新增完成，受影响的行数：{}======", rows);
        return toAjax(rows);
    }

    /**
     * 修改患者信息
     */
    //@RequiresPermissions("hospital:patient:edit")
    @Log(title = "患者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Patient patient)
    {
        log.info("======开始修改患者信息，患者ID：{}======", patient.getPatientId());
        int rows = patientService.updatePatient(patient);
        log.info("======患者信息修改完成，受影响的行数：{}======", rows);
        return toAjax(rows);
    }

    /**
     * 删除患者信息
     */
    //@RequiresPermissions("hospital:patient:remove")
    @Log(title = "患者信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        log.info("======开始删除患者信息，患者ID：{}======", (Object)patientIds);
        int rows = patientService.deletePatientByPatientIds(patientIds);
        log.info("======患者信息删除完成，受影响的行数：{}======", rows);
        return toAjax(rows);
    }
    
    /**
     * 获取患者状态统计
     */
    // @RequiresPermissions("hospital:patient:list")
    @GetMapping("/statusStats")
    public AjaxResult getStatusStats()
    {
        log.info("======开始统计患者状态信息======");
        Map<String, Long> statsMap = patientService.getPatientStatusStats();
        log.info("======患者状态统计完成======");
        return success(statsMap);
    }

    /**
     * 获取患者列表（用于前端测试）
     */
    @GetMapping("/test")
    public AjaxResult test()
    {
        System.out.println("====== 测试接口被调用 ======");
        return success("测试接口调用成功");
    }
}
