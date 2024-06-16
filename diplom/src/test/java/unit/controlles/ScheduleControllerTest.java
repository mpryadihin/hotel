package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mpryadihin.diplom.DTO.ScheduleDTO;
import ru.mpryadihin.diplom.controllers.ScheduleController;
import ru.mpryadihin.diplom.servicies.PersonalService;
import ru.mpryadihin.diplom.servicies.ScheduleService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Mock
    private PersonalService personalService;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ScheduleController scheduleController;

    @Test
    void testIndex() {
        int year = 2024;
        int month = 5;

        String viewName = scheduleController.index(model, year, month);

        verify(model).addAttribute(eq("monthValue"), any());
        verify(model).addAttribute(eq("scheduleList"), any());
        verify(model).addAttribute(eq("personal"), any());
        verify(model).addAttribute(eq("days"), any());
        assert(viewName).equals("schedule/index");
    }

    @Test
    void testEdit() {
        int year = 2024;
        int month = 5;

        String viewName = scheduleController.edit(model, year, month);

        verify(model).addAttribute(eq("monthValue"), any());
        verify(model).addAttribute(eq("scheduleListDTO"), any());
        verify(model).addAttribute(eq("personal"), any());
        verify(model).addAttribute(eq("days"), any());
        assert(viewName).equals("schedule/edit");
    }

    @Test
    void testUpdateSchedule() {

        int year = 2024;
        int month = 5;

        String redirectURL = scheduleController.updateSchedule(new ScheduleDTO(), year, month, redirectAttributes);

        verify(scheduleService).updateAll(any());
        verify(redirectAttributes).addAttribute(eq("year"), eq(year));
        verify(redirectAttributes).addAttribute(eq("month"), eq(month));
        assert(redirectURL).equals("redirect:/schedule");
    }
}
