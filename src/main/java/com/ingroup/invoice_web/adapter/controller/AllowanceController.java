package com.ingroup.invoice_web.adapter.controller;

import com.ingroup.invoice_web.adapter.dto.AllowanceMainDto;
import com.ingroup.invoice_web.adapter.dto.CanceledAllowanceDto;
import com.ingroup.invoice_web.usecase.service.AllowanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/allowance")
public class AllowanceController {

    private final AllowanceService allowanceService;

    public AllowanceController(AllowanceService allowanceService) {
        this.allowanceService = allowanceService;
    }

    @Operation(summary = "開立單張折讓")
    @PostMapping(value = "/issue")
    public ResponseEntity<String> issueAllowance(@RequestBody AllowanceMainDto allowanceMainDto) {
        //todo 前端驗證過的資料，最好再驗證一次(用ajax controller驗證)

        //規則上一張折讓單可以同時折(同個買/賣受人下)多張發票中的不同項目，但是我不準
        allowanceService.issueAllowance(allowanceMainDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @Operation(summary = "折讓作廢")
    @PostMapping(value = "/cancel")
    public ResponseEntity<String> cancelAllowance(@RequestBody CanceledAllowanceDto cancelAllowanceMainDto) {
        allowanceService.cancelAllowance(cancelAllowanceMainDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
