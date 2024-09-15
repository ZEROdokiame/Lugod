package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.domain.CustomerApplyLog;
import com.ruoyi.system.service.ICustomerApplyLogService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 客户申请记录Controller
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/log")
public class CustomerApplyLogController extends BaseController
{
    @Autowired
    private ICustomerApplyLogService customerApplyLogService;

    /**
     * 查询客户申请记录列表
     */
    @RequiresPermissions("system:log:list")
    @GetMapping("/list")
    public TableDataInfo list(CustomerApplyLog customerApplyLog)
    {
        startPage();
        List<CustomerApplyLog> list = customerApplyLogService.selectCustomerApplyLogList(customerApplyLog);
        return getDataTable(list);
    }

    /**
     * 导出客户申请记录列表
     */
    @RequiresPermissions("system:log:export")
    @Log(title = "客户申请记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerApplyLog customerApplyLog)
    {
        List<CustomerApplyLog> list = customerApplyLogService.selectCustomerApplyLogList(customerApplyLog);
        ExcelUtil<CustomerApplyLog> util = new ExcelUtil<CustomerApplyLog>(CustomerApplyLog.class);
        util.exportExcel(response, list, "客户申请记录数据");
    }

    /**
     * 获取客户申请记录详细信息
     */
    @RequiresPermissions("system:log:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(customerApplyLogService.selectCustomerApplyLogById(id));
    }

    /**
     * 新增客户申请记录
     */
    @RequiresPermissions("system:log:add")
    @Log(title = "客户申请记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerApplyLog customerApplyLog)
    {
        return toAjax(customerApplyLogService.insertCustomerApplyLog(customerApplyLog));
    }

    /**
     * 修改客户申请记录
     */
    @RequiresPermissions("system:log:edit")
    @Log(title = "客户申请记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerApplyLog customerApplyLog)
    {
        return toAjax(customerApplyLogService.updateCustomerApplyLog(customerApplyLog));
    }

    /**
     * 删除客户申请记录
     */
    @RequiresPermissions("system:log:remove")
    @Log(title = "客户申请记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerApplyLogService.deleteCustomerApplyLogByIds(ids));
    }
}
