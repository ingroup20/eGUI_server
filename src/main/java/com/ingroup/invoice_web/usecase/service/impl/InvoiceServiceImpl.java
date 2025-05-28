package com.ingroup.invoice_web.usecase.service.impl;

import com.ingroup.invoice_web.adapter.dto.CanceledInvoiceDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceDetailDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.adapter.dto.VoidedInvoiceDto;
import com.ingroup.invoice_web.exception.IssueInvoiceException;
import com.ingroup.invoice_web.exception.KeyOnLockException;
import com.ingroup.invoice_web.exception.UsedUpAssignException;
import com.ingroup.invoice_web.exception.ValidatedException;
import com.ingroup.invoice_web.model.entity.*;
import com.ingroup.invoice_web.model.repository.InvoiceDetailRepository;
import com.ingroup.invoice_web.model.repository.InvoiceMainRepository;
import com.ingroup.invoice_web.usecase.service.*;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ingroup.invoice_web.util.DateTimeUtil.getYearMonthROC;
import static com.ingroup.invoice_web.util.constant.ErrorCodeEnum.*;
import static com.ingroup.invoice_web.util.constant.TaxTypeEnum.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AssignGroupService assignGroupService;
    private final InvoiceMainRepository invoiceMainRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final XmlGeneratorService xmlGeneratorService;
    private final SecurityService securityService;
    private final RedisLockService redisLockService;

    private final Integer B2C_SALES_PRICE = 1;

    InvoiceServiceImpl(AssignGroupService assignGroupService,
                       InvoiceMainRepository invoiceMainRepository,
                       InvoiceDetailRepository invoiceDetailRepository,
                       XmlGeneratorService xmlGeneratorService,
                       SecurityService securityService,
                       RedisLockService redisLockService) {
        this.assignGroupService = assignGroupService;
        this.invoiceMainRepository = invoiceMainRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.xmlGeneratorService = xmlGeneratorService;
        this.securityService = securityService;
        this.redisLockService = redisLockService;
    }

    @Transactional(rollbackFor = Exception.class)
    public String issueInvoice(InvoiceMainDto invoiceMainDto) throws IssueInvoiceException {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);
        Printer printer = securityService.checkLoginPrinter(user);

        String invoiceNumber = "";
        String yearMonth = getYearMonthROC(invoiceMainDto.getInvoiceDate());
        String randomNumber = generateRandomNumber();
        calculateAmount(invoiceMainDto);
        if (B2C_SALES_PRICE.equals(invoiceMainDto.getConditionType())) { //b2c售價含稅
            calculateTax(invoiceMainDto);
        } //b2b定價不含稅

        AssignGroup assignGroup;
        //取號

        do {
            assignGroup = assignGroupService.getInUseAssign(yearMonth, company, printer).orElse(null);
            if (assignGroup == null) {
                assignGroup = assignGroupService.getPerUseAssign(yearMonth, company, printer).orElse(null);
            }

            if (assignGroup == null) {
                assignGroup = assignGroupService.getAvailableAssign(yearMonth, company, printer);
            }

            logger.info("assignGroup = {}", assignGroup);
            try {
                invoiceNumber = assignGroupService.takeAssignNo(assignGroup);
                if (redisLockService.checkLockKeyExists(invoiceNumber)) {
                    logger.error("the invoice number is locked , invoice number = {}", invoiceNumber);
                    throw new KeyOnLockException("error, the invoice number is locked"); //todo 通知前端
                }
                break;
            } catch (UsedUpAssignException e) {
                logger.debug("當前字軌無號碼，重取一次字軌");
                assignGroup = null;
                continue;
            } catch (Exception e) {
                logger.error("嚴重異常");
                break;
            }
        } while (assignGroup == null);


        logger.info("issueInvoice invoice number: {}, yearMonth: {}, invoice date: {}", invoiceNumber, yearMonth, invoiceMainDto.getInvoiceDate());
        InvoiceMain invoiceMain = invoiceMainDto.generateInvoiceMain(invoiceMainDto, yearMonth, invoiceNumber, company, user, randomNumber); //小心方法呼叫
        invoiceMain = invoiceMainRepository.save(invoiceMain);
        logger.debug("save invoice_main id = {}", invoiceMain.getId());

        List<InvoiceDetail> invoiceDetailList = invoiceMainDto.generateInvoiceDetail(invoiceMainDto, invoiceMain.getId(), invoiceNumber); //小心方法呼叫
        invoiceDetailRepository.saveAll(invoiceDetailList);
        logger.debug("save invoice_detail id = {}", invoiceDetailList.stream()
                .map(InvoiceDetail::getId)
                .collect(Collectors.toList()));

        try {
            //xml要傳到queue
            String xml = xmlGeneratorService.generateInvoiceXML(invoiceMain, invoiceDetailList, company);

            //queue接到後才鎖定發票號碼
            System.out.println(xml);
        } catch (IOException e) {
            logger.error("產xml檔案異常");
        } catch (TemplateException e) {
            logger.error("xml模板套用異常");
        }

        return invoiceNumber;

    }

    @Override
    public void cancelInvoice(CanceledInvoiceDto canceledInvoiceDto) {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);

        //找到發票主黨...檢查已開立成功...檢查沒有折讓過....檢查沒註銷過...檢查沒刪除過....開立
        InvoiceMain invoiceMain = invoiceMainRepository.findByInvoiceIdAndUploadDone(canceledInvoiceDto.getInvoiceId()).orElseThrow(() -> new RuntimeException("無此發票 或 發票上傳中"));

        if (invoiceMain.getAllowanceCount() != 0) {
            throw new RuntimeException("有折讓，不能作廢");
        }

        CanceledInvoice canceledInvoice = new CanceledInvoice();
        canceledInvoice.setInvoiceId(canceledInvoiceDto.getInvoiceId());
        canceledInvoice.setCancelInvoiceNumber(invoiceMain.getInvoiceNumber());
        canceledInvoice.setInvoiceDate(invoiceMain.getInvoiceDate());
        canceledInvoice.setBuyerId(invoiceMain.getBuyer().getIdentifier());
        canceledInvoice.setSellerId(invoiceMain.getSeller());
        canceledInvoice.setCancelDate(canceledInvoiceDto.getCancelDate());
        canceledInvoice.setCancelTime(canceledInvoiceDto.getCancelTime());
        canceledInvoice.setCancelReason(canceledInvoiceDto.getCancelReason());
        canceledInvoice.setReturnTaxDocumentNumber(canceledInvoiceDto.getReturnTaxDocumentNumber());
        canceledInvoice.setRemark(canceledInvoiceDto.getRemark());
        canceledInvoice.setReserved1(canceledInvoiceDto.getReserved1());
        canceledInvoice.setReserved2(canceledInvoiceDto.getReserved2());

        canceledInvoice.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), user.getId()));

        if (invoiceMain.getMigType().equals(canceledInvoiceDto.getSourceMigType())) {
            try {
                //xml要傳到queue
                xmlGeneratorService.generateCanceledInvoiceXML(canceledInvoice, canceledInvoiceDto.getSourceMigType());

            } catch (IOException e) {
                logger.error("產xml檔案異常");
            } catch (TemplateException e) {
                logger.error("xml模板套用異常");
            }
        }
    }

    @Override
    public void voidInvoice(VoidedInvoiceDto voidedInvoiceDto) {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);

        //找到發票主黨...檢查已開立成功...檢查沒有折讓過....檢查沒註銷過...檢查沒刪除過....開立
        InvoiceMain invoiceMain = invoiceMainRepository.findByInvoiceIdAndUploadDone(voidedInvoiceDto.getInvoiceId()).orElseThrow(() -> new RuntimeException("無此發票 或 發票上傳中"));

        if (invoiceMain.getAllowanceCount() != 0) {
            throw new RuntimeException("有折讓，不能作廢");
        }

        VoidedInvoice voidedInvoice = new VoidedInvoice();
        voidedInvoice.setInvoiceId(voidedInvoiceDto.getInvoiceId());
        voidedInvoice.setVoidInvoiceNumber(invoiceMain.getInvoiceNumber());
        voidedInvoice.setInvoiceDate(invoiceMain.getInvoiceDate());
        voidedInvoice.setBuyerId(invoiceMain.getBuyer().getIdentifier());
        voidedInvoice.setSellerId(invoiceMain.getSeller());
        voidedInvoice.setVoidDate(voidedInvoiceDto.getVoidDate());
        voidedInvoice.setVoidTime(voidedInvoiceDto.getVoidTime());
        voidedInvoice.setVoidReason(voidedInvoiceDto.getVoidReason());
        voidedInvoice.setRemark(voidedInvoiceDto.getRemark());
        voidedInvoice.setReserved1(voidedInvoiceDto.getReserved1());
        voidedInvoice.setReserved2(voidedInvoiceDto.getReserved2());
        voidedInvoice.setEditRecord(new EditRecord(LocalDateTime.now(), LocalDateTime.now(), user.getId()));

        try {
            //xml要傳到queue
            xmlGeneratorService.generateVoidedInvoiceXML(voidedInvoice);

        } catch (IOException e) {
            logger.error("產xml檔案異常");
        } catch (TemplateException e) {
            logger.error("xml模板套用異常");
        }

    }


    private InvoiceMainDto calculateAmount(InvoiceMainDto invoiceMainDto) throws ValidatedException {

        //總金額
        List<InvoiceDetailDto> detailList = invoiceMainDto.getInvoiceDetailDtoList();
        BigDecimal totalAmount = detailList.stream()
                .map(InvoiceDetailDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //應稅金額
        BigDecimal salesAmount = detailList.stream()
                .filter(dt -> TAXABLE.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //免稅金額
        BigDecimal freeTaxSalesAmount = detailList.stream()
                .filter(dt -> TAX_FREE.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //零稅率金額
        BigDecimal zeroTaxSalesAmount = detailList.stream()
                .filter(dt -> ZERO_TAX.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //特種稅
        if (SPECIAL_TAX.getCode().equals(invoiceMainDto.getTaxType())
                && (!salesAmount.equals(BigDecimal.ZERO) || !freeTaxSalesAmount.equals(BigDecimal.ZERO) || !zeroTaxSalesAmount.equals(BigDecimal.ZERO))) {
            throw new ValidatedException(VI003);

        } else if (SPECIAL_TAX.getCode().equals(invoiceMainDto.getTaxType())) {
            salesAmount = detailList.stream()
                    .filter(dt -> SPECIAL_TAX.getCode().equals(dt.getTaxType()))
                    .map(InvoiceDetailDto::getAmount)
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


    private String generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 10000); //0~9999
        return String.format("%04d", randomNumber); //前面補0到4位數
    }

}
