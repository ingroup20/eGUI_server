package com.ingroup.invoice_web.adapter.dto;

import com.ingroup.invoice_web.model.entity.Buyer;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InvoiceMainDto {
    @NotNull
    private LocalDate invoiceDate;
    @NotNull
    private LocalTime invoiceTime;
    @NotNull
    private Buyer buyer;
    private String buyerRemark; //買受人註記
    private String mainRemark; //總備註
    private String customsClearanceMark; //通關方式註記
    private String relateNumber; //相關號碼
    private String invoiceType; //發票類型07、08
    private String groupMark; //彙開註記
    private String donateMark; //捐贈註記
    private String carrierType; //載具類別號碼
    private String carrierId1; //載具顯碼
    private String carrierId2; //載具隱碼
    private String npoban; //發票捐贈對象
    private Boolean bondedAreaConfirm; //買受人零稅率註記
    private String zeroTaxRateReason; //零稅率原因

    @NotNull
    private List<InvoiceDetailDto> invoiceDetailDtoList;

    @NotNull
    private BigDecimal salesAmount; //應稅額合計
    @NotNull
    private BigDecimal freeTaxSalesAmount;
    @NotNull
    private BigDecimal zeroTaxSalesAmount;
    @NotNull
    private String taxType; //課稅別
    @NotNull
    private BigDecimal taxRate; //稅率
    @NotNull
    private BigDecimal taxAmount; //總稅額
    @NotNull
    private BigDecimal totalAmount; //總金額
    private BigDecimal discountAmount; //折扣金額
    private BigDecimal originalCurrencyAmount; //原幣金額
    private BigDecimal exchangeRate; //匯率
    private String currency; //幣別


    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalTime getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(LocalTime invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer( Buyer buyer) {
        this.buyer = buyer;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public String getMainRemark() {
        return mainRemark;
    }

    public void setMainRemark(String mainRemark) {
        this.mainRemark = mainRemark;
    }

    public String getCustomsClearanceMark() {
        return customsClearanceMark;
    }

    public void setCustomsClearanceMark(String customsClearanceMark) {
        this.customsClearanceMark = customsClearanceMark;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getGroupMark() {
        return groupMark;
    }

    public void setGroupMark(String groupMark) {
        this.groupMark = groupMark;
    }

    public String getDonateMark() {
        return donateMark;
    }

    public void setDonateMark(String donateMark) {
        this.donateMark = donateMark;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getCarrierId1() {
        return carrierId1;
    }

    public void setCarrierId1(String carrierId1) {
        this.carrierId1 = carrierId1;
    }

    public String getCarrierId2() {
        return carrierId2;
    }

    public void setCarrierId2(String carrierId2) {
        this.carrierId2 = carrierId2;
    }

    public String getNpoban() {
        return npoban;
    }

    public void setNpoban(String npoban) {
        this.npoban = npoban;
    }

    public Boolean getBondedAreaConfirm() {
        return bondedAreaConfirm;
    }

    public void setBondedAreaConfirm(Boolean bondedAreaConfirm) {
        this.bondedAreaConfirm = bondedAreaConfirm;
    }

    public String getZeroTaxRateReason() {
        return zeroTaxRateReason;
    }

    public void setZeroTaxRateReason(String zeroTaxRateReason) {
        this.zeroTaxRateReason = zeroTaxRateReason;
    }

    public  List<InvoiceDetailDto> getInvoiceDetailDtoList() {
        return invoiceDetailDtoList;
    }

    public void setInvoiceDetailDtoList( List<InvoiceDetailDto> invoiceDetailDtoList) {
        this.invoiceDetailDtoList = invoiceDetailDtoList;
    }

    public  BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount( BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public  BigDecimal getFreeTaxSalesAmount() {
        return freeTaxSalesAmount;
    }

    public void setFreeTaxSalesAmount( BigDecimal freeTaxSalesAmount) {
        this.freeTaxSalesAmount = freeTaxSalesAmount;
    }

    public  BigDecimal getZeroTaxSalesAmount() {
        return zeroTaxSalesAmount;
    }

    public void setZeroTaxSalesAmount( BigDecimal zeroTaxSalesAmount) {
        this.zeroTaxSalesAmount = zeroTaxSalesAmount;
    }

    public  String getTaxType() {
        return taxType;
    }

    public void setTaxType( String taxType) {
        this.taxType = taxType;
    }

    public  BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate( BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public  BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount( BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public  BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount( BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getOriginalCurrencyAmount() {
        return originalCurrencyAmount;
    }

    public void setOriginalCurrencyAmount(BigDecimal originalCurrencyAmount) {
        this.originalCurrencyAmount = originalCurrencyAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
