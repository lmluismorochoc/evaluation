package com.nttdata.accountService.application.interfaces;

import com.nttdata.accountService.application.dto.account.AccountDTO;
import com.nttdata.accountService.application.dto.account.ResAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {
    Flux<ResAccountDto> getAll();

    Mono<ResAccountDto> getInfo(Long id);

    Mono<ResAccountDto> create(AccountDTO data);

    Mono<AccountDTO> update(Long id, AccountDTO data);

    Mono<Void> delete(Long id);
    Mono<AccountDTO> getInfoAccountByAccountNumber(String accountNumber);
}
