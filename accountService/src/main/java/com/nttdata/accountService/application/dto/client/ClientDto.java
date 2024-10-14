package com.nttdata.accountService.application.dto.client;

import lombok.Data;

@Data
public class ClientDto {
    Long id;
    String name;
    String gender;
    Integer age;
    String identification;
    String direction;
    String phone;
    String password;
    Boolean status;
    Long personId;
}
