package com.nttdata.accountService.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseAccount {
    private String errorCode;
    private String errorMessage;

    @Override
    public String toString() {
        return "ErrorResponseAccount{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
