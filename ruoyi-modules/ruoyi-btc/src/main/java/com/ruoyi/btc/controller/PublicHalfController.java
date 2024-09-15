package com.ruoyi.btc.controller;

import com.ruoyi.btc.service.ISysPublicHalfService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("check")
    public AjaxResult upload(MultipartFile file)
    {

        return null;
    }
}