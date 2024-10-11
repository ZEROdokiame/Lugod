package com.ruoyi.system.process;

import com.ruoyi.common.core.web.domain.AjaxResult;

/**
 *根据流程类型 执行不同的处理器
 */
public interface ProcessHandler {


    AjaxResult invoke();
}
