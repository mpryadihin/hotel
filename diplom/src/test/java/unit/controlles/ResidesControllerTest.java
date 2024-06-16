package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.ResidesController;
import ru.mpryadihin.diplom.models.Client;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.models.Room;
import ru.mpryadihin.diplom.servicies.ClientService;
import ru.mpryadihin.diplom.servicies.ResidesService;
import ru.mpryadihin.diplom.servicies.RoomService;
import ru.mpryadihin.diplom.util.ResidesValidator;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResidesControllerTest {

    @Mock
    private ResidesService residesService;

    @Mock
    private ClientService clientService;

    @Mock
    private RoomService roomService;

    @Mock
    private ResidesValidator residesValidator;

    @InjectMocks
    private ResidesController residesController;

    @Test
    public void testIndex() {
        List<Resides> residesList = new ArrayList<>();
        when(residesService.findAll()).thenReturn(residesList);
        Model model = mock(Model.class);

        String viewName = residesController.index(model);

        assertEquals("resides/index", viewName);
        verify(model).addAttribute("resides", residesList);
    }

    @Test
    public void testShow() {
        int residesId = 1;
        Resides resides = new Resides();
        when(residesService.findOne(residesId)).thenReturn(resides);
        Model model = mock(Model.class);

        String viewName = residesController.show(residesId, model);

        assertEquals("resides/show", viewName);
        verify(model).addAttribute("resides", resides);
    }

    @Test
    public void testNewResides() {
        Model model = mock(Model.class);
        List<Client> clientList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        when(clientService.findAll()).thenReturn(clientList);
        when(roomService.findAll()).thenReturn(roomList);

        String viewName = residesController.newResides(new Resides(), model);

        assertEquals("resides/new", viewName);
        verify(model).addAttribute("clients", clientList);
        verify(model).addAttribute("rooms", roomList);
    }

    @Test
    public void testEdit() {
        int residesId = 1;
        Model model = mock(Model.class);
        List<Client> clientList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        when(clientService.findAll()).thenReturn(clientList);
        when(roomService.findAll()).thenReturn(roomList);

        String viewName = residesController.edit(residesId, new Resides(), model);

        assertEquals("resides/edit", viewName);
        verify(model).addAttribute("clients", clientList);
        verify(model).addAttribute("rooms", roomList);
    }

    @Test
    public void testCreateWithErrors() {
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        List<Client> clientList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        when(clientService.findAll()).thenReturn(clientList);
        when(roomService.findAll()).thenReturn(roomList);

        String viewName = residesController.create(new Resides(), bindingResult, model);

        assertEquals("resides/new", viewName);
        verify(model).addAttribute("clients", clientList);
        verify(model).addAttribute("rooms", roomList);
    }

    @Test
    public void testCreateWithoutErrors() {
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        Resides resides = new Resides();

        String viewName = residesController.create(resides, bindingResult, model);

        assertEquals("redirect:/resides", viewName);
        verify(residesService).save(resides);
    }

    @Test
    public void testUpdateWithErrors() {
        int residesId = 1;
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        List<Client> clientList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        when(clientService.findAll()).thenReturn(clientList);
        when(roomService.findAll()).thenReturn(roomList);

        String viewName = residesController.update(residesId, new Resides(), bindingResult, model);

        assertEquals("resides/edit", viewName);
        verify(model).addAttribute("clients", clientList);
        verify(model).addAttribute("rooms", roomList);
    }

    @Test
    public void testUpdateWithoutErrors() {
        int residesId = 1;
        Model model = mock(Model.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        Resides resides = new Resides();

        String viewName = residesController.update(residesId, resides, bindingResult, model);

        assertEquals("redirect:/resides", viewName);
        verify(residesService).update(residesId, resides);
    }

    @Test
    public void testDelete() {
        int residesId = 1;

        String viewName = residesController.delete(residesId);

        assertEquals("redirect:/resides", viewName);
        verify(residesService).delete(residesId);
    }
}
