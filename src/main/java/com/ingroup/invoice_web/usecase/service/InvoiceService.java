package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.adapter.dto.CanceledInvoiceDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.adapter.dto.VoidedInvoiceDto;

public interface InvoiceService {

    String issueInvoice(InvoiceMainDto invoiceMainDto);

    void cancelInvoice(CanceledInvoiceDto canceledInvoiceDto);

    void voidInvoice(VoidedInvoiceDto voidedInvoiceDto);


}
