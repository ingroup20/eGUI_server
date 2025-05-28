package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.adapter.dto.AllowanceMainDto;
import com.ingroup.invoice_web.exception.NotfoundOriginalSourceException;
import com.ingroup.invoice_web.model.entity.*;
import com.ingroup.invoice_web.model.repository.AllowanceDetailRepository;
import com.ingroup.invoice_web.model.repository.AllowanceMainRepository;
import com.ingroup.invoice_web.model.repository.InvoiceMainRepository;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AllowanceService {
    private final static Logger logger = LoggerFactory.getLogger(AllowanceService.class);

    private final InvoiceMainRepository invoiceMainRepository;
    private final AllowanceMainRepository allowanceMainRepository;
    private final AllowanceDetailRepository allowanceDetailRepository;
    private final SecurityService securityService;
    private final XmlGeneratorService xmlGeneratorService;

    public AllowanceService(InvoiceMainRepository invoiceMainRepository,
                            AllowanceMainRepository allowanceMainRepository,
                            AllowanceDetailRepository allowanceDetailRepository,
                            SecurityService securityService,
                            XmlGeneratorService xmlGeneratorService) {
        this.invoiceMainRepository = invoiceMainRepository;
        this.allowanceMainRepository = allowanceMainRepository;
        this.allowanceDetailRepository = allowanceDetailRepository;
        this.securityService = securityService;
        this.xmlGeneratorService = xmlGeneratorService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void issueAllowance(AllowanceMainDto allowanceMainDto) {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);
        Long originalInvoiceId = allowanceMainDto.getAllowanceDetails().get(0).getOriginalInvoiceId();
        //檢查原始發票已開立，非註銷、非作廢
        InvoiceMain originalInvoice = invoiceMainRepository.findByInvoiceIdAndUploadDone(originalInvoiceId).orElseThrow(() -> new NotfoundOriginalSourceException("無原始可折發票"));

        if ("Cancel".equals(originalInvoice.getProcessStatus()) || "void".equals(originalInvoice.getProcessStatus())) { //fixme 重構好看點
            logger.error("已作廢或註銷，無原始可折發票");
            throw new RuntimeException("已作廢或註銷，無原始可折發票");
        }

        //檢查可折餘額
        BigDecimal invoiceBalance = originalInvoice.getInvoiceBalance();
        BigDecimal taxBalance = originalInvoice.getTaxBalance();
        if (!(invoiceBalance.compareTo(allowanceMainDto.getTotalAmount()) > 0)
                && !(taxBalance.compareTo(allowanceMainDto.getTaxAmount()) > 0)) {
            throw new RuntimeException("可折讓額度不足");
        }

        //同張發票多折讓，先減金額可以開立，去到queue重複開立才被擋
        AllowanceMain allowanceMain = allowanceMainDto.generateAllowanceMain(allowanceMainDto, user, "憑證/交換"); //fixme
        allowanceMainRepository.save(allowanceMain);

        Long allowanceId = allowanceMain.getId(); //不確定能這樣嗎
        logger.info("allowance Id = {}", allowanceId);
        List<AllowanceDetail> allowanceDetailList;
        if (allowanceId != null) {
            allowanceDetailList = allowanceMainDto.generateAllowanceDetail(allowanceMainDto, user, allowanceId);
            allowanceDetailRepository.saveAll(allowanceDetailList);
        } else {
            throw new RuntimeException("關聯主資料存取異常");
        }

        //fixme 還沒考量交換發票折讓
        //產XML TO QUEUE
        try {
            String xml = xmlGeneratorService.generateAllowanceXML(allowanceMain, allowanceDetailList, company);

            //queue接到後才鎖定發票號碼
            System.out.println(xml);
        } catch (IOException e) {
            logger.error("產xml檔案異常");
        } catch (TemplateException e) {
            logger.error("xml模板套用異常");
        }

        //開完折讓更新invoice Main 可折餘額
        updateInvoiceBalance(originalInvoice, allowanceMain.getTotalAmount(), allowanceMain.getTaxAmount());

    }


    private void updateInvoiceBalance(InvoiceMain invoiceMain, BigDecimal allowanceTotalAmount, BigDecimal allowanceTotalTax) {
        invoiceMain.setInvoiceBalance(invoiceMain.getInvoiceBalance().subtract(allowanceTotalAmount));
        invoiceMain.setTaxBalance(invoiceMain.getTaxBalance().subtract(allowanceTotalTax));
        invoiceMain.setAllowanceCount(invoiceMain.getAllowanceCount() + 1);
        invoiceMainRepository.save(invoiceMain); //todo 小心確認是新增還是更新

    }


}
