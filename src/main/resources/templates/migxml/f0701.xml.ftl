<?xml version="1.0" encoding="UTF-8"?>
<Invoice xmlns="urn:GEINV:eInvoiceMessage:F0701:4.1">
    <VoidInvoiceNumber>${voidedInvoice.voidInvoiceNumber}</VoidInvoiceNumber>
    <InvoiceDate>${voidedInvoice.invoiceDate}</InvoiceDate>
    <BuyerId>${voidedInvoice.buyerId}</BuyerId>
    <SellerId>${voidedInvoice.sellerId}</SellerId>
    <VoidDate>${voidedInvoice.voidDate}</VoidDate>
    <VoidTime>${voidedInvoice.voidTime}</VoidTime>
    <VoidReason>${voidedInvoice.voidReason}</VoidReason>
    <#if voidedInvoice.remark??>
        <Remark>${voidedInvoice.remark}</Remark>
    </#if>
    <#if voidedInvoice.reserved1??>
        <Reserved1>${voidedInvoice.reserved1}</Reserved1>
    </#if>
    <#if voidedInvoice.reserved2??>
        <Reserved2>${voidedInvoice.reserved2}</Reserved2>
    </#if>
</Invoice>