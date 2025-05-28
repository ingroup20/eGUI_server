package com.ingroup.invoice_web.exception;

public class TryLockOverTimeException extends RuntimeException {

    public TryLockOverTimeException(String message) {
        super(message);
    }
}
