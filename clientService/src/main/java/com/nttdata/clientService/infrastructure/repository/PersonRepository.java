package com.nttdata.clientService.infrastructure.repository;

import com.nttdata.clientService.domain.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {
}
