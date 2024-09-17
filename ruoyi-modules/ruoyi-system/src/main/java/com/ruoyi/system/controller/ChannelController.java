package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.core.domain.http.Channel;
import com.ruoyi.system.service.IChannelService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 渠道配置Controller
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/channel")
public class ChannelController extends BaseController
{
    @Autowired
    private IChannelService channelService;

    /**
     * 查询渠道配置列表
     */
    @RequiresPermissions("system:channel:list")
    @GetMapping("/list")
    public TableDataInfo list(Channel channel)
    {
        startPage();
        List<Channel> list = channelService.selectChannelList(channel);
        return getDataTable(list);
    }

    /**
     * 导出渠道配置列表
     */
    @RequiresPermissions("system:channel:export")
    @Log(title = "渠道配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Channel channel)
    {
        List<Channel> list = channelService.selectChannelList(channel);
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.exportExcel(response, list, "渠道配置数据");
    }

    /**
     * 获取渠道配置详细信息
     */
    @RequiresPermissions("system:channel:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(channelService.selectChannelById(id));
    }

    /**
     * 新增渠道配置
     */
    @RequiresPermissions("system:channel:add")
    @Log(title = "渠道配置", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult add(@RequestBody Channel channel)
    {
        return toAjax(channelService.insertChannel(channel));
    }

    /**
     * 修改渠道配置
     */
    @RequiresPermissions("system:channel:edit")
    @Log(title = "渠道配置", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public AjaxResult edit(@RequestBody Channel channel)
    {
        return toAjax(channelService.updateChannel(channel));
    }

    /**
     * 删除渠道配置
     */
    @RequiresPermissions("system:channel:remove")
    @Log(title = "渠道配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(channelService.deleteChannelByIds(ids));
    }

    /**
     * 根据渠道标识获取渠道类型
     * @param sign
     * @return
     */
    @GetMapping("/getChannelBySign")
    public AjaxResult getChannelBySign(@RequestParam("sign")String sign){
        return channelService.getChannelBySign(sign);
    }


}
