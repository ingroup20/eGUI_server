package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="voided_invoice")
public class VoidedInvoice {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long invoiceId;
    private String voidInvoiceNumber;
    private LocalDate invoiceDate;
    private String buyerId;
    private String sellerId;
    private LocalDate voidDate;
    private LocalTime voidTime;
    private String voidReason;
    private String remark;
    private String reserved1;
    private String reserved2;

    @Embedded
    private EditRecord editRecord;

    public Long getId() {
        return id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getVoidInvoiceNumber() {
        return voidInvoiceNumber;
    }

    public void setVoidInvoiceNumber(String voidInvoiceNumber) {
        this.voidInvoiceNumber = voidInvoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public LocalDate getVoidDate() {
        return voidDate;
    }

    public void setVoidDate(LocalDate voidDate) {
        this.voidDate = voidDate;
    }

    public LocalTime getVoidTime() {
        return voidTime;
    }

    public void setVoidTime(LocalTime voidTime) {
        this.voidTime = voidTime;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }
}
