package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.system.service.IMerchantService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商户Controller
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController extends BaseController
{
    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IChannelService channelService;

    /**
     * 查询商户列表
     */
    @RequiresPermissions("system:merchant:list")
    @GetMapping("/list")
    public TableDataInfo list(Merchant merchant)
    {
        startPage();
        List<Merchant> list = merchantService.selectMerchantList(merchant);
        return getDataTable(list);
    }

    /**
     * 获取合适的产品
     *
     * @return 结果
     */
    @GetMapping("/merchantList")
    public R<List<Merchant>> merchantList(){
        return merchantService.getMerchantList();
    }

    /**
     * 导出商户列表
     */
    @RequiresPermissions("system:merchant:export")
    @Log(title = "商户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Merchant merchant)
    {
        List<Merchant> list = merchantService.selectMerchantList(merchant);
        ExcelUtil<Merchant> util = new ExcelUtil<Merchant>(Merchant.class);
        util.exportExcel(response, list, "商户数据");
    }

    /**
     * 获取商户详细信息
     */
    @RequiresPermissions("system:merchant:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(merchantService.selectMerchantById(id));
    }

    /**
     * 获取商户下渠道列表
     * @return
     */
    @GetMapping("/getMerchantChannelList")
    public AjaxResult getMerchantChannelList()
    {
        return success(channelService.findAllChannelList());
    }


    /**
     * 新增商户
     */
    @RequiresPermissions("system:merchant:add")
    @Log(title = "商户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Merchant merchant)
    {
        return toAjax(merchantService.insertMerchant(merchant));
    }

    /**
     * 修改商户
     */
    @RequiresPermissions("system:merchant:edit")
    @Log(title = "商户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Merchant merchant)
    {
        return toAjax(merchantService.updateMerchant(merchant));
    }

    /**
     * 删除商户
     */
    @RequiresPermissions("system:merchant:remove")
    @Log(title = "商户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(merchantService.deleteMerchantByIds(ids));
    }


    /**
     * 获取合适的产品
     */
    @GetMapping("/getMatchMerchant")
    public AjaxResult getMatchMerchantList(HttpServletRequest request){
        return merchantService.getMatchMerchantList(request);
    }
}
