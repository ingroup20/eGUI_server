package com.ingroup.invoice_web.adapter.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AllowanceDetailDto {
    private Long originalInvoiceId;
    private LocalDate originalInvoiceDate;
    private String originalInvoiceNumber ;
    private Integer originalSequenceNumber ;
    private String originalDescription ;

    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal amount;
    private BigDecimal tax;
    private Integer AllowanceSequenceNumber;
    private String taxType; //課稅別

    public Long getOriginalInvoiceId() {
        return originalInvoiceId;
    }

    public void setOriginalInvoiceId(Long originalInvoiceId) {
        this.originalInvoiceId = originalInvoiceId;
    }

    public LocalDate getOriginalInvoiceDate() {
        return originalInvoiceDate;
    }

    public void setOriginalInvoiceDate(LocalDate originalInvoiceDate) {
        this.originalInvoiceDate = originalInvoiceDate;
    }

    public String getOriginalInvoiceNumber() {
        return originalInvoiceNumber;
    }

    public void setOriginalInvoiceNumber(String originalInvoiceNumber) {
        this.originalInvoiceNumber = originalInvoiceNumber;
    }

    public Integer getOriginalSequenceNumber() {
        return originalSequenceNumber;
    }

    public void setOriginalSequenceNumber(Integer originalSequenceNumber) {
        this.originalSequenceNumber = originalSequenceNumber;
    }

    public String getOriginalDescription() {
        return originalDescription;
    }

    public void setOriginalDescription(String originalDescription) {
        this.originalDescription = originalDescription;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Integer getAllowanceSequenceNumber() {
        return AllowanceSequenceNumber;
    }

    public void setAllowanceSequenceNumber(Integer allowanceSequenceNumber) {
        AllowanceSequenceNumber = allowanceSequenceNumber;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }
}
