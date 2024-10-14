package com.nttdata.clientService.application.services;

import com.nttdata.clientService.infrastructure.utils.validation.ObjectValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.clientService.application.dto.ClientDTO;
import com.nttdata.clientService.application.dto.ResponseDTO;
import com.nttdata.clientService.application.interfaces.IClientService;
import com.nttdata.clientService.domain.ClientEntity;
import com.nttdata.clientService.domain.PersonEntity;
import com.nttdata.clientService.infrastructure.exceptions.CustomException;
import com.nttdata.clientService.infrastructure.repository.ClientPersonRepository;
import com.nttdata.clientService.infrastructure.repository.ClientRepository;
import com.nttdata.clientService.infrastructure.repository.PersonRepository;
import com.nttdata.clientService.infrastructure.utils.ClientResponseMessage;
import com.nttdata.clientService.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientPersonRepository clientPersonRepository;
    private final MapperConvert<ClientDTO, ClientEntity> clientMapper;
    private final PersonRepository personRepository;
    private final TransactionalOperator transactionalOperator;
    private final ClientRepository clientRepository;
    private final ObjectValidation objectValidation;

    @Override
    public Flux<ClientDTO> getAll() {
        return clientPersonRepository
                .findAllClients()
                .map(clientEntity -> clientMapper.toDTO(clientEntity, ClientDTO.class));
    }

    @Override
    public Mono<ClientDTO> getById(Long id) {
        return clientPersonRepository.findClientById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado")))
                .map(clientEntity -> clientMapper.toDTO(clientEntity, ClientDTO.class));
    }



    @Override
    public Mono<ResponseDTO> create(ClientDTO clientDTO) {

        return savePerson(clientDTO)
                .flatMap(personEntity -> {
                    return saveClient(clientDTO, personEntity.getId()).map(clientEntity -> {
                        clientMapper.toDTO(clientEntity, ClientDTO.class);
                        return ClientResponseMessage.successMessage("registrado");
                    });
                }).doOnError(error -> System.err.println("Error in create method: " + error.getMessage()))
                .onErrorReturn(ClientResponseMessage.successMessage("registrado"))
                .as(transactionalOperator::transactional);
    }

    private Mono<PersonEntity> savePerson(ClientDTO data) {
        PersonEntity personEntity = convertToPersonEntity(data);
        return personRepository.save(personEntity);
    }

    private Mono<ClientEntity> saveClient(ClientDTO data, Long id) {
        ClientEntity clientEntity = convertToClientEntity(data, id);
        return clientRepository.save(clientEntity);
    }

    private PersonEntity convertToPersonEntity(ClientDTO data) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(data.getName());
        personEntity.setIdentification(data.getIdentification());
        personEntity.setGender(data.getGender());
        personEntity.setAge(data.getAge());
        personEntity.setDirection(data.getDirection());
        personEntity.setPhone(data.getPhone());
        return personEntity;
    }

    private ClientEntity convertToClientEntity(ClientDTO data, Long id) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setPassword(data.getPassword());
        clientEntity.setStatus(data.getStatus());
        clientEntity.setPersonId(id);
        return clientEntity;
    }

    @Override
    public Mono<ResponseDTO> updateById(ClientDTO clientDTO, Long id) {
        return findUpdatePerson(id, clientDTO)
                .flatMap(savedPerson -> findUpdateClientByPersonId(savedPerson.getId(), clientDTO))
                .then(Mono.just(ClientResponseMessage.successMessage("actualizado")))
                .as(transactionalOperator::transactional);
    }

    private Mono<PersonEntity> findUpdatePerson(Long id, ClientDTO data) {
        return personRepository.findById(id).flatMap(personEntity -> {
            log.info("Exist person: {}", personEntity);
            return updatePersonEntity(personEntity, data);
        });
    }

    private Mono<ClientDTO> findUpdateClientByPersonId(Long id, ClientDTO data) {
        return clientPersonRepository.findClientByPersonId(id)
                .flatMap(clientEntity -> {
                    log.info("Exist client: {}", clientEntity);
                    return updateClientByPersonId(clientEntity.getId(), data.getPassword())
                            .then(Mono.just(clientMapper.toDTO(clientEntity, ClientDTO.class)));
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado " + id)));
    }

    private Mono<Void> updateClientByPersonId(Long id, String data) {
        return clientRepository.updatePassword(id, data);
    }

    private Mono<PersonEntity> updatePersonEntity(PersonEntity personEntity, ClientDTO data) {
        personEntity.setName(data.getName());
        personEntity.setGender(data.getGender());
        personEntity.setAge(data.getAge());
        personEntity.setDirection(data.getDirection());
        personEntity.setPhone(data.getPhone());
        return personRepository.save(personEntity);
    }

    @Override
    public Mono<ResponseDTO> deleteById(Long id) {
        return personRepository.findById(id)
                .flatMap(person ->
                        clientPersonRepository.findClientByPersonId(person.getId())
                                .flatMap(client ->
                                        clientRepository.delete(client)
                                                .then(personRepository.delete(person))
                                ).then(Mono.just(ClientResponseMessage.successMessage("eliminado")))
                )
                .switchIfEmpty(Mono.error(new CustomException("Cliente no encontrado " + id, HttpStatus.NOT_FOUND)))
                .as(transactionalOperator::transactional);
    }

}
