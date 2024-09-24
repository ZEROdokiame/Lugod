package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.core.domain.http.Customer;
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

    @Autowired
    private IMerchantService merchantService;

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
     * 通过手机号MD5查询用户信息
     *
     * @param phoneMD5 用户名
     * @return 结果
     */
    @GetMapping("/getByMd5")
    public R<Customer> getCustomerInfoByPhoneMd5(@RequestParam("phoneMD5")String phoneMD5){
        return customerService.selectByPhoneMd5(phoneMD5);
    }

    /**
     * 通过手机号MD5更新用户信息
     *
     * @param customer 用户
     * @return 结果
     */
    @PostMapping("/updateByPhoneMd5")
    public R updateByPhoneMd5(@RequestBody Customer customer ,@RequestHeader(SecurityConstants.FROM_SOURCE) String source){
        return customerService.updateByPhoneMd5(customer);
    }

    /**
     * 新增客户信息
     * @return
     */
    @PostMapping("/addNewCustomer")
    public R add(@RequestBody Customer customer,@RequestHeader(SecurityConstants.FROM_SOURCE) String source){
        boolean save = customerService.save(customer);
        if (save){
            return R.ok();
        }
        return R.fail();
    }
    /**
     * 新增客户信息
     */
    @RequiresPermissions("system:customer:add")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult add(@RequestBody Customer customer)
    {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户信息
     */
    @RequiresPermissions("system:customer:edit")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
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

    /**
     * 获取用户token
     * @param phone
     * @return
     */
    @GetMapping("/getCustomerToken")
    public String getCustomerToken(@RequestParam("phone") String phone,@RequestParam("channelId")Long channelId) {
        return customerService.getCustomerToken(phone);
    }


    /**
     * H5用户登录
     */
    @GetMapping("/customerLogin")
    public AjaxResult customerLogin(@RequestParam("phone")String phone,@RequestParam("code")Integer code,HttpServletRequest request){
        return customerService.customerLogin(phone,code,request);
    }

    /**
     * H5保存用户留资信息
     */
    @PostMapping("/saveCustomerInfo")
    public AjaxResult saveCustomerInfo(@RequestBody Customer customer, HttpServletRequest request){
        return customerService.saveCustomerInfo(customer,request);
    }



    /**
     * 获取商户下渠道列表
     * @return
     */
    @RequestMapping(value = "/getAllMerchantList", method = RequestMethod.GET)
    public AjaxResult getAllMerchantList()
    {
        return success(merchantService.findAllMerchantList());
    }
}
