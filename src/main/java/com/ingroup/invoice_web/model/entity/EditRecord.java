package com.ingroup.invoice_web.model.entity;


import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class EditRecord {
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Integer modifyUserId;
}
