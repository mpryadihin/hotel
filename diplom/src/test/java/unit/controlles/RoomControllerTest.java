package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.RoomController;
import ru.mpryadihin.diplom.models.Room;
import ru.mpryadihin.diplom.servicies.PersonalService;
import ru.mpryadihin.diplom.servicies.RoomService;
import ru.mpryadihin.diplom.servicies.TypeRoomService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    @Mock
    RoomService roomService;

    @Mock
    TypeRoomService typeRoomService;

    @Mock
    PersonalService personalService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    RoomController roomController;

    @Test
    public void testIndex() {
        List<Room> rooms = new ArrayList<>();
        when(roomService.findAll()).thenReturn(rooms);

        String viewName = roomController.index(model);

        assertEquals("room/index", viewName);
        assert(viewName.equals("room/index"));
    }

    @Test
    public void testShowFreeRooms() {
        List<Room> freeRooms = new ArrayList<>();
        when(roomService.findFreeRooms()).thenReturn(freeRooms);

        String viewName = roomController.showFreeRooms(model);

        assertEquals("room/index", viewName);
        verify(model, times(1)).addAttribute("rooms", freeRooms);
    }

    @Test
    public void testShow() {
        Room room = new Room();
        when(roomService.findOne(1)).thenReturn(room);

        String viewName = roomController.show(1, model);

        assertEquals("room/show", viewName);
        verify(model, times(1)).addAttribute("room", room);
    }

    @Test
    public void testNewRoom() {
        String viewName = roomController.newRoom(new Room(), model);

        assertEquals("room/new", viewName);
        verify(model, times(1)).addAttribute("types", typeRoomService.findAll());
        verify(model, times(1)).addAttribute("personal", personalService.findAll());
    }

    @Test
    public void testEdit() {
        Room room = new Room();
        when(roomService.findOne(1)).thenReturn(room);

        String viewName = roomController.edit(1, model);

        assertEquals("room/edit", viewName);
        verify(model, times(1)).addAttribute("room", room);
        verify(model, times(1)).addAttribute("types", typeRoomService.findAll());
        verify(model, times(1)).addAttribute("personal", personalService.findAll());
    }

    @Test
    public void testCreate() {
        Room room = new Room();

        String viewName = roomController.create(room, bindingResult, model);

        assertEquals("redirect:/room", viewName);
        verify(roomService, times(1)).save(room);
    }

    @Test
    public void testUpdate() {
        Room room = new Room();

        String viewName = roomController.update(1, room, bindingResult, model);

        assertEquals("redirect:/room", viewName);
        verify(roomService, times(1)).update(1, room);
    }

    @Test
    public void testDelete() {
        String viewName = roomController.delete(1);

        assertEquals("redirect:/room", viewName);
        verify(roomService, times(1)).delete(1);
    }
}
