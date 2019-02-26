package com.kamilkorzeniewski.stockcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceLoadException extends RuntimeException {
    public InvoiceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
