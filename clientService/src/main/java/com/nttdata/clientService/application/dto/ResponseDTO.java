package com.nttdata.clientService.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String status = "ERR";
    private String message = "Error al ejecutar el servicio";
}
