package org.bian.accountbalance.resourceserver.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bian.accountbalance.common.data.api.ErrorResponse;
import org.bian.accountbalance.common.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> unknownException(EntityNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ErrorResponse(404, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> unknownException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}