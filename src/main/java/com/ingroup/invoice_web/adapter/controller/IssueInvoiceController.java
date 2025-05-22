package com.ingroup.invoice_web.adapter.controller;

import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.usecase.service.IssueInvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/issue/invoice/")
public class IssueInvoiceController {

    private final IssueInvoiceService issueInvoiceService;

    public IssueInvoiceController(IssueInvoiceService issueInvoiceService) {
        this.issueInvoiceService = issueInvoiceService;
    }

    @Operation(summary = "單張發票開立")
    @PostMapping
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceMainDto invoiceMainDto) {
        //單張發票開立，由前端驗證資料
        String invoiceNumber = issueInvoiceService.issueInvoice(invoiceMainDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceNumber);
    }
}