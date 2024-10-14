package com.nttdata.clientService.infrastructure.repository;

import com.nttdata.clientService.domain.ClientEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends R2dbcRepository<ClientEntity, Long> {

    @Modifying
    @Query("UPDATE clients SET password = :password WHERE id = :id")
    Mono<Void> updatePassword(Long id, String password);
}
