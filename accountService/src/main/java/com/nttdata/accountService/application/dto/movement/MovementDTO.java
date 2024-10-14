package com.nttdata.accountService.application.dto.movement;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementDTO {
    Long id;
    String movementType;
    Double balance;
    Double amount;
}
