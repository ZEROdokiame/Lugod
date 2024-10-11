package com.ruoyi.btc.controller;

import com.ruoyi.btc.domain.ComPublicHalfDto;
import com.ruoyi.btc.service.ISysPublicAllService;
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
@RequestMapping("/all/api")
@RequiredArgsConstructor
public class PublicAllController {
    private final ISysPublicAllService sysPublicAllService;

    /**
     * 通用半流程撞库
     */
    @PostMapping("check")
    public AjaxResult upload(@RequestBody ComPublicHalfDto comPublicHalfDto)
    {
        sysPublicAllService.check(comPublicHalfDto);
        return null;
    }

    /**
     * 通用半流程撞库
     */
    @PostMapping("input")
    public AjaxResult input(@RequestBody ComPublicHalfDto comPublicHalfDto)
    {
        sysPublicAllService.input(comPublicHalfDto);
        return null;
    }

}