package com.ingroup.invoice_web.usecase.service.impl;

import com.ingroup.invoice_web.adapter.dto.InvoiceDetailDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.exception.IssueInvoiceException;
import com.ingroup.invoice_web.exception.NotEnoughAssignException;
import com.ingroup.invoice_web.exception.ValidatedException;
import com.ingroup.invoice_web.model.entity.*;
import com.ingroup.invoice_web.model.repository.InvoiceDetailRepository;
import com.ingroup.invoice_web.model.repository.InvoiceMainRepository;
import com.ingroup.invoice_web.usecase.service.AssignGroupService;
import com.ingroup.invoice_web.usecase.service.IssueInvoiceService;
import com.ingroup.invoice_web.usecase.service.SecurityService;
import com.ingroup.invoice_web.usecase.service.XmlGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ingroup.invoice_web.util.DateTimeUtil.*;
import static com.ingroup.invoice_web.util.constant.ErrorCodeEnum.*;
import static com.ingroup.invoice_web.util.constant.TaxTypeEnum.*;

@Service
public class IssueInvoiceServiceImpl extends SecurityService implements IssueInvoiceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AssignGroupService assignGroupService;
    private final InvoiceMainRepository invoiceMainRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final XmlGeneratorService xmlGeneratorService;

    private final Integer B2C_SALES_PRICE = 1;

    IssueInvoiceServiceImpl(AssignGroupService assignGroupService,
                            InvoiceMainRepository invoiceMainRepository,
                            InvoiceDetailRepository invoiceDetailRepository,
                            XmlGeneratorService xmlGeneratorService) {
        this.assignGroupService = assignGroupService;
        this.invoiceMainRepository = invoiceMainRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.xmlGeneratorService = xmlGeneratorService;
    }

    @Transactional(rollbackFor = Exception.class)
    public String issueInvoice(InvoiceMainDto invoiceMainDto) throws IssueInvoiceException {
        UserAccount user = checkLoginUser();
        Company company = checkLoginCompany(user);
        Printer printer = checkLoginPrinter(user);

        String invoiceNumber = "";
        String yearMonth = getYearMonthROC(invoiceMainDto.getInvoiceDate());
        String randomNumber = generateRandomNumber();
        calculateAmount(invoiceMainDto);
        if(B2C_SALES_PRICE.equals(invoiceMainDto.getConditionType())) { //b2c售價含稅
            calculateTax(invoiceMainDto);
        } //b2b定價不含稅

        AssignGroup assignGroup;
        //取號

        do {
            assignGroup = assignGroupService.getAvailableAssign(yearMonth, company, printer).orElse(null);
            if (assignGroup != null) {
                //正在使用同一字軌
                try {
                    invoiceNumber = assignGroupService.takeAssignNo(assignGroup);
                    logger.info("issueInvoice invoice number: {}, yearMonth: {}, invoice date: {}", invoiceNumber, yearMonth, invoiceMainDto.getInvoiceDate());
                    InvoiceMain invoiceMain = generateInvoiceMain(invoiceMainDto, yearMonth, invoiceNumber, company, user, randomNumber);

                    if (invoiceMain != null) {
                        invoiceMain = invoiceMainRepository.save(invoiceMain);
                        logger.debug("save invoice_main id = {}", invoiceMain.getId());
                        List<InvoiceDetail> invoiceDetailList = generateInvoiceDetail(invoiceMainDto, invoiceMain.getId(), invoiceNumber);
                        invoiceDetailRepository.saveAll(invoiceDetailList);
                        logger.debug("save invoice_detail id = {}", invoiceDetailList.stream()
                                                                                     .map(InvoiceDetail::getId)
                                                                                     .collect(Collectors.toList()));
                        //xml要傳到queue
                        xmlGeneratorService.generateInvoiceXML(invoiceMain, invoiceDetailList);
                    }
                    break;

                } catch (NotEnoughAssignException e) {
                    logger.debug("重取一次字軌");
                    continue;
                } catch (Exception e) {
                    logger.error("嚴重異常");
                }
            } else {
                logger.debug("請匯入可用字軌");
            }

        } while (assignGroup != null);

        return invoiceNumber;

    }


    private InvoiceMainDto calculateAmount(InvoiceMainDto invoiceMainDto) throws ValidatedException {

        //總金額
        List<InvoiceDetailDto> detailList = invoiceMainDto.getInvoiceDetailDtoList();
        BigDecimal totalAmount = detailList.stream()
                .map(InvoiceDetailDto::getSalesAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //應稅金額
        BigDecimal salesAmount = detailList.stream()
                .filter(dt -> TAXABLE.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getSalesAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //免稅金額
        BigDecimal freeTaxSalesAmount = detailList.stream()
                .filter(dt -> TAX_FREE.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getSalesAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //零稅率金額
        BigDecimal zeroTaxSalesAmount = detailList.stream()
                .filter(dt -> ZERO_TAX.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getSalesAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //特種稅
        if (SPECIAL_TAX.getCode().equals(invoiceMainDto.getTaxType())
                && (!salesAmount.equals(BigDecimal.ZERO) || !freeTaxSalesAmount.equals(BigDecimal.ZERO) || !zeroTaxSalesAmount.equals(BigDecimal.ZERO))) {
            throw new ValidatedException(VI003);

        } else if (SPECIAL_TAX.getCode().equals(invoiceMainDto.getTaxType())) {
            salesAmount = detailList.stream()
                    .filter(dt -> SPECIAL_TAX.getCode().equals(dt.getTaxType()))
                    .map(InvoiceDetailDto::getSalesAmount)
                    .filter(Objects::nonNull) //避免NullPointException
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        //混合稅
        if (MIXTURE_TAX.getCode().equals(invoiceMainDto.getTaxType())) {
            if (!freeTaxSalesAmount.equals(BigDecimal.ZERO) && !zeroTaxSalesAmount.equals(BigDecimal.ZERO)) {
                throw new ValidatedException(VI001);
            }

            if (!totalAmount.equals(salesAmount.add(freeTaxSalesAmount).add(zeroTaxSalesAmount))) {
                throw new ValidatedException(VI002);
            }
        }

        invoiceMainDto.setTotalAmount(totalAmount);
        invoiceMainDto.setSalesAmount(salesAmount);
        invoiceMainDto.setFreeTaxSalesAmount(freeTaxSalesAmount);
        invoiceMainDto.setZeroTaxSalesAmount(zeroTaxSalesAmount);


        return invoiceMainDto;
    }


    private InvoiceMainDto calculateTax(InvoiceMainDto invoiceMainDto) {
        BigDecimal taxAmount = new BigDecimal(BigInteger.ZERO);
        BigDecimal taxRate = invoiceMainDto.getTaxRate();
        taxAmount = taxAmount.add(taxRate.multiply(invoiceMainDto.getSalesAmount()));
        invoiceMainDto.setTaxAmount(taxAmount); //總稅額
        return invoiceMainDto;
    }

    private InvoiceMain generateInvoiceMain(InvoiceMainDto invoiceMainDto, String yearMonth, String invoiceNumber, Company company, UserAccount user, String randomNumber) throws ValidatedException {
        InvoiceMain invoiceMain = new InvoiceMain();
        invoiceMain.setYearMonth(yearMonth);
        invoiceMain.setInvoiceNumber(invoiceNumber);
        invoiceMain.setInvoiceDate(invoiceMainDto.getInvoiceDate());
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
        invoiceMain.setNpoban(invoiceMainDto.getNpoban());
        invoiceMain.setRandomNumber(randomNumber);//
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
        invoiceMain.setInvoiceBalance(invoiceMainDto.getTotalAmount()); //
        invoiceMain.setTaxBalance(invoiceMainDto.getTaxAmount()); //

        // 補充欄位（如有需要）：
        invoiceMain.setUploadStatus("待上傳");
        String nowDateTime = getCurrentDateTime();
        invoiceMain.setEditRecord(new EditRecord(nowDateTime, nowDateTime, user.getId()));

        return null;
    }


    private List<InvoiceDetail> generateInvoiceDetail(InvoiceMainDto invoiceMainDto, Long invoiceMainId, String invoiceNumber) {
        List<InvoiceDetail> invoiceDetailList = new ArrayList<InvoiceDetail>();

        List<InvoiceDetailDto> invoiceDetailDtoList = invoiceMainDto.getInvoiceDetailDtoList();
        for (InvoiceDetailDto invoiceDetailDto : invoiceDetailDtoList) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoiceMainId(invoiceMainId);
            invoiceDetail.setInvoiceNumber(invoiceNumber);
            invoiceDetail.setDescription(invoiceDetailDto.getDescription());
            invoiceDetail.setQuantity(invoiceDetailDto.getQuantity());
            invoiceDetail.setUnit(invoiceDetailDto.getUnit());
            invoiceDetail.setUnitPrice(invoiceDetailDto.getUnitPrice());
            invoiceDetail.setTaxType(invoiceDetailDto.getTaxType());
            invoiceDetail.setSalesAmount(invoiceDetailDto.getSalesAmount());
            invoiceDetail.setSequenceNumber(invoiceDetailDto.getSequenceNumber());
            invoiceDetail.setRemark(invoiceDetailDto.getRemark());
            invoiceDetail.setRelateNumber(invoiceDetailDto.getRelateNumber());

            invoiceDetailList.add(invoiceDetail);
        }
        return invoiceDetailList;

    }

    private String generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 10000); //0~9999
        return String.format("%04d", randomNumber); //前面補0到4位數
    }

}
