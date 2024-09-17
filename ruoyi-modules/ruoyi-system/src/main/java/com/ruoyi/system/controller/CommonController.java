package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.http.Channel;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.system.service.IChannelService;
import com.ruoyi.system.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 渠道配置Controller
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController
{
    @Autowired
    private ICommonService commonService;


    /**
     * H5发送验证码
     * @param phone
     * @return
     */
    @GetMapping("/getChannelBySign")
    public AjaxResult getChannelBySign(@RequestParam("phone")String phone, HttpServletRequest request){
        return commonService.sendSms(phone);
    }


}
