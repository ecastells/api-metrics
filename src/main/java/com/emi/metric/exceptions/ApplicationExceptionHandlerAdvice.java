package com.emi.metric.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Emi on 24/07/2017.
 */
@RestControllerAdvice
public class ApplicationExceptionHandlerAdvice {
    @ExceptionHandler(OlderTransactionException.class)
    public ResponseEntity handleIdNotFoundException(OlderTransactionException ex) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
