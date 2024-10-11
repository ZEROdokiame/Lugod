package com.ruoyi.common.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 概率计算
 * @Author: daisi
 * @Date: 2022/4/2 9:49
 */
@Data
@Accessors(chain = true)
public class GuestProbabilityReq implements Serializable {
    private static final long serialVersionUID = -9096451963988288187L;

    /**
     * 计划Id
     */
    private Long planId;
    /**
     * 排序价格
     */
    private BigDecimal orderPrice;
    /**
     * 概率
     */
    private Double guestProbability;
    /**
     * 计算结果概率
     */
    private Integer resultGuestProbability;
}
