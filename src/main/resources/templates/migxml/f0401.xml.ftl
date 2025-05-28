<?xml version="1.0" encoding="UTF-8"?>
    <Invoice xmlns="urn:GEINV:eInvoiceMessage:F0401:4.1">
        <Main><!-- 主檔資料 -->
            <InvoiceNumber>${invoiceMain.invoiceNumber}</InvoiceNumber>
            <InvoiceDate>${invoiceMain.invoiceDate}</InvoiceDate>
            <InvoiceTime>${invoiceMain.invoiceTime}</InvoiceTime>
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
                <#if seller.roleRemark??>
                <RoleRemark>${seller.roleRemark}</RoleRemark>
                </#if>
            </Seller>
            <Buyer>
                <Identifier>${invoiceMain.buyer.identifier}</Identifier>
                <Name>${invoiceMain.buyer.name}</Name>
                <#if invoiceMain.buyer.address??>
                    <Address>${invoiceMain.buyer.address}</Address>
                </#if>
                <#if invoiceMain.buyer.personInCharge??>
                    <PersonInCharge>${invoiceMain.buyer.personInCharge}</PersonInCharge>
                </#if>
                <#if invoiceMain.buyer.telephoneNumber??>
                    <TelephoneNumber>${invoiceMain.buyer.telephoneNumber}</TelephoneNumber>
                </#if>
                <#if invoiceMain.buyer.facsimileNumber??>
                   <FacsimileNumber>${invoiceMain.buyer.facsimileNumber}</FacsimileNumber>
                </#if>
                <#if invoiceMain.buyer.emailAddress??>
                    <EmailAddress>${invoiceMain.buyer.emailAddress}</EmailAddress>
                </#if>
                <#if invoiceMain.buyer.customerNumber??>
                    <CustomerNumber>${invoiceMain.buyer.customerNumber}</CustomerNumber>
                </#if>
                <#if invoiceMain.buyer.roleRemark??>
                    <RoleRemark>${invoiceMain.buyer.roleRemark}</RoleRemark>
                </#if>
            </Buyer>
            <#if invoiceMain.buyerRemark??>
                <BuyerRemark>${invoiceMain.buyerRemark}</BuyerRemark>
            </#if>
            <#if invoiceMain.mainRemark??>
                <MainRemark>${invoiceMain.mainRemark}</MainRemark>
            </#if>
            <#if invoiceMain.customsClearanceMark??>
                <CustomsClearanceMark>${invoiceMain.customsClearanceMark}</CustomsClearanceMark>
            </#if>
            <#if invoiceMain.category??>
                <Category>${invoiceMain.category}</Category>
            </#if>
            <#if invoiceMain.relateNumber??>
                <RelateNumber>${invoiceMain.relateNumber}</RelateNumber>
            </#if>

            <InvoiceType>${invoiceMain.invoiceType}</InvoiceType>

            <#if invoiceMain.groupMark??>
                <GroupMark>${invoiceMain.groupMark}</GroupMark>
            </#if>

            <DonateMark>${invoiceMain.donateMark}</DonateMark>

            <#if invoiceMain.carrierType??>
                <CarrierType>${invoiceMain.carrierType}</CarrierType>
            </#if>
            <#if invoiceMain.carrierId1??>
                <CarrierId1>${invoiceMain.carrierId1}</CarrierId1>
            </#if>
            <#if invoiceMain.carrierId2??>
                <CarrierId2>${invoiceMain.carrierId2}</CarrierId2>
            </#if>

            <PrintMark>${invoiceMain.printMark}</PrintMark>

            <#if invoiceMain.npoban??>
                <NPOBAN>${invoiceMain.npoban}</NPOBAN>
            </#if>
            <#if invoiceMain.randomNumber??>
                <RandomNumber>${invoiceMain.randomNumber}</RandomNumber>
            </#if>
            <#if invoiceMain.bondedAreaConfirm??>
                <BondedAreaConfirm>${invoiceMain.bondedAreaConfirm}</BondedAreaConfirm>
            </#if>
            <#if invoiceMain.zeroTaxRateReason??>
                <ZeroTaxRateReason>${invoiceMain.zeroTaxRateReason}</ZeroTaxRateReason>
            </#if>
            <#if invoiceMain.reserved1??>
                <Reserved1>${invoiceMain.reserved1}</Reserved1>
            </#if>
            <#if invoiceMain.reserved2??>
                <Reserved2>${invoiceMain.reserved2}</Reserved2>
            </#if>
        </Main>

        <Details>
            <#list invoiceDetailList as invoiceDetai>
                <Description>${invoiceDetai.description}</Description>
                <Quantity>${invoiceDetai.quantity}</Quantity>
                <#if invoiceDetai.unit??>
                    <Unit>${invoiceDetai.unit}</Unit>
                </#if>
                <UnitPrice>${invoiceDetai.unitPrice}</UnitPrice>
                <TaxType>${invoiceDetai.taxType}</TaxType>
                <Amount>${invoiceDetai.amount}</Amount>
                <SequenceNumber>${invoiceDetai.sequenceNumber}</SequenceNumber>
                <#if invoiceDetai.remark??>
                    <Remark>${invoiceDetai.remark}</Remark>
                </#if>
                <#if invoiceDetai.relateNumber??>
                    <RelateNumber>${invoiceDetai.relateNumber}</RelateNumber>
                </#if>
            </#list>
        </Details>

        <Amount>
            <SalesAmount>${invoiceMain.salesAmount}</SalesAmount>
            <FreeTaxSalesAmount>${invoiceMain.freeTaxSalesAmount}</FreeTaxSalesAmount>
            <ZeroTaxSalesAmount>${invoiceMain.zeroTaxSalesAmount}</ZeroTaxSalesAmount>
            <TaxType>${invoiceMain.taxType}</TaxType>
            <TaxRate>${invoiceMain.taxRate}</TaxRate>
            <TaxAmount>${invoiceMain.taxAmount}</TaxAmount>
            <TotalAmount>${invoiceMain.totalAmount}</TotalAmount>
            <#if invoiceMain.discountAmount??>
                <DiscountAmount>${invoiceMain.discountAmount}</DiscountAmount>
            </#if>
            <#if invoiceMain.originalCurrencyAmount??>
                <OriginalCurrencyAmount>${invoiceMain.originalCurrencyAmount}</OriginalCurrencyAmount>
            </#if>
            <#if invoiceMain.exchangeRate??>
                <ExchangeRate>${invoiceMain.exchangeRate}</ExchangeRate>
            </#if>
            <#if invoiceMain.currency??>
                <Currency>${invoiceMain.currency}</Currency>
            </#if>
        </Amount>
    </Invoice>