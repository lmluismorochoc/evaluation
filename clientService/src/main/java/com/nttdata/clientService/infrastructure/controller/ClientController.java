package com.nttdata.clientService.infrastructure.controller;

import com.nttdata.clientService.application.dto.ClientDTO;
import com.nttdata.clientService.application.dto.ResponseDTO;
import com.nttdata.clientService.application.interfaces.IClientService;
import com.nttdata.clientService.infrastructure.utils.docs.ClientOpenApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@ClientOpenApi
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private final IClientService IClientService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClientDTO> getAllClients() {
        return IClientService.getAll();
    }

    @GetMapping("/info-client/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO> getInfoClientById(@PathVariable Long id) {
        return IClientService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDTO> save(@RequestBody @Validated ClientDTO clientDTO) {
        return IClientService.create(clientDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDTO> updateClient(@RequestBody @Validated ClientDTO clientDTO, @PathVariable Long id) {
        return IClientService.updateById(clientDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseDTO> deleteClient(@PathVariable Long id) {
        return IClientService.deleteById(id);
    }


}
