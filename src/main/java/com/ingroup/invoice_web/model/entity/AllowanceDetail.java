package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AllowanceDetail {
    private Long id;
    private Integer allowanceId;
    private LocalDate allowance_date;
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
}
