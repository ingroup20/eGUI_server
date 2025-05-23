package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assign_group")
public class AssignGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignId;
    private String yearMonth; //期別
    private String invoiceTrack; //兩碼英文
    private Integer companyId;
    private Integer printerId;
    private Integer startNo; //起始號碼
    private Integer usedCount; //已使用數量
    private String lastUsedNo; //最後被使用號碼
    private Integer status; //使用中

    @Embedded
    private EditRecord editRecord;

    public Integer getAssignId() {
        return assignId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getInvoiceTrack() {
        return invoiceTrack;
    }

    public void setInvoiceTrack(String invoiceTrack) {
        this.invoiceTrack = invoiceTrack;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStartNo() {
        return startNo;
    }

    public void setStartNo(Integer startNo) {
        this.startNo = startNo;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public String getLastUsedNo() {
        return lastUsedNo;
    }

    public void setLastUsedNo(String lastUsedNo) {
        this.lastUsedNo = lastUsedNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }
}
