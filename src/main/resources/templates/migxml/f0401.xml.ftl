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
                <PersonInCharge>${seller.personInCharge}</PersonInCharge>
                <TelephoneNumber>${seller.telephoneNumber}</TelephoneNumber>
                <FacsimileNumber>${seller.facsimileNumber}</FacsimileNumber>
                <EmailAddress>${seller.emailAddress}</EmailAddress>
                <CustomerNumber>${seller.customerNumber}</CustomerNumber>
                <RoleRemark>${seller.roleRemark}</RoleRemark>
            </Seller>
            <Buyer>
                <Identifier>${invoiceMain.buyer.identifier}</Identifier>
                <Name>${invoiceMain.buyer.name}</Name>
                <Address>${invoiceMain.buyer.address}</Address>
                <PersonInCharge>${invoiceMain.buyer.personInCharge}</PersonInCharge>
                <TelephoneNumber>${invoiceMain.buyer.telephoneNumber}</TelephoneNumber>
                <FacsimileNumber>${invoiceMain.buyer.facsimileNumber}</FacsimileNumber>
                <EmailAddress>${invoiceMain.buyer.emailAddress}</EmailAddress>
                <CustomerNumber>${invoiceMain.buyer.customerNumber}</CustomerNumber>
                <RoleRemark>${invoiceMain.buyer.roleRemark}</RoleRemark>
            </Buyer>
            <#if invoiceMain.BuyerRemark??>
                <BuyerRemark>${invoiceMain.BuyerRemark}</BuyerRemark>
            </#if>
            <#if invoiceMain.MainRemark??>
                <MainRemark>${invoiceMain.MainRemark}</MainRemark>
            </#if>
            <#if invoiceMain.CustomsClearanceMark??>
                <CustomsClearanceMark>${invoiceMain.CustomsClearanceMark}</CustomsClearanceMark>
            </#if>
            <#if invoiceMain.Category??>
                <Category>${invoiceMain.Category}</Category>
            </#if>
            <#if invoiceMain.RelateNumber??>
                <RelateNumber>${invoiceMain.RelateNumber}</RelateNumber>
            </#if>

            <InvoiceType>${invoiceMain.InvoiceType}</InvoiceType>

            <#if invoiceMain.GroupMark??>
                <GroupMark>${invoiceMain.GroupMark}</GroupMark>
            </#if>

            <DonateMark>${invoiceMain.DonateMarkumber}</DonateMark>

            <#if invoiceMain.CarrierType??>
                <CarrierType>${invoiceMain.CarrierType}</CarrierType>
            </#if>
            <#if invoiceMain.CarrierId1??>
                <CarrierId1>${invoiceMain.CarrierId1}</CarrierId1>
            </#if>
            <#if invoiceMain.CarrierId2??>
                <CarrierId2>${invoiceMain.CarrierId2}</CarrierId2>
            </#if>

            <PrintMark>${invoiceMain.PrintMark}</PrintMark>

            <#if invoiceMain.NPOBAN??>
                <NPOBAN>${invoiceMain.NPOBAN}</NPOBAN>
            </#if>
            <#if invoiceMain.RandomNumber??>
                <RandomNumber>${invoiceMain.RandomNumber}</RandomNumber>
            </#if>
            <#if invoiceMain.BondedAreaConfirm??>
                <BondedAreaConfirm>${invoiceMain.BondedAreaConfirm}</BondedAreaConfirm>
            </#if>
            <#if invoiceMain.ZeroTaxRateReason??>
                <ZeroTaxRateReason>${invoiceMain.ZeroTaxRateReason}</ZeroTaxRateReason>
            </#if>
            <#if invoiceMain.Reserved1??>
                <Reserved1>${invoiceMain.Reserved1}</Reserved1>
            </#if>
            <#if invoiceMain.Reserved2??>
                <Reserved2>${invoiceMain.Reserved2}</Reserved2>
            </#if>
        </Main>

        <Details>
            <#list invoiceDetailList as invoiceDetai>
                <Description>${invoiceDetai.Description}</Description>
                <Quantity>${invoiceDetai.Quantity}</Quantity>
                <#if invoiceDetai.Unit??>
                    <Unit>${invoiceDetai.Unit}</Unit>
                </#if>
                <UnitPrice>${invoiceDetai.UnitPrice}</UnitPrice>
                <Amount>${invoiceDetai.Amount}</Amount>
                <SequenceNumber>${invoiceDetai.SequenceNumber}</SequenceNumber>
                <#if invoiceDetai.Remark??>
                    <Remark>${invoiceDetai.Remark}</Remark>
                </#if>
                <#if invoiceDetai.RelateNumber??>
                    <RelateNumber>${invoiceDetai.RelateNumber}</RelateNumber>
                </#if>
            </#list>
        </Details>

        <Amount>
            <SalesAmount>${invoiceMain.SalesAmount}</SalesAmount>
            <FreeTaxSalesAmount>${invoiceMain.FreeTaxSalesAmount}</FreeTaxSalesAmount>
            <ZeroTaxSalesAmount>${invoiceMain.ZeroTaxSalesAmount}</ZeroTaxSalesAmount>
            <TaxType>${invoiceMain.TaxType}</TaxType>
            <TaxRate>${invoiceMain.TaxRate}</TaxRate>
            <TaxAmount>${invoiceMain.TaxAmount}</TaxAmount>

            <#if invoiceMain.DiscountAmount??>
                <DiscountAmount>${invoiceMain.DiscountAmount}</DiscountAmount>
            </#if>
            <#if invoiceMain.OriginalCurrencyAmount??>
                <OriginalCurrencyAmount>${invoiceMain.OriginalCurrencyAmount}</OriginalCurrencyAmount>
            </#if>
            <#if invoiceMain.ExchangeRate??>
                <ExchangeRate>${invoiceMain.ExchangeRate}</ExchangeRate>
            </#if>
            <#if invoiceMain.Currency??>
                <Currency>${invoiceMain.Currency}</Currency>
            </#if>
        </Amount>
    </Invoice>