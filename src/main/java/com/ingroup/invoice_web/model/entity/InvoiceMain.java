package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class InvoiceMain {
    private Integer id;
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
}
