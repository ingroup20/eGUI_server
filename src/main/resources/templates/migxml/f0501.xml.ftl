<?xml version="1.0" encoding="UTF-8"?>
<Invoice xmlns="urn:GEINV:eInvoiceMessage:F0501:4.1">
    <CancelInvoiceNumber>${canceledInvoice.cancelInvoiceNumber}</CancelInvoiceNumber>
    <InvoiceDate>${canceledInvoice.invoiceDate}</InvoiceDate>
    <BuyerId>${canceledInvoice.buyerId}</BuyerId>
    <SellerId>${canceledInvoice.sellerId}</SellerId>
    <CancelDate>${canceledInvoice.cancelDate}</CancelDate>
    <CancelTime>${canceledInvoice.cancelTime}</CancelTime>
    <CancelReason>${canceledInvoice.cancelReason}</CancelReason>

    <#if canceledInvoice.returnTaxDocumentNumber??>
        <ReturnTaxDocumentNumber>${canceledInvoice.returnTaxDocumentNumber}</ReturnTaxDocumentNumber>
    </#if>
    <#if canceledInvoice.remark??>
        <Remark>${canceledInvoice.remark}</Remark>
    </#if>
    <#if canceledInvoice.reserved1??>
        <Reserved1>${canceledInvoice.reserved1}</Reserved1>
    </#if>
    <#if canceledInvoice.reserved2??>
        <Reserved2>${canceledInvoice.reserved2}</Reserved2>
    </#if>
</Invoice>