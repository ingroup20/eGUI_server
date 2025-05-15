package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;

public interface IssueInvoiceService {

    String issueInvoice(InvoiceMainDto invoiceMainDto);

//    InvoiceMainDto calculateAmount(InvoiceMainDto invoiceMainDto);
//
//    InvoiceMainDto calculateTax(InvoiceMainDto invoiceMainDto);
//
//    String generateXML(InvoiceMainDto invoiceMainDto);
}
