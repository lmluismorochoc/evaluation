package com.nttdata.clientService.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonDTO {
    Long id;
    @NotNull(message = "{name.not.null}")
    @NotBlank(message = "{name.not.blank}")
    String name;
    @NotNull(message = "{gender.not.null}")
    @NotBlank(message = "{gender.not.blank}")
    String gender;
    @NotNull(message = "{age.not.null}")
    Integer age;
    @NotNull(message = "{identification.not.null}")
    @NotBlank(message = "{identification.not.blank}")
    String identification;
    @NotNull(message = "{direction.not.null}")
    @NotBlank(message = "{direction.not.blank}")
    String direction;
    @NotNull(message = "{phone.not.null}")
    @NotBlank(message = "{phone.not.blank}")
    String phone;

    public PersonDTO() {
    }

    public PersonDTO(String name, String gender, Integer age, String identification, String direction, String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.identification = identification;
        this.direction = direction;
        this.phone = phone;
    }
}
