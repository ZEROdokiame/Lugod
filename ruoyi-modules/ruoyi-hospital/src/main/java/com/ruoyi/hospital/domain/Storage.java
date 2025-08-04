package com.ruoyi.hospital.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Storage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMedicineName(String medicineName) 
    {
        this.medicineName = medicineName;
    }

    public String getMedicineName() 
    {
        return medicineName;
    }

    public void setMedicineCode(String medicineCode) 
    {
        this.medicineCode = medicineCode;
    }

    public String getMedicineCode() 
    {
        return medicineCode;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }

    public void setMinStock(Long minStock) 
    {
        this.minStock = minStock;
    }

    public Long getMinStock() 
    {
        return minStock;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setAcceptanceStatus(Integer acceptanceStatus) 
    {
        this.acceptanceStatus = acceptanceStatus;
    }

    public Integer getAcceptanceStatus() 
    {
        return acceptanceStatus;
    }

    public void setAcceptanceBy(String acceptanceBy) 
    {
        this.acceptanceBy = acceptanceBy;
    }

    public String getAcceptanceBy() 
    {
        return acceptanceBy;
    }

    public void setAcceptanceTime(Date acceptanceTime) 
    {
        this.acceptanceTime = acceptanceTime;
    }

    public Date getAcceptanceTime() 
    {
        return acceptanceTime;
    }

    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public void setSupplier(String supplier) 
    {
        this.supplier = supplier;
    }

    public String getSupplier() 
    {
        return supplier;
    }

    public void setBatchNumber(String batchNumber) 
    {
        this.batchNumber = batchNumber;
    }

    public String getBatchNumber() 
    {
        return batchNumber;
    }

    public void setProductionDate(Date productionDate) 
    {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() 
    {
        return productionDate;
    }

    public void setExpiryDate(Date expiryDate) 
    {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() 
    {
        return expiryDate;
    }

    public void setStorageLocation(String storageLocation) 
    {
        this.storageLocation = storageLocation;
    }

    public String getStorageLocation() 
    {
        return storageLocation;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("medicineName", getMedicineName())
            .append("medicineCode", getMedicineCode())
            .append("specification", getSpecification())
            .append("quantity", getQuantity())
            .append("minStock", getMinStock())
            .append("unit", getUnit())
            .append("unitPrice", getUnitPrice())
            .append("imageUrl", getImageUrl())
            .append("acceptanceStatus", getAcceptanceStatus())
            .append("acceptanceBy", getAcceptanceBy())
            .append("acceptanceTime", getAcceptanceTime())
            .append("manufacturer", getManufacturer())
            .append("supplier", getSupplier())
            .append("batchNumber", getBatchNumber())
            .append("productionDate", getProductionDate())
            .append("expiryDate", getExpiryDate())
            .append("storageLocation", getStorageLocation())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
