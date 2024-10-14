package com.nttdata.accountService.application.interfaces;

import com.nttdata.accountService.application.dto.client.ClientDto;
import reactor.core.publisher.Mono;

public interface IClientService {

    Mono<ClientDto> getInfoCustomerById(Long id);
}
