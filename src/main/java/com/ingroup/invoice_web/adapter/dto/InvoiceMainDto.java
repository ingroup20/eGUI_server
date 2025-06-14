package com.ingroup.invoice_web.adapter.dto;

import com.ingroup.invoice_web.exception.runtime.ValidatedException;
import com.ingroup.invoice_web.model.entity.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private String printMark; //電子發票證明聯已列印註記
    private String npoban; //發票捐贈對象
    private String bondedAreaConfirm; //買受人零稅率註記
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
    private String migType;


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

    public void setBuyer(Buyer buyer) {
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

    public String getPrintMark() {
        return printMark;
    }

    public void setPrintMark(String printMark) {
        this.printMark = printMark;
    }

    public String getNpoban() {
        return npoban;
    }

    public void setNpoban(String npoban) {
        this.npoban = npoban;
    }

    public String getBondedAreaConfirm() {
        return bondedAreaConfirm;
    }

    public void setBondedAreaConfirm(String bondedAreaConfirm) {
        this.bondedAreaConfirm = bondedAreaConfirm;
    }

    public String getZeroTaxRateReason() {
        return zeroTaxRateReason;
    }

    public void setZeroTaxRateReason(String zeroTaxRateReason) {
        this.zeroTaxRateReason = zeroTaxRateReason;
    }

    public List<InvoiceDetailDto> getInvoiceDetailDtoList() {
        return invoiceDetailDtoList;
    }

    public void setInvoiceDetailDtoList(List<InvoiceDetailDto> invoiceDetailDtoList) {
        this.invoiceDetailDtoList = invoiceDetailDtoList;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public BigDecimal getFreeTaxSalesAmount() {
        return freeTaxSalesAmount;
    }

    public void setFreeTaxSalesAmount(BigDecimal freeTaxSalesAmount) {
        this.freeTaxSalesAmount = freeTaxSalesAmount;
    }

    public BigDecimal getZeroTaxSalesAmount() {
        return zeroTaxSalesAmount;
    }

    public void setZeroTaxSalesAmount(BigDecimal zeroTaxSalesAmount) {
        this.zeroTaxSalesAmount = zeroTaxSalesAmount;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
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


    public String getMigType() {
        return migType;
    }

    public void setMigType(String migType) {
        this.migType = migType;
    }

    public InvoiceMain generateInvoiceMain(InvoiceMainDto invoiceMainDto, String yearMonth, String invoiceNumber, Company company, UserAccount user, String randomNumber) throws ValidatedException {
        InvoiceMain invoiceMain = new InvoiceMain();
        invoiceMain.setCompanyId(company.getCompanyId());
        invoiceMain.setYearMonth(yearMonth);
        invoiceMain.setInvoiceNumber(invoiceNumber);
        invoiceMain.setInvoiceDate(invoiceMainDto.getInvoiceDate());
        invoiceMain.setInvoiceTime(invoiceMainDto.getInvoiceTime());
        invoiceMain.setSeller(company.getIdentifier());
        invoiceMain.setBuyer(invoiceMainDto.getBuyer());
        invoiceMain.setBuyerRemark(invoiceMainDto.getBuyerRemark());
        invoiceMain.setMainRemark(invoiceMainDto.getMainRemark());
        invoiceMain.setCustomsClearanceMark(invoiceMainDto.getCustomsClearanceMark());
        invoiceMain.setRelateNumber(invoiceMainDto.getRelateNumber());
        invoiceMain.setInvoiceType(invoiceMainDto.getInvoiceType());
        invoiceMain.setGroupMark(invoiceMainDto.getGroupMark());
        invoiceMain.setDonateMark(invoiceMainDto.getDonateMark());
        invoiceMain.setCarrierType(invoiceMainDto.getCarrierType());
        invoiceMain.setCarrierId1(invoiceMainDto.getCarrierId1());
        invoiceMain.setCarrierId2(invoiceMainDto.getCarrierId2());
        invoiceMain.setPrintMark(invoiceMainDto.getPrintMark());
        invoiceMain.setNpoban(invoiceMainDto.getNpoban());
        invoiceMain.setRandomNumber(randomNumber);
        invoiceMain.setBondedAreaConfirm(invoiceMainDto.getBondedAreaConfirm());
        invoiceMain.setZeroTaxRateReason(invoiceMainDto.getZeroTaxRateReason());

        invoiceMain.setSalesAmount(invoiceMainDto.getSalesAmount());
        invoiceMain.setFreeTaxSalesAmount(invoiceMainDto.getFreeTaxSalesAmount());
        invoiceMain.setZeroTaxSalesAmount(invoiceMainDto.getZeroTaxSalesAmount());
        invoiceMain.setTaxType(invoiceMainDto.getTaxType());
        invoiceMain.setTaxRate(invoiceMainDto.getTaxRate());
        invoiceMain.setTaxAmount(invoiceMainDto.getTaxAmount());
        invoiceMain.setTotalAmount(invoiceMainDto.getTotalAmount());
        invoiceMain.setDiscountAmount(invoiceMainDto.getDiscountAmount());
        invoiceMain.setOriginalCurrencyAmount(invoiceMainDto.getOriginalCurrencyAmount());
        invoiceMain.setExchangeRate(invoiceMainDto.getExchangeRate());
        invoiceMain.setCurrency(invoiceMainDto.getCurrency());

        invoiceMain.setAllowanceCount(0);
        invoiceMain.setTotalAllowanceAmount(new BigDecimal(BigInteger.ZERO));
        invoiceMain.setInvoiceBalance(invoiceMainDto.getTotalAmount());
        invoiceMain.setTaxBalance(invoiceMainDto.getTaxAmount());

        invoiceMain.setMigType("A0401".equals(invoiceMainDto.getMigType()) ? "A0401" : "F0401");
        invoiceMain.setUploadStatus("P");
        invoiceMain.setProcessStatus("開立");
        invoiceMain.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), user.getId()));

        return invoiceMain;
    }


    public List<InvoiceDetail> generateInvoiceDetail(InvoiceMainDto invoiceMainDto, UserAccount userAccount, Long invoiceMainId, String invoiceNumber) {
        List<InvoiceDetail> invoiceDetailList = new ArrayList<InvoiceDetail>();

        List<InvoiceDetailDto> invoiceDetailDtoList = invoiceMainDto.getInvoiceDetailDtoList();
        for (InvoiceDetailDto invoiceDetailDto : invoiceDetailDtoList) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setCompanyId(userAccount.getCompanyId());
            invoiceDetail.setInvoiceDate(invoiceMainDto.getInvoiceDate());
            invoiceDetail.setInvoiceMainId(invoiceMainId);
            invoiceDetail.setInvoiceNumber(invoiceNumber);
            invoiceDetail.setDescription(invoiceDetailDto.getDescription());
            invoiceDetail.setQuantity(invoiceDetailDto.getQuantity());
            invoiceDetail.setUnit(invoiceDetailDto.getUnit());
            invoiceDetail.setUnitPrice(invoiceDetailDto.getUnitPrice());
            invoiceDetail.setTaxType(invoiceDetailDto.getTaxType());
            invoiceDetail.setAmount(invoiceDetailDto.getAmount());
            invoiceDetail.setSequenceNumber(invoiceDetailDto.getSequenceNumber());
            invoiceDetail.setRemark(invoiceDetailDto.getRemark());
            invoiceDetail.setRelateNumber(invoiceDetailDto.getRelateNumber());
            invoiceDetail.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), userAccount.getId()));
            invoiceDetailList.add(invoiceDetail);
        }
        return invoiceDetailList;

    }

}
