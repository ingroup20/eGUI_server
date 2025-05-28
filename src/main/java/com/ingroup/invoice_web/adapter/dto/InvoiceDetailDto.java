package com.ingroup.invoice_web.adapter.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class InvoiceDetailDto {
    @NotNull
    private String description;
    @NotNull
    private BigDecimal quantity;
    private String unit;
    @NotNull
    private BigDecimal unitPrice;
    @NotNull
    private String taxType; //課稅別
    @NotNull
    private BigDecimal amount; //銷售金額
    @NotNull
    private Integer sequenceNumber; //明細序號
    private String remark; //註記
    private String relateNumber; //相關號碼


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }
}
