package com.ingroup.invoice_web.adapter.dto;

import com.ingroup.invoice_web.model.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoidedInvoiceDto {

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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public String getVoidInvoiceNumber() {
        return voidInvoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public LocalDate getVoidDate() {
        return voidDate;
    }

    public LocalTime getVoidTime() {
        return voidTime;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public String getRemark() {
        return remark;
    }

    public String getReserved1() {
        return reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public VoidedInvoice generateVoidedInvoice(VoidedInvoiceDto voidedInvoiceDto, InvoiceMain invoiceMain, UserAccount userAccount) {
        VoidedInvoice voidedInvoice = new VoidedInvoice();
        voidedInvoice.setInvoiceId(voidedInvoiceDto.getInvoiceId());
        voidedInvoice.setVoidInvoiceNumber(invoiceMain.getInvoiceNumber());
        voidedInvoice.setInvoiceDate(invoiceMain.getInvoiceDate());
        voidedInvoice.setBuyerId(invoiceMain.getBuyer().getIdentifier());
        voidedInvoice.setSellerId(invoiceMain.getSeller());
        voidedInvoice.setVoidDate(voidedInvoiceDto.getVoidDate());
        voidedInvoice.setVoidTime(voidedInvoiceDto.getVoidTime());
        voidedInvoice.setVoidReason(voidedInvoiceDto.getVoidReason());
        voidedInvoice.setRemark(voidedInvoiceDto.getRemark());
        voidedInvoice.setReserved1(voidedInvoiceDto.getReserved1());
        voidedInvoice.setReserved2(voidedInvoiceDto.getReserved2());
        voidedInvoice.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), userAccount.getId()));
        return voidedInvoice;
    }
}
