package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.adapter.dto.CanceledInvoiceDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.adapter.dto.VoidedInvoiceDto;

public interface InvoiceService {

    String issueInvoice(InvoiceMainDto invoiceMainDto) throws Exception;

    void cancelInvoice(CanceledInvoiceDto canceledInvoiceDto) throws Exception;

    void voidInvoice(VoidedInvoiceDto voidedInvoiceDto) throws Exception;


}
