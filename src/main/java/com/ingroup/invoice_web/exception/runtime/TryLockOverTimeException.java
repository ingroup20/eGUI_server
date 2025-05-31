package com.ingroup.invoice_web.exception.runtime;

public class TryLockOverTimeException extends RuntimeException {

    public TryLockOverTimeException(String message) {
        super(message);
    }
}
