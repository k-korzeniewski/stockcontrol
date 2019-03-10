package com.kamilkorzeniewski.stockcontrol.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private String extendedMessage;
    private LocalDateTime timeStamp;

    private ApiError() {
        timeStamp = LocalDateTime.now();
    }


     ApiError(HttpStatus httpStatus, String message, Throwable exception) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.extendedMessage = exception.getCause().getMessage();
    }

    @JsonIgnore
     HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
    @JsonProperty("extendedMessage")
    public String getExtendedMessage() {
        return extendedMessage;
    }
    @JsonProperty("timeStamp")
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
