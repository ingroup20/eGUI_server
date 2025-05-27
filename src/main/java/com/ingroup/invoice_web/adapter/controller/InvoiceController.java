package com.ingroup.invoice_web.adapter.controller;

import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.usecase.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "開立單張發票")
    @PostMapping(value = "/issue")
    public ResponseEntity<String> issueInvoice(@RequestBody InvoiceMainDto invoiceMainDto) {
        //單張發票開立，由前端驗證資料
        String invoiceNumber = invoiceService.issueInvoice(invoiceMainDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceNumber);
    }

    @Operation(summary = "註銷單張發票")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelInvoice(@PathVariable Long id) {


        return ResponseEntity.status(HttpStatus.CREATED).body("");
    };

    @Operation(summary = "作廢單張發票")
    @PostMapping("/{id}/void")
    public ResponseEntity<?> voidInvoice(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    };

}
