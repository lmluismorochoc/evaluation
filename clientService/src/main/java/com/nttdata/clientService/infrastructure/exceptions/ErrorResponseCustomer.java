package com.nttdata.clientService.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseCustomer {
    private String errorCode;
    private String errorMessage;
}
