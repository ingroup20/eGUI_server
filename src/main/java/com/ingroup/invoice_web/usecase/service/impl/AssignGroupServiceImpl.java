package com.ingroup.invoice_web.usecase.service.impl;

import com.ingroup.invoice_web.exception.NotEnoughAssignException;
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


//    @Override
//    public Optional<AssignGroup> getRemainingNo(String yearMonth, Company company, Printer printer) {
//        Integer companyId = company.getCompanyId();
//        Integer printerId = printer.getPrinterId();
//        return assignGroupRepository.findByCompanyAndPrinterAndInUse(yearMonth, companyId, printerId);
//    }

    @Override
    public Optional<AssignGroup> getAvailableAssign(String yearMonth, Company company, Printer printer) {
        Integer companyId = company.getCompanyId();
        Integer printerId = printer.getPrinterId();
        return assignGroupRepository.findByCompanyAndPrinterAndAvailable(yearMonth, companyId, printerId);
    }

    @Override
    public String takeAssignNo(AssignGroup assignGroup) throws Exception {
        Integer endNo = assignGroup.getStartNo() + 49; //一組50號，紀錄取二位數
        Integer lastNo = assignGroup.getStartNo() + assignGroup.getUsedCount() - 1; //已被使用的最後一號

        if (lastNo < endNo) {
            if (assignGroup.getLastUsedNo().substring(8, 10).equals(lastNo.toString())) {
                String assignNo = assignGroup.getLastUsedNo().substring(0, 8) + (lastNo + 1);
                return assignNo;
            } else {
                logger.error("嚴重字軌錯誤");
                throw new Exception("嚴重字軌錯誤");
            }

        } else {
            logger.warn("此字軌已無號碼");
            throw new NotEnoughAssignException("此字軌已無號碼");
        }
    }
}
