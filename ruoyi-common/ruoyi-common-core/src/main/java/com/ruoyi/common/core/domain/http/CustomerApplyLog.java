package com.ruoyi.common.core.domain.http;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 客户申请记录对象 customer_apply_log
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Data
public class CustomerApplyLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long customerId;

    /** 商户ID */
    @Excel(name = "商户ID")
    private Long merchantId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long channelId;

    /** 订单状态 0 已申请 1 注册中 2风控中 3下单中 4 下单成功 5已成交  */
    @Excel(name = "订单状态 0 已申请 1 注册中 2风控中 3下单中 4 下单成功 5已成交 ")
    private Long orderStatus;

    /** 成交金额 分 */
    @Excel(name = "成交金额 分")
    private BigDecimal price;

}
