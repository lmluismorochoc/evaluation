package com.nttdata.clientService.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseCustomer> handleGeneralException(Exception ex) {
        ErrorResponseCustomer errorResponse = new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Ha ocurrido un error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseCustomer> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseCustomer errorResponse = new ErrorResponseCustomer("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
