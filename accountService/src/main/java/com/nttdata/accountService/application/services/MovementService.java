package com.nttdata.accountService.application.services;

import com.nttdata.accountService.application.dto.movement.DepositDTO;
import com.nttdata.accountService.application.dto.movement.MovementDTO;
import com.nttdata.accountService.application.dto.report.ResReportDto;
import com.nttdata.accountService.application.dto.movement.WithdrawalDTO;
import com.nttdata.accountService.application.interfaces.IAccountService;
import com.nttdata.accountService.application.interfaces.IClientService;
import com.nttdata.accountService.application.interfaces.IMovementService;
import com.nttdata.accountService.domain.MovementsEntity;
import com.nttdata.accountService.infrastructure.repository.AccountRepository;
import com.nttdata.accountService.infrastructure.utils.MapperConvert;
import com.nttdata.accountService.infrastructure.utils.enums.TypeMovementEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.nttdata.accountService.infrastructure.exceptions.CustomException;
import com.nttdata.accountService.infrastructure.repository.MovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementService implements IMovementService {
    private final IAccountService accountService;
    private final IClientService clientService;
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;


    private Boolean validateAccount(String originAccount, String destinationAccount) {
        return destinationAccount.equals(originAccount);
    }


    @Override
    public Mono<MovementDTO> makeWithdrawal(WithdrawalDTO withdrawalDTO) {
        return accountService.getInfoAccountByAccountNumber(withdrawalDTO.getAccount())
                .flatMap(existAccount ->  {

                            Double newBalance = existAccount.getInitialBalance() - withdrawalDTO.getAmount();
                            if (existAccount.getInitialBalance() < withdrawalDTO.getAmount()) {
                                return Mono.error(new CustomException("Saldo insuficiente. Saldo actual: " + existAccount.getInitialBalance(), HttpStatus.BAD_REQUEST));
                            }
                            return registerMovement(existAccount.getId(), -withdrawalDTO.getAmount(), newBalance)
                                    .doOnSuccess(movement -> log.info("Se registro correctamente la transacción"));
                        });
    }
    @Override
    public Mono<MovementDTO> makeDeposit(DepositDTO depositDTO) {
        return accountService.getInfoAccountByAccountNumber(depositDTO.getAccount())
                .flatMap(existAccount  -> {
                    Double newBalance = existAccount.getInitialBalance() + depositDTO.getAmount();
                            return registerMovement(existAccount.getId(), depositDTO.getAmount(), newBalance)
                                    .doOnSuccess(movement -> log.info("Se registro correctamente la transacción"));
                        });
    }


    @Override
    public Mono<ResReportDto> getReport(Long clientId, LocalDate startDate, LocalDate endDate) {
        return clientService.getInfoCustomerById(clientId)
                .switchIfEmpty(Mono.error(new CustomException("No se encontró el cliente especificado.", HttpStatus.NOT_FOUND)))
                .flatMap(clientDto -> {
                    ResReportDto report = new ResReportDto();
                    report.setClient(clientDto);

                    return accountRepository.findAccontsByClientId(clientId)
                            .collectList()
                            .flatMap(accounts -> {
                                report.setAccounts(accounts);
                                return Flux.fromIterable(accounts)
                                        .flatMap(account -> {
                                            // Obtener movimientos para cada cuenta en el rango de fechas
                                            return movementRepository.findMovementsByAccountIdAndDateRange(account.getId(), startDate, endDate)
                                                    .collectList()
                                                    .flatMap(movements -> {
                                                        account.setMovements(movements);
                                                        return Mono.just(account);
                                                    });
                                        })
                                        .collectList()
                                        .flatMap(updatedAccounts -> {
                                            report.setAccounts(updatedAccounts);
                                            return Mono.just(report);
                                        });
                            });
                });
    }
    public Mono<MovementDTO> getMovementByAccountId(Long movementId) {
        return movementRepository.findFirstByAccountIdOrderByDateDesc(movementId)
                .map(existMovement -> mapperConvert.toDTO(existMovement, MovementDTO.class))
                .switchIfEmpty(Mono.empty());
    }
    public Mono<MovementDTO> registerMovement(Long accountId, Double amount, Double newBalance) {
        MovementsEntity movement = new MovementsEntity();
        movement.setMovementType(amount < 0 ? TypeMovementEnums.DEBIT.toString() : TypeMovementEnums.CREDIT.toString());
        movement.setBalance(newBalance);
        movement.setAmount(amount);
        movement.setAccountId(accountId);
        return movementRepository.save(movement)
                .flatMap(savedMovement -> {
                    return accountRepository.findById(accountId)
                            .flatMap(account -> {
                                account.setInitialBalance(newBalance);
                                return accountRepository.save(account)
                                        .map(updatedAccount -> mapperConvert.toDTO(savedMovement, MovementDTO.class));
                            });
                });

    }

}
