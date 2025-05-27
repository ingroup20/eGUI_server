package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;

public interface InvoiceService {

    String issueInvoice(InvoiceMainDto invoiceMainDto);

    void cancelInvoice(String invoiceId, String cancelReason);


}
