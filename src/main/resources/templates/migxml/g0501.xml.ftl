<?xml version="1.0" encoding="UTF-8"?>
<Invoice xmlns="urn:GEINV:eInvoiceMessage:G0501:4.1">
    <CancelAllowanceNumber>${canceledAllowance.cancelAllowanceNumber}</CancelAllowanceNumber>
    <AllowanceType>${canceledAllowance.allowanceType}</AllowanceType>
    <AllowanceDate>${canceledAllowance.allowanceDate}</AllowanceDate>
    <BuyerId>${canceledAllowance.buyerId}</BuyerId>
    <SellerId>${canceledAllowance.sellerId}</SellerId>
    <CancelDate>${canceledAllowance.cancelDate}</CancelDate>
    <CancelTime>${canceledAllowance.cancelTime}</CancelTime>
    <CancelReason>${canceledAllowance.cancelReason}</CancelReason>

    <#if canceledAllowance.remark??>
        <Remark>${canceledAllowance.remark}</Remark>
    </#if>

</Invoice>