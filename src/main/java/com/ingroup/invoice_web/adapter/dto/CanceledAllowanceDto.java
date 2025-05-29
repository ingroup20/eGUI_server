package com.ingroup.invoice_web.adapter.dto;

import com.ingroup.invoice_web.model.entity.AllowanceMain;
import com.ingroup.invoice_web.model.entity.CanceledAllowance;
import com.ingroup.invoice_web.model.entity.EditRecord;
import com.ingroup.invoice_web.model.entity.UserAccount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CanceledAllowanceDto {
    private Long allowanceId;
    private String cancelAllowanceNumber;
    private LocalDate allowanceDate;
    private LocalDate cancelDate;
    private LocalTime cancelTime;
    private String cancelReason;
    private String remark;
    private String sourceMigType;

    public Long getAllowanceId() {
        return allowanceId;
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

    public String getSourceMigType() {
        return sourceMigType;
    }

    public void setSourceMigType(String sourceMigType) {
        this.sourceMigType = sourceMigType;
    }

    public CanceledAllowance generateCanceledAllowance(CanceledAllowanceDto canceledAllowanceDto, AllowanceMain allowanceMain, UserAccount userAccount) {
        CanceledAllowance canceledAllowance = new CanceledAllowance();
        canceledAllowance.setCompanyId(userAccount.getCompanyId());
        canceledAllowance.setAllowanceId(canceledAllowanceDto.getAllowanceId());
        canceledAllowance.setCancelAllowanceNumber(canceledAllowanceDto.getCancelAllowanceNumber());
        canceledAllowance.setAllowanceDate(canceledAllowanceDto.getAllowanceDate());
        canceledAllowance.setBuyerId(allowanceMain.getBuyer().getIdentifier());
        canceledAllowance.setSellerId(allowanceMain.getSeller());
        canceledAllowance.setCancelDate(canceledAllowanceDto.getCancelDate());
        canceledAllowance.setCancelTime(canceledAllowanceDto.getCancelTime());
        canceledAllowance.setCancelReason(canceledAllowanceDto.getCancelReason());
        canceledAllowance.setRemark(canceledAllowanceDto.getRemark());
        canceledAllowance.setEditRecord(new EditRecord(LocalDateTime.now(),LocalDateTime.now(),userAccount.getId()));
        return canceledAllowance;
    }
}
