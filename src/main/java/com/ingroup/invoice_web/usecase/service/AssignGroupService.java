package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.exception.runtime.UsedUpAssignException;
import com.ingroup.invoice_web.model.entity.AssignGroup;
import com.ingroup.invoice_web.model.entity.Company;
import com.ingroup.invoice_web.model.entity.Printer;

import java.util.Optional;

public interface AssignGroupService {

    AssignGroup getAvailableAssign(String yearMonth, Company company, Printer printer) throws Exception;
    Optional<AssignGroup> getInUseAssign(String yearMonth, Company company, Printer printer);
    Optional<AssignGroup> getPerUseAssign(String yearMonth, Company company, Printer printer);


    String takeAssignNo(AssignGroup assignGroup) throws UsedUpAssignException;
}
