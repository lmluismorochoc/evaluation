package com.nttdata.clientService.application.interfaces;

import com.nttdata.clientService.application.dto.ClientDTO;
import com.nttdata.clientService.application.dto.ResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {
    Flux<ClientDTO> getAll();

    Mono<ClientDTO> getById(Long id);

    Mono<ResponseDTO> create(ClientDTO data);

    Mono<ResponseDTO> updateById(ClientDTO clientDTO, Long id);

    Mono<ResponseDTO> deleteById(Long id);
}
