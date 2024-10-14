package com.nttdata.accountService.application.dto.report;

import com.nttdata.accountService.application.dto.movement.MovementDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResAccountReportDTO {
    Long id;
    String accountNumber;
    String accountType;
    Double initialBalance;
    Boolean status;
    private List<ResMovementReportDTO> movements;
}
