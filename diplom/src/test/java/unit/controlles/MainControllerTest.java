package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.ui.Model;
import ru.mpryadihin.diplom.controllers.MainController;
import ru.mpryadihin.diplom.models.*;
import ru.mpryadihin.diplom.security.PersonDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import ru.mpryadihin.diplom.servicies.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    private ClientService clientService;
    @Mock
    private JobService jobService;
    @Mock
    private PersonalService personalService;
    @Mock
    private ResidesService residesService;
    @Mock
    private RoomService roomService;
    @Mock
    private StatisticsService statisticsService;
    @Mock
    private TypeRoomService typeRoomService;

    @InjectMocks
    private MainController mainController;


    @Test
    void testIndex() {
        Person user = new Person();
        user.setUsername("testUser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(new PersonDetails(user));

        SecurityContextHolder.setContext(new SecurityContextImpl());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<Resides> residesList = new ArrayList<>();
        List<Client> clientList = new ArrayList<>();
        List<Job> jobList = new ArrayList<>();
        List<Personal> personalList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        List<TypeRoom> typeRoomList = new ArrayList<>();
        List<Integer> yearsList = new ArrayList<>();

        when(residesService.findAll()).thenReturn(residesList);
        when(clientService.findAll()).thenReturn(clientList);
        when(jobService.findAll()).thenReturn(jobList);
        when(personalService.findAll()).thenReturn(personalList);
        when(roomService.findAll()).thenReturn(roomList);
        when(typeRoomService.findAll()).thenReturn(typeRoomList);

        Model model = mock(Model.class);

        String viewName = mainController.index(model);

        verify(model).addAttribute("resides", residesList);
        verify(model).addAttribute("clients", clientList);
        verify(model).addAttribute("jobs", jobList);
        verify(model).addAttribute("persons", personalList);
        verify(model).addAttribute("rooms", roomList);
        verify(model).addAttribute("statistics", yearsList);
        verify(model).addAttribute("types", typeRoomList);
        verify(model).addAttribute("user", user);

        assertEquals("main/start", viewName);
    }
}
