package com.ingroup.invoice_web.adapter.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CanceledInvoiceDto {
    private Long invoiceId;
    private String cancelInvoiceNumber;
    private LocalDate cancelDate;
    private LocalTime cancelTime;
    private String cancelReason;
    private String returnTaxDocumentNumber; //專案作廢核准文號
    private String remark;
    private String reserved1;
    private String reserved2;
    private String sourceMigType;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCancelInvoiceNumber() {
        return cancelInvoiceNumber;
    }

    public void setCancelInvoiceNumber(String cancelInvoiceNumber) {
        this.cancelInvoiceNumber = cancelInvoiceNumber;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public LocalTime getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(LocalTime cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getReturnTaxDocumentNumber() {
        return returnTaxDocumentNumber;
    }

    public void setReturnTaxDocumentNumber(String returnTaxDocumentNumber) {
        this.returnTaxDocumentNumber = returnTaxDocumentNumber;
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

    public String getSourceMigType() {
        return sourceMigType;
    }
}
