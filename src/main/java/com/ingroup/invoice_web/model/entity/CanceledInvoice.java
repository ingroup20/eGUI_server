package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.time.LocalDate;
import java.time.LocalTime;

public class CanceledInvoice {
    private Integer id;
    private Integer invoiceId;
    private String cancelInvoiceNumber;
    private LocalDate invoiceDate;
    private String buyerId;
    private String sellerId;
    private LocalDate cancelDate;
    private LocalTime cancelTime;
    private String cancelReason;
    private String returnTaxDocumentNumber; //專案作廢核准文號
    private String remark;
    private String reserved1;
    private String reserved2;

    @Embedded
    private EditRecord editRecord;
}
