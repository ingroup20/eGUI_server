package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cancel_allowance")
public class CanceledAllowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer companyId;
    private Long allowanceId;
    private String cancelAllowanceNumber;
    private LocalDate allowanceDate;
    private String buyerId;
    private String sellerId;
    private LocalDate cancelDate;
    private LocalTime cancelTime;
    private String cancelReason;
    private String remark;
    private String uploadStatus; //上傳狀態

    @Embedded
    private EditRecord editRecord;

    public Long getAllowanceId() {
        return allowanceId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setAllowanceId(Long allowanceId) {
        this.allowanceId = allowanceId;
    }

    public String getCancelAllowanceNumber() {
        return cancelAllowanceNumber;
    }

    public void setCancelAllowanceNumber(String cancelAllowanceNumber) {
        this.cancelAllowanceNumber = cancelAllowanceNumber;
    }

    public LocalDate getAllowanceDate() {
        return allowanceDate;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        this.allowanceDate = allowanceDate;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public LocalTime getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(LocalTime cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }
}
