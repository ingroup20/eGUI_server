package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "invoice_main")
public class InvoiceMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer companyId;
    private String yearMonth;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalTime invoiceTime;
    private String seller; //統一編號
    @Embedded
    private Buyer buyer;
    private String buyerRemark; //買受人註記
    private String mainRemark; //總備註
    private String customsClearanceMark; //通關方式註記
    private String category; //沖帳別
    private String relateNumber; //相關號碼
    private String invoiceType; //發票類型07、08
    private String groupMark; //彙開註記
    private String donateMark; //捐贈註記
    private String carrierType; //載具類別號碼
    private String carrierId1; //載具顯碼
    private String carrierId2; //載具隱碼
    private String printMark; //已列印註記
    private String npoban; //發票捐贈對象
//    @pattern value="[0-9][0-9][0-9][0-9]|AAAA
    private String randomNumber; //防偽隨機碼
    private String bondedAreaConfirm; //買受人零稅率註記
    private String zeroTaxRateReason; //零稅率原因
    private String reserved1; //保留欄位
    private String reserved2; //保留欄位

    private BigDecimal salesAmount; //應稅額合計
    private BigDecimal freeTaxSalesAmount;
    private BigDecimal zeroTaxSalesAmount;
    private String taxType; //課稅別
    private BigDecimal taxRate; //稅率
    private BigDecimal taxAmount; //總稅額
    private BigDecimal totalAmount; //總金額
    private BigDecimal discountAmount; //折扣金額
    private BigDecimal originalCurrencyAmount; //原幣金額
    private BigDecimal exchangeRate; //匯率
    private String currency; //幣別

    private Integer allowanceCount;
    private BigDecimal totalAllowanceAmount;
    private BigDecimal invoiceBalance;
    private BigDecimal taxBalance;

    private String migType; //上傳migType
    private String uploadStatus; //上傳狀態
    private String processStatus;

    @Embedded
    private EditRecord editRecord;

    public Long getId() {
        return id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceTime(LocalTime invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public void setMainRemark(String mainRemark) {
        this.mainRemark = mainRemark;
    }

    public void setCustomsClearanceMark(String customsClearanceMark) {
        this.customsClearanceMark = customsClearanceMark;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setGroupMark(String groupMark) {
        this.groupMark = groupMark;
    }

    public void setDonateMark(String donateMark) {
        this.donateMark = donateMark;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public void setCarrierId1(String carrierId1) {
        this.carrierId1 = carrierId1;
    }

    public void setCarrierId2(String carrierId2) {
        this.carrierId2 = carrierId2;
    }

    public void setPrintMark(String printMark) {
        this.printMark = printMark;
    }

    public void setNpoban(String npoban) {
        this.npoban = npoban;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public void setBondedAreaConfirm(String bondedAreaConfirm) {
        this.bondedAreaConfirm = bondedAreaConfirm;
    }

    public void setZeroTaxRateReason(String zeroTaxRateReason) {
        this.zeroTaxRateReason = zeroTaxRateReason;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public void setFreeTaxSalesAmount(BigDecimal freeTaxSalesAmount) {
        this.freeTaxSalesAmount = freeTaxSalesAmount;
    }

    public void setZeroTaxSalesAmount(BigDecimal zeroTaxSalesAmount) {
        this.zeroTaxSalesAmount = zeroTaxSalesAmount;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setOriginalCurrencyAmount(BigDecimal originalCurrencyAmount) {
        this.originalCurrencyAmount = originalCurrencyAmount;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAllowanceCount(Integer allowanceCount) {
        this.allowanceCount = allowanceCount;
    }

    public void setTotalAllowanceAmount(BigDecimal totalAllowanceAmount) {
        this.totalAllowanceAmount = totalAllowanceAmount;
    }

    public void setInvoiceBalance(BigDecimal invoiceBalance) {
        this.invoiceBalance = invoiceBalance;
    }

    public void setTaxBalance(BigDecimal taxBalance) {
        this.taxBalance = taxBalance;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public void setEditRecord(EditRecord editRecord) {
        this.editRecord = editRecord;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalTime getInvoiceTime() {
        return invoiceTime;
    }

    public String getSeller() {
        return seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public String getMainRemark() {
        return mainRemark;
    }

    public String getCustomsClearanceMark() {
        return customsClearanceMark;
    }

    public String getCategory() {
        return category;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getGroupMark() {
        return groupMark;
    }

    public String getDonateMark() {
        return donateMark;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public String getCarrierId1() {
        return carrierId1;
    }

    public String getCarrierId2() {
        return carrierId2;
    }

    public String getPrintMark() {
        return printMark;
    }

    public String getNpoban() {
        return npoban;
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public String getBondedAreaConfirm() {
        return bondedAreaConfirm;
    }

    public String getZeroTaxRateReason() {
        return zeroTaxRateReason;
    }

    public String getReserved1() {
        return reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public BigDecimal getFreeTaxSalesAmount() {
        return freeTaxSalesAmount;
    }

    public BigDecimal getZeroTaxSalesAmount() {
        return zeroTaxSalesAmount;
    }

    public String getTaxType() {
        return taxType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getOriginalCurrencyAmount() {
        return originalCurrencyAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getAllowanceCount() {
        return allowanceCount;
    }

    public BigDecimal getTotalAllowanceAmount() {
        return totalAllowanceAmount;
    }

    public BigDecimal getInvoiceBalance() {
        return invoiceBalance;
    }

    public BigDecimal getTaxBalance() {
        return taxBalance;
    }

    public String getMigType() {
        return migType;
    }

    public void setMigType(String migType) {
        this.migType = migType;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public EditRecord getEditRecord() {
        return editRecord;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
}
