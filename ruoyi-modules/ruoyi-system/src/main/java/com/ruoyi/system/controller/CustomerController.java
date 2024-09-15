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
import com.ruoyi.system.domain.Customer;
import com.ruoyi.system.service.ICustomerService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 客户信息Controller
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController
{
    @Autowired
    private ICustomerService customerService;

    /**
     * 查询客户信息列表
     */
    @RequiresPermissions("system:customer:list")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer)
    {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @RequiresPermissions("system:customer:export")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Customer customer)
    {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        util.exportExcel(response, list, "客户信息数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @RequiresPermissions("system:customer:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(customerService.selectCustomerById(id));
    }

    /**
     * 新增客户信息
     */
    @RequiresPermissions("system:customer:add")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Customer customer)
    {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户信息
     */
    @RequiresPermissions("system:customer:edit")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Customer customer)
    {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户信息
     */
    @RequiresPermissions("system:customer:remove")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }
}
