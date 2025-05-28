package com.ingroup.invoice_web.adapter.dto;

import com.ingroup.invoice_web.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AllowanceMainDto {

    private String allowanceNumber;
    private LocalDate AllowanceDate;
    private String seller; //統一編號
    private Buyer buyer;
    private Integer allowanceType;
    private Integer originalInvoiceSellerId;
    private Integer originalInvoiceBuyerId;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String sourceMigType;
    private List<AllowanceDetailDto> allowanceDetails;

    public String getAllowanceNumber() {
        return allowanceNumber;
    }

    public void setAllowanceNumber(String allowanceNumber) {
        this.allowanceNumber = allowanceNumber;
    }

    public LocalDate getAllowanceDate() {
        return AllowanceDate;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        AllowanceDate = allowanceDate;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Integer getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(Integer allowanceType) {
        this.allowanceType = allowanceType;
    }

    public Integer getOriginalInvoiceSellerId() {
        return originalInvoiceSellerId;
    }

    public void setOriginalInvoiceSellerId(Integer originalInvoiceSellerId) {
        this.originalInvoiceSellerId = originalInvoiceSellerId;
    }

    public Integer getOriginalInvoiceBuyerId() {
        return originalInvoiceBuyerId;
    }

    public void setOriginalInvoiceBuyerId(Integer originalInvoiceBuyerId) {
        this.originalInvoiceBuyerId = originalInvoiceBuyerId;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSourceMigType() {
        return sourceMigType;
    }

    public void setSourceMigType(String sourceMigType) {
        this.sourceMigType = sourceMigType;
    }

    public List<AllowanceDetailDto> getAllowanceDetails() {
        return allowanceDetails;
    }

    public void setAllowanceDetails(List<AllowanceDetailDto> allowanceDetails) {
        this.allowanceDetails = allowanceDetails;
    }


    public AllowanceMain generateAllowanceMain(AllowanceMainDto allowanceMainDto, UserAccount userAccount, String migType) {
        AllowanceMain allowanceMain = new AllowanceMain();
        allowanceMain.setAllowanceNumber(allowanceMainDto.getAllowanceNumber());
        allowanceMain.setAllowanceDate(allowanceMainDto.getAllowanceDate());
        allowanceMain.setSeller(allowanceMainDto.getSeller());
        allowanceMain.setBuyer(allowanceMainDto.getBuyer());
        allowanceMain.setAllowanceType(allowanceMainDto.getAllowanceType());
        allowanceMain.setOriginalInvoiceSellerId(allowanceMainDto.getOriginalInvoiceSellerId());
        allowanceMain.setOriginalInvoiceBuyerId(allowanceMainDto.getOriginalInvoiceBuyerId());
        allowanceMain.setTaxAmount(allowanceMainDto.getTaxAmount());
        allowanceMain.setTotalAmount(allowanceMainDto.getTotalAmount());
        allowanceMain.setMigType(migType);
        allowanceMain.setUploadStatus("上傳中");
        allowanceMain.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), userAccount.getId()));
        return allowanceMain;
    }

    public List<AllowanceDetail> generateAllowanceDetail(AllowanceMainDto allowanceMainDto, UserAccount userAccount, Long allowanceId) {
        List<AllowanceDetail> allowanceDetailList = new ArrayList<>();
        for(AllowanceDetailDto allowanceDetailDto : allowanceMainDto.getAllowanceDetails()) {
            AllowanceDetail allowanceDetail= new AllowanceDetail();
            allowanceDetail.setAllowanceId(allowanceId);
            allowanceDetail.setAllowanceDate(allowanceMainDto.getAllowanceDate());
            allowanceDetail.setOriginalInvoiceDate(allowanceDetailDto.getOriginalInvoiceDate());
            allowanceDetail.setOriginalInvoiceNumber(allowanceDetailDto.getOriginalInvoiceNumber());
            allowanceDetail.setOriginalSequenceNumber(allowanceDetailDto.getOriginalSequenceNumber());
            allowanceDetail.setOriginalDescription(allowanceDetailDto.getOriginalDescription());
            allowanceDetail.setQuantity(allowanceDetailDto.getQuantity());
            allowanceDetail.setUnit(allowanceDetailDto.getUnit());
            allowanceDetail.setUnitPrice(allowanceDetailDto.getUnitPrice());
            allowanceDetail.setAmount(allowanceDetailDto.getAmount());
            allowanceDetail.setTax(allowanceDetailDto.getTax());
            allowanceDetail.setAllowanceSequenceNumber(allowanceDetailDto.getAllowanceSequenceNumber());
            allowanceDetail.setTaxType(allowanceDetailDto.getTaxType());
            allowanceDetail.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), userAccount.getId()));

            allowanceDetailList.add(allowanceDetail);
        }
        return allowanceDetailList;
    }


}
