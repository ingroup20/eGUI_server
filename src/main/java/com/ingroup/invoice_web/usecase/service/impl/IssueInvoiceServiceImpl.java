package com.ingroup.invoice_web.usecase.service.impl;

import com.ingroup.invoice_web.adapter.dto.InvoiceDetailDto;
import com.ingroup.invoice_web.adapter.dto.InvoiceMainDto;
import com.ingroup.invoice_web.exception.ValidatedException;
import com.ingroup.invoice_web.usecase.service.IssueInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static com.ingroup.invoice_web.util.constant.ErrorCodeEnum.*;
import static com.ingroup.invoice_web.util.constant.TaxTypeEnum.*;

@Service
public class IssueInvoiceServiceImpl implements IssueInvoiceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String issueInvoice(InvoiceMainDto invoiceMainDto) {
        String invoiceNumber = "";
        String yearMonth = "";
        invoiceMainDto = calculateAmount(invoiceMainDto);
        invoiceMainDto = calculateTax(invoiceMainDto);
        generateXML(invoiceMainDto);
        logger.info("issueInvoice invoice number: {}, yearMonth: {}, invoice date: {}", invoiceNumber, yearMonth, invoiceMainDto.getInvoiceDate());
        return invoiceNumber;
    }


    public InvoiceMainDto calculateAmount(InvoiceMainDto invoiceMainDto) throws ValidatedException {

        //總金額
        List<InvoiceDetailDto> detailList = invoiceMainDto.getInvoiceDetailDtoList();
        BigDecimal totalAmount = detailList.stream()
                .map(InvoiceDetailDto::getSalesAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //應稅金額
        BigDecimal salesAmount = detailList.stream()
                .filter(dt -> TAXABLE.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getSalesAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //免稅金額
        BigDecimal freeTaxSalesAmount = detailList.stream()
                .filter(dt -> TAX_FREE.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getSalesAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //零稅率金額
        BigDecimal zeroTaxSalesAmount = detailList.stream()
                .filter(dt -> ZERO_TAX.getCode().equals(dt.getTaxType()))
                .map(InvoiceDetailDto::getSalesAmount)
                .filter(Objects::nonNull) //避免NullPointException
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //特種稅
        if (SPECIAL_TAX.getCode().equals(invoiceMainDto.getTaxType())
                && (!salesAmount.equals(BigDecimal.ZERO) || !freeTaxSalesAmount.equals(BigDecimal.ZERO) || !zeroTaxSalesAmount.equals(BigDecimal.ZERO))) {
            throw new ValidatedException(VI003);

        } else if (SPECIAL_TAX.getCode().equals(invoiceMainDto.getTaxType())) {
            salesAmount = detailList.stream()
                    .filter(dt -> SPECIAL_TAX.getCode().equals(dt.getTaxType()))
                    .map(InvoiceDetailDto::getSalesAmount)
                    .filter(Objects::nonNull) //避免NullPointException
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        //混合稅
        if (MIXTURE_TAX.getCode().equals(invoiceMainDto.getTaxType())) {
            if (!freeTaxSalesAmount.equals(BigDecimal.ZERO) && !zeroTaxSalesAmount.equals(BigDecimal.ZERO)) {
                throw new ValidatedException(VI001);
            }

            if (!totalAmount.equals(salesAmount.add(freeTaxSalesAmount).add(zeroTaxSalesAmount))) {
                throw new ValidatedException(VI002);
            }
        }

        invoiceMainDto.setTotalAmount(totalAmount);
        invoiceMainDto.setSalesAmount(salesAmount);
        invoiceMainDto.setFreeTaxSalesAmount(freeTaxSalesAmount);
        invoiceMainDto.setZeroTaxSalesAmount(zeroTaxSalesAmount);


        return invoiceMainDto;
    }


    public InvoiceMainDto calculateTax(InvoiceMainDto invoiceMainDto) {
        BigDecimal taxAmount = new BigDecimal(BigInteger.ZERO);
        BigDecimal taxRate = invoiceMainDto.getTaxRate();
        taxAmount = taxAmount.add(taxRate.multiply(invoiceMainDto.getSalesAmount()));
        invoiceMainDto.setTaxAmount(taxAmount); //總稅額
        return invoiceMainDto;
    }


    public String generateXML(InvoiceMainDto invoiceMainDto) {
        String invoiceNumber = "AA00000000";
        return invoiceNumber;
    }


}
