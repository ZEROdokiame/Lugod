package com.ruoyi.system.domain;

import java.math.BigDecimal;
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setMerchantId(Long merchantId) 
    {
        this.merchantId = merchantId;
    }

    public Long getMerchantId() 
    {
        return merchantId;
    }
    public void setChannelId(Long channelId) 
    {
        this.channelId = channelId;
    }

    public Long getChannelId() 
    {
        return channelId;
    }
    public void setOrderStatus(Long orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public Long getOrderStatus() 
    {
        return orderStatus;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("merchantId", getMerchantId())
            .append("channelId", getChannelId())
            .append("orderStatus", getOrderStatus())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
