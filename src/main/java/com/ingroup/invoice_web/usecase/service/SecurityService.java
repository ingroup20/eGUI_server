package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.model.entity.Company;
import com.ingroup.invoice_web.model.entity.Printer;
import com.ingroup.invoice_web.model.entity.UserAccount;
import com.ingroup.invoice_web.model.repository.CompanyRepository;
import com.ingroup.invoice_web.model.repository.PrinterRepository;
import com.ingroup.invoice_web.model.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final CompanyRepository companyRepository;
    private final UserAccountRepository userAccountRepository;
    private final PrinterRepository printerRepository;

    public SecurityService(CompanyRepository companyRepository,
                           UserAccountRepository userAccountRepository,
                           PrinterRepository printerRepository) {
        this.companyRepository = companyRepository;
        this.userAccountRepository = userAccountRepository;
        this.printerRepository = printerRepository;
    }

    public UserAccount checkLoginUser() {
        UserAccount userAccount = userAccountRepository.findById(1).orElse(null);
        return userAccount;
    }

    public Company checkLoginCompany(UserAccount userAccount) {
        Company company = companyRepository.findById(1).orElse(null);
        return company;
    }

    public Printer checkLoginPrinter(UserAccount userAccount) {
        Printer printer = printerRepository.findById(1).orElse(null);
        return printer;
    }
}
