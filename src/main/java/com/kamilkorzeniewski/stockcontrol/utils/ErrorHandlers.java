package com.kamilkorzeniewski.stockcontrol.utils;

import com.kamilkorzeniewski.stockcontrol.exception.FileStorageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlers extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"Wrong json request",ex);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(FileStorageException.class)
    @ResponseBody
    public ResponseEntity<ApiError> FileStorageExceptionHandler(Throwable ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getMessage(),ex);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }




}
