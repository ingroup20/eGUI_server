<?xml version="1.0" encoding="UTF-8"?>
<Invoice xmlns="urn:GEINV:eInvoiceMessage:G0401:4.1">
    <Main>
        <AllowanceNumber>${allowanceMain.allowanceNumber}</AllowanceNumber>
        <AllowanceDate>${allowanceMain.AllowanceDate}</AllowanceDate>
        <Seller>
            <Identifier>${seller.identifier}</Identifier>
            <Name>${seller.name}</Name>
            <Address>${seller.address}</Address>
            <#if seller.personInCharge??>
                <PersonInCharge>${seller.personInCharge}</PersonInCharge>
            </#if>
            <#if seller.telephoneNumber??>
                <TelephoneNumber>${seller.telephoneNumber}</TelephoneNumber>
            </#if>
            <#if seller.facsimileNumber??>
                <FacsimileNumber>${seller.facsimileNumber}</FacsimileNumber>
            </#if>
            <#if seller.emailAddress??>
                <EmailAddress>${seller.emailAddress}</EmailAddress>
            </#if>
            <#if seller.customerNumber??>
                <CustomerNumber>${seller.customerNumber}</CustomerNumber>
            </#if>
        </Seller>
        <Buyer>
            <Identifier>${allowanceMain.buyer.identifier}</Identifier>
            <Name>${allowanceMain.buyer.name}</Name>
            <#if allowanceMain.buyer.address??>
                <Address>${allowanceMain.buyer.address}</Address>
            </#if>
            <#if allowanceMain.buyer.personInCharge??>
                <PersonInCharge>${allowanceMain.buyer.personInCharge}</PersonInCharge>
            </#if>
            <#if allowanceMain.buyer.telephoneNumber??>
                <TelephoneNumber>${allowanceMain.buyer.telephoneNumber}</TelephoneNumber>
            </#if>
            <#if allowanceMain.buyer.facsimileNumber??>
                <FacsimileNumber>${allowanceMain.buyer.facsimileNumber}</FacsimileNumber>
            </#if>
            <#if allowanceMain.buyer.emailAddress??>
                <EmailAddress>${allowanceMain.buyer.emailAddress}</EmailAddress>
            </#if>
            <#if allowanceMain.buyer.customerNumber??>
                <CustomerNumber>${allowanceMain.buyer.customerNumber}</CustomerNumber>
            </#if>
        </Buyer>
        <AllowanceType>${allowanceMain.allowanceType}</AllowanceType>
        <#if allowanceMain.originalInvoiceSellerId??>
            <OriginalInvoiceSellerId>${allowanceMain.originalInvoiceSellerId}</OriginalInvoiceSellerId>
        </#if>
        <#if allowanceMain.originalInvoiceBuyerId??>
            <OriginalInvoiceBuyerId>${allowanceMain.originalInvoiceBuyerId}</OriginalInvoiceBuyerId>
        </#if>
    </Main>

    <Details>
        <#list allowanceDetailList as allowanceDetail>
            <ProductItem>
                <OriginalInvoiceDate>${allowanceDetail.originalInvoiceDate}</OriginalInvoiceDate>
                <OriginalInvoiceNumber>${allowanceDetail.originalInvoiceNumber}</OriginalInvoiceNumber>
                <#if allowanceDetail.originalSequenceNumber??>
                    <OriginalSequenceNumber>${allowanceDetail.originalSequenceNumber}</OriginalSequenceNumber>
                </#if>
                <OriginalDescription>${allowanceDetail.originalDescription}</OriginalDescription>
                <Quantity>${allowanceDetail.quantity}</Quantity>
                <#if allowanceDetail.unit??>
                    <Unit>${allowanceDetail.unit}</Unit>
                </#if>
                <UnitPrice>${allowanceDetail.unitPrice}</UnitPrice>
                <Amount>${allowanceDetail.amount}</Amount>
                <Tax>${allowanceDetail.tax}</Tax>
                <AllowanceSequenceNumber>${allowanceDetail.AllowanceSequenceNumber}</AllowanceSequenceNumber>
                <TaxType>${allowanceDetail.taxType}</TaxType>
            </ProductItem>
        </#list>
    </Details>

    <Amount>
        <TaxAmount>${allowanceMain.salesAmount}</TaxAmount>
        <TotalAmount>${allowanceMain.freeTaxSalesAmount}</TotalAmount>
    </Amount>
</Invoice>