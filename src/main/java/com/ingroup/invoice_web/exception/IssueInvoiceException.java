package com.ingroup.invoice_web.exception;

import com.ingroup.invoice_web.util.constant.ErrorCodeEnum;

public class IssueInvoiceException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public IssueInvoiceException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
