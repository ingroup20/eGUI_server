package com.ingroup.invoice_web.util.constant;


public enum TaxTypeEnum {
    TAXABLE("1", "應稅"),
    ZERO_TAX("2", "零稅率"),
    TAX_FREE("3","免稅"),
    SPECIAL_TAX("4","特種稅"),
    MIXTURE_TAX("9","混合稅");

    private String code;
    private String description;

    TaxTypeEnum(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
