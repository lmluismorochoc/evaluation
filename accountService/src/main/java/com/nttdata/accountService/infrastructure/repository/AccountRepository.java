package com.nttdata.accountService.infrastructure.repository;

import com.nttdata.accountService.application.dto.report.ResAccountReportDTO;
import com.nttdata.accountService.domain.AccountsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends R2dbcRepository<AccountsEntity, Long> {

    @Query("SELECT ac.* FROM accounts ac where ac.account_number = :accountNumber")
    Mono<AccountsEntity> findByAccountNumber(String accountNumber);

    @Query("SELECT  a.id,a.account_type, a.account_number, a.initial_balance, a.status  " +
            "FROM accounts a " +
            "where a.client_id = :clientId ")
    Flux<ResAccountReportDTO> findAccontsByClientId(@Param("clientId") Long clientId);

}
