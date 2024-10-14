package com.nttdata.clientService.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends PersonDTO {
    @NotBlank
    String password;
    Boolean status;
    Long personId;

    public ClientDTO() {
    }

    public ClientDTO( String name, String gender, Integer age, String identification,
                     String direction, String phone, String password, Boolean status) {
        super( name, gender, age, identification, direction, phone);
        this.password = password;
        this.status = status;
    }
}
