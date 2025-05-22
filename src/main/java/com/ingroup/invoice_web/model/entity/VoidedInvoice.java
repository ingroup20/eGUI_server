package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoidedInvoice {
    private Long id;
    private Integer invoiceId;
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
}
