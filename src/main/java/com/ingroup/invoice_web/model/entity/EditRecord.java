package com.ingroup.invoice_web.model.entity;


import jakarta.persistence.Embeddable;

@Embeddable
public class EditRecord {
    private String createDate;
    private String modifyDate;
    private Integer modifyUserId;

    public EditRecord() {
    }

    public EditRecord(String createDate, String modifyDate, Integer modifyUserId) {
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.modifyUserId = modifyUserId;
    }

    public EditRecord(String modifyDate, Integer modifyUserId) {
        this.modifyDate = modifyDate;
        this.modifyUserId = modifyUserId;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
}
