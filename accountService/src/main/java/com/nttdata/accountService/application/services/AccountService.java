package com.nttdata.accountService.application.services;

import com.nttdata.accountService.application.dto.account.AccountDTO;
import com.nttdata.accountService.application.dto.account.ResAccountDto;
import com.nttdata.accountService.application.interfaces.IAccountService;
import com.nttdata.accountService.domain.AccountsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.accountService.infrastructure.exceptions.CustomException;
import com.nttdata.accountService.infrastructure.repository.AccountRepository;
import com.nttdata.accountService.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final MapperConvert<ResAccountDto, AccountsEntity> mapperConvert;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperAccountConvert;
    private final ClientService customerService;

    @Override
    public Flux<ResAccountDto> getAll() {
        return accountRepository.findAll()
                .flatMap(accounts -> customerService.getInfoCustomerById(accounts.getClientId())
                        .map(listAccount -> {
                            ResAccountDto resAccountDto = mapperConvert.toDTO(accounts, ResAccountDto.class);
                            resAccountDto.setNames(listAccount.getName());
                            return resAccountDto;
                        }))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<ResAccountDto> getInfo(Long id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("No se encontro cuenta con el id: " + id, HttpStatus.NOT_FOUND)))
                .flatMap(account -> customerService.getInfoCustomerById(account.getClientId())
                        .map(customerDto -> {
                            ResAccountDto resAccountDto = mapperConvert.toDTO(account, ResAccountDto.class);
                            resAccountDto.setNames(customerDto.getName());
                            return resAccountDto;
                        })
                ).switchIfEmpty(Mono.error(new CustomException("No se encontró información del cliente.", HttpStatus.NOT_FOUND)));
    }


    @Override
    public Mono<ResAccountDto> create(AccountDTO data) {
        return customerService.getInfoCustomerById(data.getClientId())
                .switchIfEmpty(Mono.error(new CustomException("Cliente no se encuentra registrado", HttpStatus.NOT_FOUND)))
                .flatMap(customerEntity -> {
                    if (customerEntity != null) {
                        AccountsEntity accountsEntity = mapperAccountConvert.toENTITY(data, AccountsEntity.class);
                        accountsEntity.setStatus(true);
                        accountsEntity.setClientId(Long.parseLong(customerEntity.getId().toString()));
                        return accountRepository.save(accountsEntity)
                                .map(accountSave -> {
                                    ResAccountDto responseDto = mapperConvert.toDTO(accountSave, ResAccountDto.class);
                                    responseDto.setNames(customerEntity.getName());
                                    return responseDto;
                                });
                    } else {
                        return Mono.error(new CustomException("Cliente no se encuentra registrado", HttpStatus.NOT_FOUND));
                    }
                });
    }


    @Override
    public Mono<AccountDTO> update(Long id, AccountDTO data) {
        return accountRepository.findById(id)
                .flatMap(account -> {
                    account.setAccountType(data.getAccountType());
                    account.setStatus(data.getStatus());
                    return accountRepository.save(account);
                }).map(updateAccount -> mapperAccountConvert.toDTO(updateAccount, AccountDTO.class));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return accountRepository.findById(id)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(account -> accountRepository.deleteById(account.getId()))
                .then();
    }
    @Override
    public Mono<AccountDTO> getInfoAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new CustomException("Cuenta No encontrada : " + accountNumber, HttpStatus.NOT_FOUND)))
                .map(accountsEntity -> mapperAccountConvert.toDTO(accountsEntity, AccountDTO.class));
    }
}
