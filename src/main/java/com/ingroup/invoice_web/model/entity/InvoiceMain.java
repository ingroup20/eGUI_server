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
    private Boolean bondedAreaConfirm; //買受人零稅率註記
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

    private String uploadStatus; //上傳狀態

    @Embedded
    private EditRecord editRecord;

    public Long getId() {
        return id;
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

    public void setBondedAreaConfirm(Boolean bondedAreaConfirm) {
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
}
