package com.ingroup.invoice_web.usecase.service.impl;

import com.ingroup.invoice_web.adapter.dto.CanceledInvoiceDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceDetailDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.adapter.dto.VoidedInvoiceDto;
import com.ingroup.invoice_web.exception.GenerateXmlException;
import com.ingroup.invoice_web.exception.NoTargetAvailableException;
import com.ingroup.invoice_web.exception.OtherOperationInUseException;
import com.ingroup.invoice_web.exception.TargetKeyLockedException;
import com.ingroup.invoice_web.exception.runtime.UsedUpAssignException;
import com.ingroup.invoice_web.exception.runtime.ValidatedException;
import com.ingroup.invoice_web.model.entity.*;
import com.ingroup.invoice_web.model.repository.CanceledInvoiceRepository;
import com.ingroup.invoice_web.model.repository.InvoiceDetailRepository;
import com.ingroup.invoice_web.model.repository.InvoiceMainRepository;
import com.ingroup.invoice_web.model.repository.VoidedInvoiceRepository;
import com.ingroup.invoice_web.usecase.amqp.RabbitMQProducer;
import com.ingroup.invoice_web.usecase.service.*;
import com.ingroup.invoice_web.util.constant.MigTypeEnum;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private final CanceledInvoiceRepository canceledInvoiceRepository;
    private final VoidedInvoiceRepository voidedInvoiceRepository;
    private final XmlGeneratorService xmlGeneratorService;
    private final SecurityService securityService;
    private final RedisLockService redisLockService;
    private final RabbitMQProducer rabbitMQProducer;

    private final String B2C_SALES_PRICE_TYPE = "C0401";

    InvoiceServiceImpl(AssignGroupService assignGroupService,
                       InvoiceMainRepository invoiceMainRepository,
                       InvoiceDetailRepository invoiceDetailRepository,
                       CanceledInvoiceRepository canceledInvoiceRepository,
                       VoidedInvoiceRepository voidedInvoiceRepository,
                       XmlGeneratorService xmlGeneratorService,
                       SecurityService securityService,
                       RedisLockService redisLockService,
                       RabbitMQProducer rabbitMQProducer) {
        this.assignGroupService = assignGroupService;
        this.invoiceMainRepository = invoiceMainRepository;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.canceledInvoiceRepository = canceledInvoiceRepository;
        this.voidedInvoiceRepository = voidedInvoiceRepository;
        this.xmlGeneratorService = xmlGeneratorService;
        this.securityService = securityService;
        this.redisLockService = redisLockService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String issueInvoice(InvoiceMainDto invoiceMainDto) throws Exception {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);
        Printer printer = securityService.checkLoginPrinter(user);

        logger.info("======= start issue invoice =======");
        String invoiceNumber = "";
        String yearMonth = getYearMonthROC(invoiceMainDto.getInvoiceDate());
        String randomNumber = generateRandomNumber();
        calculateAmount(invoiceMainDto);
        if (B2C_SALES_PRICE_TYPE.equals(invoiceMainDto.getMigType())) { //b2c售價含稅
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
                //fixme 要檢查開立日期必須在前個號碼之後
                if (redisLockService.checkLockKeyExists(invoiceNumber, company, invoiceMainDto.getMigType())) {
                    logger.error("the invoice number is locked , invoice number = {}", invoiceNumber);
                    throw new TargetKeyLockedException("error, the invoice number is locked"); //todo 通知前端
                } else {
                    //鎖發票號碼
                    redisLockService.issInvoiceNumberLock(invoiceNumber, company);
                }
                break;
            } catch (UsedUpAssignException e) {
                logger.debug("當前字軌無號碼，重取一次字軌");
                assignGroup = null;
                continue;
            }
        } while (assignGroup == null);

        //fixme 還沒考量交換發票
        logger.info("issueInvoice invoice number: {}, yearMonth: {}, invoice date: {}", invoiceNumber, yearMonth, invoiceMainDto.getInvoiceDate());
        InvoiceMain invoiceMain = invoiceMainDto.generateInvoiceMain(invoiceMainDto, yearMonth, invoiceNumber, company, user, randomNumber); //小心方法呼叫
        invoiceMain = invoiceMainRepository.save(invoiceMain);
        logger.debug("save invoice_main id = {}", invoiceMain.getId());

        List<InvoiceDetail> invoiceDetailList = invoiceMainDto.generateInvoiceDetail(invoiceMainDto, user, invoiceMain.getId(), invoiceNumber); //小心方法呼叫
        invoiceDetailRepository.saveAll(invoiceDetailList);
        logger.debug("save invoice_detail id = {}", invoiceDetailList.stream()
                .map(InvoiceDetail::getId)
                .collect(Collectors.toList()));

        try {
            //xml要傳到queue
            String xml = xmlGeneratorService.generateInvoiceXML(invoiceMain, invoiceDetailList, company);
            logger.debug("send invoice xml to turnkey invoice_id = {}", invoiceMain.getId());
            rabbitMQProducer.sendXmlToTurnkeyMessageRelay(xml, MigTypeEnum.ISSUE_EVIDENCE_INVOICE.getMigTypeCode());

        } catch (IOException | TemplateException e) {
            logger.error("generateInvoiceXML error");
            throw new GenerateXmlException("Invoice XML 發生錯誤， invoice id : " + invoiceMain.getId());
        }
        logger.info("Issue Invoice Operation Success, invoiceMain_id = {}", invoiceMain.getId());
        return invoiceNumber;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelInvoice(CanceledInvoiceDto canceledInvoiceDto) throws Exception {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);

        //找到發票主黨...檢查已開立成功...檢查沒有折讓過....檢查沒註銷過...檢查沒刪除過....開立
        InvoiceMain invoiceMain = invoiceMainRepository.findByIdAndProcessStatusAndUploadStatus(canceledInvoiceDto.getInvoiceId(), "開立", "C").orElseThrow(() -> new RuntimeException("無此發票可操作 或 發票上傳中"));

        if (invoiceMain.getAllowanceCount() != 0) {
            logger.error("allowance still exists");
            throw new OtherOperationInUseException("有折讓，不能作廢");
        }
        logger.info("======= start cancel invoice, original invoice id :{} =======", canceledInvoiceDto.getInvoiceId());

        CanceledInvoice canceledInvoice = canceledInvoiceDto.generateCanceledInvoice(canceledInvoiceDto, invoiceMain, user);
        canceledInvoiceRepository.save(canceledInvoice);

        if (invoiceMain.getMigType().equals(canceledInvoiceDto.getSourceMigType())) {
            try {
                //xml要傳到queue
                String xml = xmlGeneratorService.generateCanceledInvoiceXML(canceledInvoice, canceledInvoiceDto.getSourceMigType());
                logger.debug("send canceled invoice xml to turnkey invoice_id = {}", canceledInvoice.getInvoiceId());
                rabbitMQProducer.sendXmlToTurnkeyMessageRelay(xml,MigTypeEnum.CATCH_ISSUE_EXCHANGE_INVOICE.getMigTypeCode());

            } catch (IOException | TemplateException e) {
                logger.error("generateCanceledInvoiceXML error");
                throw new GenerateXmlException("canceledInvoice XML 發生錯誤，original invoice id : " + canceledInvoice.getInvoiceId());
            }
        }

        //更新發票主檔
        invoiceMain.setProcessStatus("作廢");
        invoiceMain.setUploadStatus("P");
        invoiceMainRepository.save(invoiceMain);
        logger.info("Cancel Invoice Operation Success, canceledInvoice_id = {}", canceledInvoice.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void voidInvoice(VoidedInvoiceDto voidedInvoiceDto) throws Exception {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);

        //找到發票主黨...檢查已開立成功...檢查沒有折讓過....檢查沒註銷過...檢查沒刪除過....開立
        InvoiceMain invoiceMain = invoiceMainRepository.findByIdAndProcessStatusAndUploadStatus(voidedInvoiceDto.getInvoiceId(), "開立", "C").orElseThrow(() -> new NoTargetAvailableException("無此發票可操作 或 發票上傳中"));

        if (invoiceMain.getAllowanceCount() != 0) {
            logger.error("allowance still exists");
            throw new OtherOperationInUseException("有折讓，不能作廢");
        }

        logger.info("======= start void invoice, original invoice id :{} =======", voidedInvoiceDto.getInvoiceId());
        VoidedInvoice voidedInvoice = voidedInvoiceDto.generateVoidedInvoice(voidedInvoiceDto, invoiceMain, user);
        voidedInvoiceRepository.save(voidedInvoice);

        try {
            //xml要傳到queue
            String xml = xmlGeneratorService.generateVoidedInvoiceXML(voidedInvoice);
            logger.debug("send voided invoice xml to turnkey invoice_id = {}", voidedInvoice.getInvoiceId());
            rabbitMQProducer.sendXmlToTurnkeyMessageRelay(xml,MigTypeEnum.VOID_EVIDENCE_INVOICE.getMigTypeCode());

        } catch (IOException | TemplateException e) {
            logger.error("generateVoidedInvoiceXML error");
            throw new GenerateXmlException("voidedInvoice XML 發生錯誤，original invoice id : " + voidedInvoice.getInvoiceId());
        }

        //更新發票主檔
        invoiceMain.setProcessStatus("註銷");
        invoiceMain.setUploadStatus("P");
        invoiceMainRepository.save(invoiceMain);
        logger.info("Void Invoice Operation Success, voidedInvoice_id = {}", voidedInvoice.getId());
    }


    private void calculateAmount(InvoiceMainDto invoiceMainDto) throws ValidatedException {

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

    }


    private void calculateTax(InvoiceMainDto invoiceMainDto) {
        BigDecimal taxAmount = new BigDecimal(BigInteger.ZERO);
        BigDecimal taxRate = invoiceMainDto.getTaxRate();
        taxAmount = taxAmount.add(taxRate.multiply(invoiceMainDto.getSalesAmount()));
        invoiceMainDto.setTaxAmount(taxAmount); //總稅額
    }


    private String generateRandomNumber() {
        int randomNumber = (int) (Math.random() * 10000); //0~9999
        return String.format("%04d", randomNumber); //前面補0到4位數
    }

}
