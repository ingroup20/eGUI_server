package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name ="allowance_main")
public class AllowanceMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String allowanceNumber;
    private LocalDate AllowanceDate;
    private String seller; //統一編號
    @Embedded
    private Buyer buyer;
    private Integer allowanceType;
    private Integer originalInvoiceSellerId;
    private Integer originalInvoiceBuyerId;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;

    private String migType; //上傳migType
    private String uploadStatus; //上傳狀態

    @Embedded
    private EditRecord editRecord;

    public Long getId() {
        return id;
    }

    public String getAllowanceNumber() {
        return allowanceNumber;
    }

    public void setAllowanceNumber(String allowanceNumber) {
        this.allowanceNumber = allowanceNumber;
    }

    public LocalDate getAllowanceDate() {
        return AllowanceDate;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        AllowanceDate = allowanceDate;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Integer getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(Integer allowanceType) {
        this.allowanceType = allowanceType;
    }

    public Integer getOriginalInvoiceSellerId() {
        return originalInvoiceSellerId;
    }

    public void setOriginalInvoiceSellerId(Integer originalInvoiceSellerId) {
        this.originalInvoiceSellerId = originalInvoiceSellerId;
    }

    public Integer getOriginalInvoiceBuyerId() {
        return originalInvoiceBuyerId;
    }

    public void setOriginalInvoiceBuyerId(Integer originalInvoiceBuyerId) {
        this.originalInvoiceBuyerId = originalInvoiceBuyerId;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMigType() {
        return migType;
    }

    public void setMigType(String migType) {
        this.migType = migType;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }
}
