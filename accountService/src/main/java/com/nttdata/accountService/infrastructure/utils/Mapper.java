package com.nttdata.accountService.infrastructure.utils;

import org.modelmapper.ModelMapper;

public class Mapper {
    private Mapper() {
        throw new IllegalStateException("Cannot instantiate a Utility");
    }

    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
