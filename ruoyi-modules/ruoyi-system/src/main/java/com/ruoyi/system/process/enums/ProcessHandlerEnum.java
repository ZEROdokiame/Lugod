package com.ruoyi.system.process.enums;

import com.ruoyi.common.core.exception.ServiceException;

public enum ProcessHandlerEnum {

    H5_HANDLER("H5_HANDLER", "H5Handler"),
    HALF_HANDLER("HALF_HANDLER", "HalfHandler");
    final String code;

    final String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    ProcessHandlerEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        ProcessHandlerEnum[] processHandlerEnums = values();
        for (ProcessHandlerEnum processHandlerEnum : processHandlerEnums) {
            if (processHandlerEnum.getCode().equals(code)) {
                return processHandlerEnum.getName();
            }
        }
        String msg = String.format("请检查对应的流程处理器是否实现，编码为:%s", code);
        throw new ServiceException(msg, 500);
    }
}
