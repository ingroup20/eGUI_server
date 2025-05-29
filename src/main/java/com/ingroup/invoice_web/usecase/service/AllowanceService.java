package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.adapter.dto.AllowanceMainDto;
import com.ingroup.invoice_web.adapter.dto.CanceledAllowanceDto;
import com.ingroup.invoice_web.exception.NotfoundOriginalSourceException;
import com.ingroup.invoice_web.model.entity.*;
import com.ingroup.invoice_web.model.repository.AllowanceDetailRepository;
import com.ingroup.invoice_web.model.repository.AllowanceMainRepository;
import com.ingroup.invoice_web.model.repository.CanceledAllowanceRepository;
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
    private final CanceledAllowanceRepository canceledAllowanceRepository;
    private final SecurityService securityService;
    private final XmlGeneratorService xmlGeneratorService;
    private final RedisLockService redisLockService;

    public AllowanceService(InvoiceMainRepository invoiceMainRepository,
                            AllowanceMainRepository allowanceMainRepository,
                            AllowanceDetailRepository allowanceDetailRepository,
                            CanceledAllowanceRepository canceledAllowanceRepository,
                            SecurityService securityService,
                            XmlGeneratorService xmlGeneratorService,
                            RedisLockService redisLockService) {
        this.invoiceMainRepository = invoiceMainRepository;
        this.allowanceMainRepository = allowanceMainRepository;
        this.allowanceDetailRepository = allowanceDetailRepository;
        this.canceledAllowanceRepository = canceledAllowanceRepository;
        this.securityService = securityService;
        this.xmlGeneratorService = xmlGeneratorService;
        this.redisLockService = redisLockService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void issueAllowance(AllowanceMainDto allowanceMainDto) {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);

        //TODO 這裡判斷可能能優化
        if (allowanceMainRepository.findByCompanyIdAndAllowanceNumber(company.getCompanyId(), allowanceMainDto.getAllowanceNumber()) != null) {
            logger.warn("此折讓單號碼已被使用");
            throw new RuntimeException("此折讓單號碼已被使用");
        }

        //檢查折讓單號不能重複，redis要鎖
        if (redisLockService.checkLockKeyExists(allowanceMainDto.getAllowanceNumber(), company, allowanceMainDto.getSourceMigType())) {
            logger.warn("此折讓單號碼開立中");
            throw new RuntimeException("此折讓單號碼開立中");
        } else {
            redisLockService.issAllowanceNumberLock(allowanceMainDto.getAllowanceNumber(), company);
        }

        Long originalInvoiceId = allowanceMainDto.getAllowanceDetails().get(0).getOriginalInvoiceId();
        //檢查原始發票已開立，非註銷、非作廢
        InvoiceMain originalInvoice = invoiceMainRepository.findByIdAndProcessStatusAndUploadStatus(originalInvoiceId, "開立", "C").orElseThrow(() -> new NotfoundOriginalSourceException("無原始可折發票"));

        if ("Cancel".equals(originalInvoice.getProcessStatus()) || "void".equals(originalInvoice.getProcessStatus())) { //fixme 重構好看點
            logger.error("已作廢或註銷，無原始可折發票");
            throw new RuntimeException("已作廢或註銷，無原始可折發票");
        }

        //檢查可折餘額
        BigDecimal invoiceBalance = originalInvoice.getInvoiceBalance();
        BigDecimal taxBalance = originalInvoice.getTaxBalance();
        if (!(invoiceBalance.compareTo(allowanceMainDto.getTotalAmount()) > 0)
                || !(taxBalance.compareTo(allowanceMainDto.getTaxAmount()) > 0)) {
            throw new RuntimeException("可折讓額度不足");
        }

        //同張發票多折讓，先減金額可以開立，去到queue重複開立才被擋
        AllowanceMain allowanceMain = allowanceMainDto.generateAllowanceMain(allowanceMainDto, user, "憑證/交換"); //fixme
        allowanceMainRepository.save(allowanceMain);

        Long allowanceId = allowanceMain.getId();
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
        logger.info("折讓開立操作成功, Allowance_id = {}", allowanceId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelAllowance(CanceledAllowanceDto canceledAllowanceDto) {
        UserAccount user = securityService.checkLoginUser();
        Company company = securityService.checkLoginCompany(user);
        //避免連續作廢同張折讓單
        if (canceledAllowanceRepository.findByCompanyIdAndCancelAllowanceNumberAndUploadStatus(company.getCompanyId(), canceledAllowanceDto.getCancelAllowanceNumber(), "C") != null) {
            throw new RuntimeException("折讓已作廢");
        }

        //前端要負責顯示與過濾，指回傳可作廢id給後端
        AllowanceMain allowanceMain = allowanceMainRepository.findByIdAndProcessStatusAndUploadStatus(canceledAllowanceDto.getAllowanceId(), "開立", "C").orElseThrow(() -> new RuntimeException("沒單據可作廢"));

        CanceledAllowance canceledAllowance = canceledAllowanceDto.generateCanceledAllowance(canceledAllowanceDto, allowanceMain, user);
        canceledAllowanceRepository.save(canceledAllowance);

        //更新折讓主檔
        allowanceMain.setProcessStatus("作廢");
        allowanceMain.setUploadStatus("P");
        allowanceMainRepository.save(allowanceMain);

        //XML

        logger.info("折讓作廢操作成功, cancelAllowance_id = {}", canceledAllowance.getId());


    }


    private void updateInvoiceBalance(InvoiceMain invoiceMain, BigDecimal allowanceTotalAmount, BigDecimal allowanceTotalTax) {
        invoiceMain.setTotalAllowanceAmount(invoiceMain.getTotalAllowanceAmount().add(allowanceTotalAmount));
        invoiceMain.setInvoiceBalance(invoiceMain.getInvoiceBalance().subtract(allowanceTotalAmount));
        invoiceMain.setTaxBalance(invoiceMain.getTaxBalance().subtract(allowanceTotalTax));
        invoiceMain.setAllowanceCount(invoiceMain.getAllowanceCount() + 1);
        invoiceMainRepository.save(invoiceMain); //todo 小心確認是新增還是更新

    }


}
