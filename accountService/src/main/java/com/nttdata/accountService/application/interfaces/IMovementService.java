package com.nttdata.accountService.application.interfaces;

import com.nttdata.accountService.application.dto.movement.DepositDTO;
import com.nttdata.accountService.application.dto.movement.MovementDTO;
import com.nttdata.accountService.application.dto.report.ResReportDto;
import com.nttdata.accountService.application.dto.movement.WithdrawalDTO;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface IMovementService {

    Mono<MovementDTO> makeWithdrawal(WithdrawalDTO withdrawalDTO);

    Mono<MovementDTO> makeDeposit(DepositDTO depositDTO);

    Mono<ResReportDto> getReport(Long clientId, LocalDate startDate, LocalDate endDate);
    //Mono<MovementDTO> registerMovement(Long accountId, Double amount, Double newBalance);
   // Mono<MovementDTO> getMovementByAccountId(Long movementId);


}
