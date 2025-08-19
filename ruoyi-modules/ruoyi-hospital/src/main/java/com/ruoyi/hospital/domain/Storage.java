package com.ruoyi.hospital.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 药材库存管理对象 storage
 * 
 * @author lugod
 * @date 2025-08-04
 */
@Data
public class Storage extends BaseEntity
{

    /** 库存ID */
    private Long id;

    /** 药材名称 */
    @Excel(name = "药材名称")
    private String medicineName;

    /** 药材编码 */
    @Excel(name = "药材编码")
    private String medicineCode;

    /** 药材规格 */
    @Excel(name = "药材规格")
    private String specification;

    /** 当前库存数量 */
    @Excel(name = "当前库存数量")
    private Long quantity;

    /** 最小库存预警值 */
    @Excel(name = "最小库存预警值")
    private Long minStock;

    /** 单位（盒、瓶、袋等） */
    @Excel(name = "单位", readConverterExp = "盒=、瓶、袋等")
    private String unit;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 药材图片URL */
    @Excel(name = "药材图片URL")
    private String imageUrl;

    /** 是否已验收（0-未验收 1-已验收） */
    @Excel(name = "是否已验收", readConverterExp = "0=-未验收,1=-已验收")
    private Integer acceptanceStatus;

    /** 验收人 */
    @Excel(name = "验收人")
    private String acceptanceBy;

    /** 验收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date acceptanceTime;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String manufacturer;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplier;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNumber;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /** 有效期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 存储位置 */
    @Excel(name = "存储位置")
    private String storageLocation;

    /** 状态（0-正常 1-过期 2-损坏 3-停用） */
    @Excel(name = "状态", readConverterExp = "0=-正常,1=-过期,2=-损坏,3=-停用")
    private Integer status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
