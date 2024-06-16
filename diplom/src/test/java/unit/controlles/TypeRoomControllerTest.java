package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.TypeRoomController;
import ru.mpryadihin.diplom.models.TypeRoom;
import ru.mpryadihin.diplom.servicies.TypeRoomService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeRoomControllerTest {

    @Mock
    private TypeRoomService typeRoomService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private TypeRoomController controller;

    @Test
    void index_shouldReturnCorrectViewNameAndModelAttribute() {
        List<TypeRoom> typeRooms = Arrays.asList(new TypeRoom(), new TypeRoom());
        when(typeRoomService.findAll()).thenReturn(typeRooms);

        String viewName = controller.index(model);

        assertEquals("typeRoom/index", viewName);
        verify(model).addAttribute("type", typeRooms);
    }

    @Test
    void show_shouldReturnCorrectViewNameAndModelAttribute() {
        int id = 1;
        TypeRoom typeRoom = new TypeRoom();
        when(typeRoomService.findOne(id)).thenReturn(typeRoom);

        String viewName = controller.show(id, model);

        assertEquals("typeRoom/show", viewName);
        verify(model).addAttribute("type", typeRoom);
    }

    @Test
    void newType_shouldReturnCorrectViewName() {
        String viewName = controller.newType(new TypeRoom());

        assertEquals("typeRoom/new", viewName);
    }

    @Test
    void edit_shouldReturnCorrectViewNameAndModelAttribute() {
        int id = 1;
        TypeRoom typeRoom = new TypeRoom();
        when(typeRoomService.findOne(id)).thenReturn(typeRoom);

        String viewName = controller.edit(id, model);

        assertEquals("typeRoom/edit", viewName);
        verify(model).addAttribute("type", typeRoom);
    }

    @Test
    void create_shouldRedirectCorrectlyWhenNoErrors() {
        TypeRoom typeRoom = new TypeRoom();

        String viewName = controller.create(typeRoom, bindingResult);

        assertEquals("redirect:/typeRoom", viewName);
        verify(typeRoomService).save(typeRoom);
    }

    @Test
    void create_shouldReturnCorrectViewNameWhenErrorsExist() {

        TypeRoom typeRoom = new TypeRoom();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = controller.create(typeRoom, bindingResult);

        assertEquals("typeRoom/new", viewName);
        verify(typeRoomService, never()).save(any(TypeRoom.class));
    }

    @Test
    void update_shouldRedirectCorrectlyWhenNoErrors() {
        int id = 1;
        TypeRoom typeRoom = new TypeRoom();

        String viewName = controller.update(id, typeRoom, bindingResult);

        assertEquals("redirect:/typeRoom", viewName);
        verify(typeRoomService).update(id, typeRoom);
    }

    @Test
    void update_shouldReturnCorrectViewNameWhenErrorsExist() {
        int id = 1;
        TypeRoom typeRoom = new TypeRoom();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = controller.update(id, typeRoom, bindingResult);

        assertEquals("typeRoom/edit", viewName);
        verify(typeRoomService, never()).update(anyInt(), any(TypeRoom.class));
    }

    @Test
    void delete_shouldRedirectCorrectly() {
        int id = 1;

        String viewName = controller.delete(id);

        assertEquals("redirect:/typeRoom", viewName);
        verify(typeRoomService).delete(id);
    }
}
