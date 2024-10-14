package com.nttdata.clientService.infrastructure.repository;

import com.nttdata.clientService.domain.ClientEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientPersonRepository extends R2dbcRepository<ClientEntity, Long> {

    @Query("select c.*, p.* from clients c inner join persons p on p.id = c.person_id ")
    Flux<ClientEntity> findAllClients();

    @Query("select c.*, p.* from public.clients c inner join public.persons p on p.id = c.person_id where c.id =:id")
    Mono<ClientEntity> findClientById(Long id);

    @Query("select c.*, p.* from public.clients c inner join public.persons p on p.id = c.person_id where p.identification =:identification")
    Mono<ClientEntity> findClientByIdentification(String identification);

    @Query("select c.* from public.clients c where c.person_id =:id")
    Mono<ClientEntity> findClientByPersonId(Long id);
}
