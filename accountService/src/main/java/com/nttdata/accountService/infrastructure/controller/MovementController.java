package com.nttdata.accountService.infrastructure.controller;

import com.nttdata.accountService.application.dto.movement.DepositDTO;
import com.nttdata.accountService.application.dto.movement.MovementDTO;
import com.nttdata.accountService.application.dto.movement.WithdrawalDTO;
import com.nttdata.accountService.application.interfaces.IMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movements", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovementController {
    @Autowired
    private final IMovementService movementService;


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovementDTO> makeDeposit(@RequestBody @Validated DepositDTO data) {
        return movementService.makeDeposit(data);
    }

    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovementDTO> makeWithdrawal(@RequestBody @Validated WithdrawalDTO data) {
        return movementService.makeWithdrawal(data);
    }


}
