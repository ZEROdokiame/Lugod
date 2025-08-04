package com.ruoyi.hospital.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
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
import com.ruoyi.hospital.domain.Storage;
import com.ruoyi.hospital.service.IStorageService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 药材库存管理Controller
 * 
 * @author lugod
 * @date 2025-08-04
 */
@RestController
@RequestMapping("/storage")
public class StorageController extends BaseController
{
    @Autowired
    private IStorageService storageService;

    /**
     * 查询药材库存管理列表
     */
    @RequiresPermissions("hospital:storage:list")
    @GetMapping("/list")
    public TableDataInfo list(Storage storage)
    {
        startPage();
        List<Storage> list = storageService.selectStorageList(storage);
        return getDataTable(list);
    }

    /**
     * 导出药材库存管理列表
     */
    @RequiresPermissions("hospital:storage:export")
    @Log(title = "药材库存管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Storage storage)
    {
        List<Storage> list = storageService.selectStorageList(storage);
        ExcelUtil<Storage> util = new ExcelUtil<Storage>(Storage.class);
        util.exportExcel(response, list, "药材库存管理数据");
    }

    /**
     * 获取药材库存管理详细信息
     */
    @RequiresPermissions("hospital:storage:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(storageService.selectStorageById(id));
    }

    /**
     * 新增药材库存管理
     */
    @RequiresPermissions("hospital:storage:add")
    @Log(title = "药材库存管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Storage storage)
    {
        return toAjax(storageService.insertStorage(storage));
    }

    /**
     * 修改药材库存管理
     */
    @RequiresPermissions("hospital:storage:edit")
    @Log(title = "药材库存管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Storage storage)
    {
        return toAjax(storageService.updateStorage(storage));
    }

    /**
     * 删除药材库存管理
     */
    @RequiresPermissions("hospital:storage:remove")
    @Log(title = "药材库存管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(storageService.deleteStorageByIds(ids));
    }
}
