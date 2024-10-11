package com.ruoyi.system.process.handler;

import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.process.ProcessHandler;
import org.springframework.stereotype.Component;

@Component(value = "HalfHandler")
public class HalfHandler implements ProcessHandler {
    @Override
    public AjaxResult invoke() {
        return null;
    }
}
