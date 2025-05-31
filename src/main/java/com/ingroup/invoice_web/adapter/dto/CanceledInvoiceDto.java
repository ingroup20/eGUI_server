package com.ingroup.invoice_web.adapter.dto;

import com.ingroup.invoice_web.model.entity.CanceledInvoice;
import com.ingroup.invoice_web.model.entity.EditRecord;
import com.ingroup.invoice_web.model.entity.InvoiceMain;
import com.ingroup.invoice_web.model.entity.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public CanceledInvoice generateCanceledInvoice(CanceledInvoiceDto canceledInvoiceDto, InvoiceMain invoiceMain, UserAccount userAccount) {
        CanceledInvoice canceledInvoice = new CanceledInvoice();
        canceledInvoice.setInvoiceId(canceledInvoiceDto.getInvoiceId());
        canceledInvoice.setCancelInvoiceNumber(invoiceMain.getInvoiceNumber());
        canceledInvoice.setInvoiceDate(invoiceMain.getInvoiceDate());
        canceledInvoice.setBuyerId(invoiceMain.getBuyer().getIdentifier());
        canceledInvoice.setSellerId(invoiceMain.getSeller());
        canceledInvoice.setCancelDate(canceledInvoiceDto.getCancelDate());
        canceledInvoice.setCancelTime(canceledInvoiceDto.getCancelTime());
        canceledInvoice.setCancelReason(canceledInvoiceDto.getCancelReason());
        canceledInvoice.setReturnTaxDocumentNumber(canceledInvoiceDto.getReturnTaxDocumentNumber());
        canceledInvoice.setRemark(canceledInvoiceDto.getRemark());
        canceledInvoice.setReserved1(canceledInvoiceDto.getReserved1());
        canceledInvoice.setReserved2(canceledInvoiceDto.getReserved2());
        canceledInvoice.setUploadStatus("P");
        canceledInvoice.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), userAccount.getId()));
        return canceledInvoice;
    }
}
