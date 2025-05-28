package com.ingroup.invoice_web.model.entity;


import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class EditRecord {
    private LocalDateTime  createDate;
    private LocalDateTime modifyDate;
    private Integer modifyUserId;

    public EditRecord() {
    }

    public EditRecord(LocalDateTime  createDate, LocalDateTime  modifyDate, Integer modifyUserId) {
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyUserId = modifyUserId;
    }

    public EditRecord(LocalDateTime  modifyDate, Integer modifyUserId) {
        this.modifyDate = modifyDate;
        this.modifyUserId = modifyUserId;
    }

    public void setCreateDate(LocalDateTime  createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(LocalDateTime  modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
}
