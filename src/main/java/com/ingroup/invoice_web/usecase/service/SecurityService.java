package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.model.entity.Company;
import com.ingroup.invoice_web.model.entity.Printer;
import com.ingroup.invoice_web.model.entity.UserAccount;

public class SecurityService {

    protected UserAccount checkLoginUser() {
        UserAccount userAccount = new UserAccount();
        return userAccount;
    }

    protected Company checkLoginCompany(UserAccount userAccount) {
        Company company = new Company();
        return company;
    }

    protected Printer checkLoginPrinter(UserAccount userAccount) {
        Printer printer = new Printer();
        return printer;
    }
}
