package com.nttdata.clientService.infrastructure.utils;


import com.nttdata.clientService.application.dto.ResponseDTO;

public class ClientResponseMessage {


    public static ResponseDTO successMessage(String operation) {
        return new ResponseDTO(
                "OK",
                "Cliente " +operation+"  exitosamente"
        );
    }
}
