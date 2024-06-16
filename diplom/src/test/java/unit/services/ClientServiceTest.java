package unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mpryadihin.diplom.models.Client;
import ru.mpryadihin.diplom.repositories.ClientRepository;
import ru.mpryadihin.diplom.servicies.ClientService;
import ru.mpryadihin.diplom.servicies.SecretKeyService;
import ru.mpryadihin.diplom.util.CryptoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CryptoUtils cryptoUtils;

    @Mock
    private SecretKeyService secretKeyService;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertEquals(clients, result);
    }

    @Test
    void testFindOne() {
        int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setPassport("6616 849080");
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(cryptoUtils.decrypt(anyString(), any())).thenReturn("decryptedPassport");

        Client result = clientService.findOne(clientId);

        assertEquals(client, result);
        assertEquals("decryptedPassport", result.getPassport());
    }

    @Test
    void testSave() {
        Client client = new Client();
        client.setId(1);
        client.setPassport("6616 849080");
        clientService.save(client);

        verify(cryptoUtils, times(1)).encrypt(anyString(), any());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testUpdate() {
        int clientId = 1;
        Client updatedClient = new Client();
        updatedClient.setPassport("6616 849080");
        updatedClient.setId(clientId);

        clientService.update(clientId, updatedClient);

        verify(cryptoUtils, times(1)).encrypt(anyString(), any());
        verify(clientRepository, times(1)).save(updatedClient);
    }

    @Test
    void testDelete() {
        int clientId = 1;

        clientService.delete(clientId);

        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void testSearch() {
        String searchString = "search";
        List<Client> searchResult = new ArrayList<>();
        searchResult.add(new Client());
        when(clientRepository.searchClientBySurname(searchString)).thenReturn(searchResult);

        List<Client> result = clientService.search(searchString);

        assertEquals(searchResult, result);
    }
}
