package com.nttdata.clientService.infrastructure.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
