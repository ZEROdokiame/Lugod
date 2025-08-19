package com.ruoyi.hospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.hospital.domain.HospitalDept;
import com.ruoyi.hospital.service.IHospitalDeptService;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 医院科室信息Controller
 */
@RestController
@RequestMapping("/dept")
public class HospitalDeptController extends BaseController {
    @Autowired
    private final IHospitalDeptService hospitalDeptService;

    public HospitalDeptController(IHospitalDeptService hospitalDeptService) {
        this.hospitalDeptService = hospitalDeptService;
    }

    /**
     * 获取科室列表
     */
    @PreAuthorize("@ss.hasPerms('hospital:dept:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalDept hospitalDept) {
        startPage();
        List<HospitalDept> list = hospitalDeptService.selectHospitalDeptList(hospitalDept);
        return getDataTable(list);
    }

    /**
     * 获取所有可用科室（用于下拉选择）
     */
    @PreAuthorize("@ss.hasAnyPerms({'hospital:dept:list','hospital:patient:list','hospital:queue:list'})")
    @GetMapping("/listAll")
    public AjaxResult listAll() {
        List<HospitalDept> list = hospitalDeptService.selectHospitalDeptAll();
        return success(list);
    }

    /**
     * 获取科室详细信息
     */
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable("deptId") Long deptId) {
        return success(hospitalDeptService.selectHospitalDeptById(deptId));
    }

    /**
     * 新增科室
     */
    @PostMapping
    public AjaxResult add(@RequestBody HospitalDept dept) {
        return toAjax(hospitalDeptService.insertHospitalDept(dept));
    }

    /**
     * 修改科室
     */
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalDept dept) {
        return toAjax(hospitalDeptService.updateHospitalDept(dept));
    }

    /**
     * 删除科室
     */
    @DeleteMapping("/{deptIds}")
    public AjaxResult remove(@PathVariable Long[] deptIds) {
        return toAjax(hospitalDeptService.deleteHospitalDeptByIds(deptIds));
    }
}
