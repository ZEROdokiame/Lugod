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

/**
 * 患者信息Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/patient")
public class PatientController extends BaseController
{
    @Autowired
    private IPatientService patientService;

    /**
     * 查询患者信息列表
     */
    //@RequiresPermissions("hospital:patient:list")
    @GetMapping("/list")
    public TableDataInfo list(Patient patient)
    {
        startPage();
        List<Patient> list = patientService.selectPatientList(patient);
        return getDataTable(list);
    }

    /**
     * 导出患者信息列表
     */
    //@RequiresPermissions("hospital:patient:export")
    @Log(title = "患者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Patient patient)
    {
        List<Patient> list = patientService.selectPatientList(patient);
        ExcelUtil<Patient> util = new ExcelUtil<Patient>(Patient.class);
        util.exportExcel(response, list, "患者信息数据");
    }

    /**
     * 获取患者信息详细信息
     */
    //@RequiresPermissions("hospital:patient:query")
    @GetMapping(value = "/{patientId}")
    public AjaxResult getInfo(@PathVariable("patientId") Long patientId)
    {
        return success(patientService.selectPatientByPatientId(patientId));
    }

    /**
     * 新增患者信息
     */
   // @RequiresPermissions("hospital:patient:add")
    @Log(title = "患者信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Patient patient)
    {
        return toAjax(patientService.insertPatient(patient));
    }

    /**
     * 修改患者信息
     */
    //@RequiresPermissions("hospital:patient:edit")
    @Log(title = "患者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Patient patient)
    {
        return toAjax(patientService.updatePatient(patient));
    }

    /**
     * 删除患者信息
     */
    //@RequiresPermissions("hospital:patient:remove")
    @Log(title = "患者信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patientIds}")
    public AjaxResult remove(@PathVariable Long[] patientIds)
    {
        return toAjax(patientService.deletePatientByPatientIds(patientIds));
    }
    
    /**
     * 获取患者状态统计
     */
    // @RequiresPermissions("hospital:patient:list")
    @GetMapping("/statusStats")
    public AjaxResult getStatusStats()
    {
        System.out.println("====== 患者状态统计接口被调用 ======");
        Map<String, Long> statsMap = patientService.getPatientStatusStats();
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
