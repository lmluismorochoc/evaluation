package com.nttdata.clientService;

import com.nttdata.clientService.application.dto.ClientDTO;
import com.nttdata.clientService.application.dto.ResponseDTO;
import com.nttdata.clientService.application.interfaces.IClientService;
import com.nttdata.clientService.domain.ClientEntity;
import com.nttdata.clientService.infrastructure.repository.ClientPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IClientServiceApplicationIntegrationTests {

    @Autowired
    private IClientService clientService;
    @Autowired
    private ClientPersonRepository clientPersonRepository;

    @Test
    void testCreateClientIntegration() {
        ClientDTO clientDTO =   new ClientDTO(
                "John Doe",
                "M",
                30,
                "231231232",
                "123 Main St, City",
                "0967909190",
                "password123",
                true
        );

        // Probar la creación del cliente
        Mono<ResponseDTO> result = clientService.create(clientDTO);
        ResponseDTO createdClient = result.block();

        // Validar que el cliente se creó correctamente
        assertNotNull(createdClient);
        assertEquals(createdClient.getStatus(), "OK");
    }

    @Test
    void testCreateClientWithDuplicateCedulaIntegration() {
        ClientDTO clientDTO =   new ClientDTO(
                "John Doe",
                "M",
                30,
                "4727272",
                "123 Main St, City",
                "0967909190",
                "password123",
                true
        );


        // Intentar crear otro cliente con la misma cédula
        Mono<ResponseDTO> result = clientService.create(clientDTO);

        // Verificar que lanza una excepción
        assertThrows(RuntimeException.class, result::block);
    }
}
