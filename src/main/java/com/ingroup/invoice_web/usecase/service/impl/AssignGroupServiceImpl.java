package com.ingroup.invoice_web.usecase.service.impl;

import com.ingroup.invoice_web.exception.NotEnoughAssignException;
import com.ingroup.invoice_web.exception.UsedUpAssignException;
import com.ingroup.invoice_web.model.entity.AssignGroup;
import com.ingroup.invoice_web.model.entity.Company;
import com.ingroup.invoice_web.model.entity.Printer;
import com.ingroup.invoice_web.model.repository.AssignGroupRepository;
import com.ingroup.invoice_web.usecase.service.AssignGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignGroupServiceImpl implements AssignGroupService {
    private final static Logger logger = LoggerFactory.getLogger(AssignGroupServiceImpl.class);

    private final AssignGroupRepository assignGroupRepository;

    public AssignGroupServiceImpl(AssignGroupRepository assignGroupRepository) {
        this.assignGroupRepository = assignGroupRepository;
    }


    @Override
    public AssignGroup getAvailableAssign(String yearMonth, Company company, Printer printer) {
        Integer companyId = company.getCompanyId();
        AssignGroup assignGroup = assignGroupRepository.findFirstByYearMonthAndCompanyIdAndStatusOrderByStatusDescAssignIdAsc(yearMonth, companyId, 0)
                .orElseThrow(() -> new NotEnoughAssignException("no enough assign , please set new assign"));
        assignGroup.setPrinterId(printer.getPrinterId());
        assignGroup.setStatus(1);
        assignGroupRepository.save(assignGroup);
        return assignGroup;
    }

    @Override
    public Optional<AssignGroup> getInUseAssign(String yearMonth, Company company, Printer printer) {
        Integer companyId = company.getCompanyId();
        Integer printerId = printer.getPrinterId();
        return assignGroupRepository.findByYearMonthAndCompanyIdAndPrinterIdAndStatus(yearMonth, companyId, printerId, 1);
    }

    @Override
    public Optional<AssignGroup> getPerUseAssign(String yearMonth, Company company, Printer printer) {
        Integer companyId = company.getCompanyId();
        Integer printerId = printer.getPrinterId();
        return assignGroupRepository.findFirstByYearMonthAndCompanyIdAndPrinterIdAndStatusOrderByAssignIdAsc(yearMonth, companyId, printerId, 0);
    }

    @Override
    public String takeAssignNo(AssignGroup assignGroup) throws Exception {

        if (assignGroup.getUsedCount() < 50) {
            String assignNo;
            if (assignGroup.getLastUsedNo() == null) {
                assignNo = assignGroup.getStartNo();
            } else {
                int nextNo = Integer.valueOf(assignGroup.getLastUsedNo()) + 1;
                assignNo = String.format("%08d", nextNo);
            }
            assignGroup.setLastUsedNo(assignNo);
            assignGroup.setUsedCount(assignGroup.getUsedCount() + 1);
            assignGroupRepository.save(assignGroup);

            return assignGroup.getInvoiceTrack() + assignNo;

        } else {
            assignGroup.setStatus(2);
            assignGroupRepository.save(assignGroup);
            logger.warn("此字軌已無號碼");
            throw new UsedUpAssignException("此字軌已無號碼");
        }
    }
}
