package com.kamilkorzeniewski.stockcontrol.utils;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;
    private LocalDateTime timeStamp;

    private ApiError() {
        timeStamp = LocalDateTime.now();
    }


    ApiError(HttpStatus httpStatus, String message, Throwable exception) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = exception.getLocalizedMessage();
    }

     HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
