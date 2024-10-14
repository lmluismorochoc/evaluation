package com.nttdata.accountService.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseAccount> handleGeneralException(Exception ex) {
        ErrorResponseAccount errorResponse = new ErrorResponseAccount("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseAccount> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseAccount errorResponse = new ErrorResponseAccount("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
