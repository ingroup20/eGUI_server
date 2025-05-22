package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.math.BigDecimal;

public class ProductItem {
    private Long itemId;
    private String description;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitPrice;
    private String taxType;
    private String remark;
    private Boolean isEnabled; //啟用

    @Embedded
    private EditRecord editRecord;
}
