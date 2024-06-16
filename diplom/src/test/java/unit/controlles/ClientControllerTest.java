package unit.controlles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.ClientController;
import ru.mpryadihin.diplom.models.Client;
import ru.mpryadihin.diplom.servicies.ClientService;
import ru.mpryadihin.diplom.util.ClientValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ClientValidator clientValidator;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIndex() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        when(clientService.findAll()).thenReturn(clients);

        String viewName = clientController.index(model, "");

        assertEquals("client/index", viewName);
        verify(model).addAttribute("clients", clients);
    }

    @Test
    public void testShow() {
        Client client = new Client();
        when(clientService.findOne(1)).thenReturn(client);

        String viewName = clientController.show(1, model);

        assertEquals("client/show", viewName);
        verify(model).addAttribute("client", client);
    }

    @Test
    public void testSearch() {
        List<Client> searchResult = new ArrayList<>();
        searchResult.add(new Client());
        when(clientService.search("Smith")).thenReturn(searchResult);

        String viewName = clientController.search("Smith", model);

        assertEquals("client/index", viewName);
        verify(model).addAttribute("search", searchResult);
    }

    @Test
    public void testNewClient() {
        String viewName = clientController.newClient(new Client());

        assertEquals("client/new", viewName);
    }

    @Test
    public void testCreate() {
        Client client = new Client();

        String viewName = clientController.create(client, bindingResult);

        assertEquals("redirect:/client", viewName);
        verify(clientValidator).validate(client, bindingResult);
        verify(clientService).save(client);
    }

    @Test
    public void testEdit() {
        Client client = new Client();
        when(clientService.findOne(1)).thenReturn(client);

        String viewName = clientController.edit(1, model);

        assertEquals("client/edit", viewName);
        verify(model).addAttribute("client", client);
    }

    @Test
    public void testUpdate() {
        Client client = new Client();

        String viewName = clientController.update(1, client, bindingResult);

        assertEquals("redirect:/client", viewName);
        verify(clientValidator).validate(client, bindingResult);
        verify(clientService).update(1, client);
    }

    @Test
    public void testDelete() {
        String viewName = clientController.delete(1);

        assertEquals("redirect:/client", viewName);
        verify(clientService).delete(1);
    }
}
