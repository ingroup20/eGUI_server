package com.ingroup.invoice_web.util.constant;


public enum ErrorCodeEnum {

    VI001("混合稅不能同時包含免稅與零稅率"),
    VI002("混合稅總金額加總錯誤"),
    VI003("特總稅金額檢查未通過");

    private String message;

    ErrorCodeEnum(String message) {
        this.message = message;
    }

    public String getCode() {
        return name(); // VI001 就是錯誤碼，不用額外宣告
    }

    public String getMessage() {
        return message;
    }
}
