package com.kamilkorzeniewski.stockcontrol.exception.handler;

import com.kamilkorzeniewski.stockcontrol.exception.ApiError;
import com.kamilkorzeniewski.stockcontrol.exception.FileStorageException;
import com.kamilkorzeniewski.stockcontrol.exception.InvoiceLoadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Wrong json request", ex);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(FileStorageException.class)
    @ResponseBody
    public ResponseEntity<ApiError> fileStorageExceptionHandler(Throwable ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(InvoiceLoadException.class)
    public ResponseEntity<ApiError> invoiceLoadException(Throwable ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> noSuchElementException(Throwable ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> authenticationException(Throwable ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }


}
