package com.nttdata.clientService;

import com.nttdata.clientService.application.dto.ClientDTO;
import com.nttdata.clientService.application.dto.ResponseDTO;
import com.nttdata.clientService.application.interfaces.IClientService;
import com.nttdata.clientService.application.services.ClientService;
import com.nttdata.clientService.domain.ClientEntity;
import com.nttdata.clientService.infrastructure.repository.ClientPersonRepository;
import com.nttdata.clientService.infrastructure.repository.ClientRepository;
import com.nttdata.clientService.infrastructure.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = IClientService.class)
class IClientServiceApplicationTests {

	@Mock
	private ClientPersonRepository clientPersonRepository;
	@Mock
	private ClientRepository clientRepository;
	@Mock
	private PersonRepository personRepository;
	@InjectMocks
	private ClientService clientService;

	private ClientDTO clientDTO;

	@BeforeEach
	void setUp() {
		clientDTO =  new ClientDTO(
				"John Doe",
				"M",
				30,
				"999999999",
				"123 Main St, City",
				"0967909190",
				"password123",
				true
		);
	}

	@Test
	void testCreateClient() {
		// Simular que el repositorio devuelve un Mono vacío al buscar la cédula
		when(clientPersonRepository.findClientByIdentification(clientDTO.getIdentification())).thenReturn(null);

		// Ejecutar el método de creación
		Mono<ResponseDTO> result = clientService.create(clientDTO);
		// Validar que el cliente fue creado correctamente
		assertNotNull(result);
		ResponseDTO createdClient = result.block();
		assertEquals("OK", createdClient.getStatus());


	}
	@Test
	void testUpdateClient() {
		ClientEntity updatedClientDTO = new ClientEntity();


		// Ejecutar la actualización
		Mono<ResponseDTO> result = clientService.updateById(clientDTO,1L);

		// Validar que el cliente fue actualizado correctamente
		assertNotNull(result);
		ResponseDTO createdClient = result.block();
		assertEquals("OK", createdClient.getStatus());


	}


}
