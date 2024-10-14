package com.nttdata.accountService.application.dto.movement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawalDTO {

    @NotNull(message = "account cannot be null")
    @NotBlank(message = "account cannot be empty")
    String account;
    @NotNull(message = "Amount cannot be null")
    Double amount;
}
