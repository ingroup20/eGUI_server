package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.time.LocalDate;
import java.time.LocalTime;

public class CanceledAllowance {
    private Long id;
    private Integer allowanceId;
    private String cancelAllowanceNumber;
    private LocalDate allowanceDate;
    private String buyerId;
    private String sellerId;
    private LocalDate cancelDate;
    private LocalTime cancelTime;
    private String cancelReason;
    private String remark;

    @Embedded
    private EditRecord editRecord;
}
