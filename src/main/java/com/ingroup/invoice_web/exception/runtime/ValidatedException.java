package com.ingroup.invoice_web.exception.runtime;

import com.ingroup.invoice_web.util.constant.ErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidatedException extends RuntimeException {

    private final ErrorCodeEnum errorCode;

    public ValidatedException(String message) {
        super(message);
        this.errorCode = null;
    }

    public ValidatedException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public String getErrorCode() {
        return errorCode.getCode();
    }

    public String getErrorMessage(){
        return errorCode.getMessage();
    }

}