package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.math.BigDecimal;

public class InvoiceDetail {
    private Integer id;
    private Integer invoiceId;
    private String invoiceNumber;
    private String description;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitPrice;
    private String taxType; //課稅別
    private BigDecimal salesAmount; //銷售金額
    private Integer sequenceNumber; //明細序號
    private String remark; //註記
    private String relateNumber; //相關號碼
    private Boolean isEnabled; //啟用

    @Embedded
    private EditRecord editRecord;
}
