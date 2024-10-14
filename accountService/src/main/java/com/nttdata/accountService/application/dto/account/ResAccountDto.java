package com.nttdata.accountService.application.dto.account;

import lombok.Data;

@Data
public class ResAccountDto {
    Long id;
    String accountNumber;
    String accountType;
    Double initialBalance;
    Boolean status;
    String names;
}
