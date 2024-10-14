package com.nttdata.accountService.application.services;

import com.nttdata.accountService.application.dto.client.ClientDto;
import com.nttdata.accountService.application.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.accountService.infrastructure.externalApi.HttpClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final HttpClientService httpClientService;
    @Value("${uri.base.http.url}")
    private String uri;



    @Override
    public Mono<ClientDto> getInfoCustomerById(Long id) {
        String apiCustomer = "/api/v1/clients/info-client/" + id;
        return httpClientService.get(uri + apiCustomer, ClientDto.class);
    }
}
