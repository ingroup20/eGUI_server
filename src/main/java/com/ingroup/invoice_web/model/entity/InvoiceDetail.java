package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "invoice_detail")
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long invoiceMainId;
    private String invoiceNumber;
    private String description;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitPrice;
    private String taxType; //課稅別
    private BigDecimal salesAmount; //銷售金額
    private Integer sequenceNumber; //明細序號
    private String remark; //註記
    private String relateNumber; //相關號碼
    private Boolean isEnabled; //啟用

    @Embedded
    private EditRecord editRecord;

    public Long getId() {
        return id;
    }

    public Long getInvoiceMainId() {
        return invoiceMainId;
    }

    public void setInvoiceMainId(Long invoiceMainId) {
        this.invoiceMainId = invoiceMainId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

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

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }
}
