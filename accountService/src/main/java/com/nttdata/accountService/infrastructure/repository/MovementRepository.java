package com.nttdata.accountService.infrastructure.repository;

import com.nttdata.accountService.application.dto.movement.MovementDTO;
import com.nttdata.accountService.application.dto.report.ResMovementReportDTO;
import com.nttdata.accountService.domain.MovementsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface MovementRepository extends R2dbcRepository<MovementsEntity, Long> {

    @Query("SELECT id, movement_type, balance, amount, account_id FROM public.movements m where m.account_id = :accountId order by date desc LIMIT 1")
    Mono<MovementsEntity> findFirstByAccountIdOrderByDateDesc(@Param("accountId") Long accountId);

    @Query("SELECT m.*  " +
            "FROM movements m " +
            "where m.account_id = :accountId and date BETWEEN :startDate and :endDate")
    Flux<ResMovementReportDTO> findMovementsByAccountIdAndDateRange(@Param("accountId") Long accountId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
