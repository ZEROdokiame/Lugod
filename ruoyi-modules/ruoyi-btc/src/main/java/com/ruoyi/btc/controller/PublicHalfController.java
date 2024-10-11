package com.ruoyi.btc.controller;

import com.ruoyi.btc.domain.ComPublicHalfDto;
import com.ruoyi.btc.service.ISysPublicHalfService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 半流程API接口写这里
 * 
 * @author z
 */
@RestController
@Slf4j
@RequestMapping("/half/api")
@RequiredArgsConstructor
public class PublicHalfController{
    private final ISysPublicHalfService sysPublicHalfService;

    /**
     * 通用半流程撞库
     */
    @PostMapping("/check")
    public AjaxResult upload(@RequestBody ComPublicHalfDto comPublicHalfDto)
    {
        return sysPublicHalfService.check(comPublicHalfDto);
    }

    /**
     * 通用半流程撞库
     */
    @PostMapping("/input")
    public AjaxResult input(@RequestBody ComPublicHalfDto comPublicHalfDto)
    {
        return sysPublicHalfService.input(comPublicHalfDto);
    }


    /**
     * 通用半流程撞库
     */
    @GetMapping("/checkOrder")
    public AjaxResult checkOrder(String phoneMd5,String channelSign)
    {

        return sysPublicHalfService.checkOrder(phoneMd5,channelSign);
    }
}