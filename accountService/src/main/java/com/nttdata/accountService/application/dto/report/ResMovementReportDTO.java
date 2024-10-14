package com.nttdata.accountService.application.dto.report;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResMovementReportDTO {
    Long id;
    String movementType;
    Double balance;
    Double amount;
    LocalDate date;
}
