package com.nttdata.accountService.infrastructure.controller;

import com.nttdata.accountService.application.dto.report.ResReportDto;
import com.nttdata.accountService.application.interfaces.IMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {
    @Autowired
    private final IMovementService movementService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResReportDto> getReportMovement(@RequestParam Long clientId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return movementService.getReport(clientId, startDate, endDate);
    }


}
