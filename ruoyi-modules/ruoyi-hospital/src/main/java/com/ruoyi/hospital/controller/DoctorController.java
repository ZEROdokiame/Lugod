package com.ruoyi.hospital.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.hospital.domain.Patient;
import com.ruoyi.hospital.service.IPatientService;
import com.ruoyi.hospital.domain.Doctor;
import com.ruoyi.hospital.service.IDoctorService;

/**
 * 医生工作台Controller
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController extends BaseController {
    @Autowired
    private IPatientService patientService;

    @Autowired
    private IDoctorService doctorService;

    /**
     * 获取医生待诊患者列表
     */
    @PreAuthorize("@ss.hasPerms('hospital:doctor:list')")
    @GetMapping("/waiting")
    public AjaxResult getWaitingPatients() {
        Patient query = new Patient();
        query.setStatus("0"); // 等待中
        List<Patient> list = patientService.selectPatientList(query);
        return success(list);
    }

    /**
     * 获取科室下的医生列表
     */
    @PreAuthorize("@ss.hasPerms('hospital:doctor:list')")
    @GetMapping("/dept/{deptId}")
    public AjaxResult getDoctorsByDept(@PathVariable Long deptId) {
        List<Doctor> list = doctorService.selectDoctorsByDeptId(deptId);
        return success(list);
    }

    /**
     * 叫号操作
     */
    @PreAuthorize("@ss.hasPerms('hospital:doctor:list')")
    @PutMapping("/call/{patientId}")
    public AjaxResult callPatient(@PathVariable Long patientId) {
        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setStatus("2"); // 更新状态为就诊中
        patient.setCallTime(new Date()); // 设置叫号时间
        patientService.updatePatient(patient);
        return success();
    }

    /**
     * 获取当前就诊患者
     */
    @PreAuthorize("@ss.hasPerms('hospital:doctor:list')")
    @GetMapping("/current")
    public AjaxResult getCurrentPatient() {
        Patient query = new Patient();
        query.setStatus("2"); // 就诊中
        List<Patient> list = patientService.selectPatientList(query);
        return success(list.isEmpty() ? null : list.get(0));
    }

    /**
     * 完成就诊
     */
    @PreAuthorize("@ss.hasPerms('hospital:doctor:list')")
    @PutMapping("/complete/{patientId}")
    public AjaxResult completePatient(@PathVariable Long patientId) {
        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setStatus("3"); // 更新状态为已就诊
        patientService.updatePatient(patient);
        return success();
    }
}
