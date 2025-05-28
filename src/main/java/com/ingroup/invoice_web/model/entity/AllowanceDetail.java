package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name ="allowance_detail")
public class AllowanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long allowanceId;
    private LocalDate allowanceDate;
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

    @Embedded
    private EditRecord editRecord;

    public Long getId() {
        return id;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public Integer getAllowanceSequenceNumber() {
        return AllowanceSequenceNumber;
    }

    public void setAllowanceSequenceNumber(Integer allowanceSequenceNumber) {
        AllowanceSequenceNumber = allowanceSequenceNumber;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getOriginalDescription() {
        return originalDescription;
    }

    public void setOriginalDescription(String originalDescription) {
        this.originalDescription = originalDescription;
    }

    public Integer getOriginalSequenceNumber() {
        return originalSequenceNumber;
    }

    public void setOriginalSequenceNumber(Integer originalSequenceNumber) {
        this.originalSequenceNumber = originalSequenceNumber;
    }

    public String getOriginalInvoiceNumber() {
        return originalInvoiceNumber;
    }

    public void setOriginalInvoiceNumber(String originalInvoiceNumber) {
        this.originalInvoiceNumber = originalInvoiceNumber;
    }

    public LocalDate getOriginalInvoiceDate() {
        return originalInvoiceDate;
    }

    public void setOriginalInvoiceDate(LocalDate originalInvoiceDate) {
        this.originalInvoiceDate = originalInvoiceDate;
    }

    public LocalDate getAllowanceDate() {
        return allowanceDate;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        this.allowanceDate = allowanceDate;
    }

    public Long getAllowanceId() {
        return allowanceId;
    }

    public void setAllowanceId(Long allowanceId) {
        this.allowanceId = allowanceId;
    }
}
