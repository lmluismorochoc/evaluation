package com.nttdata.accountService.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.accountService.application.dto.account.AccountDTO;
import com.nttdata.accountService.application.dto.account.ResAccountDto;
import com.nttdata.accountService.application.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    private final IAccountService IAccountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ResAccountDto> getAll() {
        return IAccountService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResAccountDto> getAccountById(@PathVariable Long id) {
        return IAccountService.getInfo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResAccountDto> registerAccount(@RequestBody @Validated AccountDTO data) {
        return IAccountService.create(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDTO> updateAccount(@RequestBody @Validated AccountDTO data, @PathVariable Long id) {
        return IAccountService.update(id, data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteAccount(@PathVariable Long id) {
        return IAccountService.delete(id);
    }
}
