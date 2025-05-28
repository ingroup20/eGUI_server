package com.ingroup.invoice_web.adapter.dto;

import java.time.LocalDate;
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

}
