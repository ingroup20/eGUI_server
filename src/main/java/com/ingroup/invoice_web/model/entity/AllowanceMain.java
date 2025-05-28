package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AllowanceMain {
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
}
