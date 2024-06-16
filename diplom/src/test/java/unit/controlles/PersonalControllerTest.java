package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import ru.mpryadihin.diplom.controllers.PersonalController;
import ru.mpryadihin.diplom.models.Job;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.servicies.JobService;
import ru.mpryadihin.diplom.servicies.PersonalService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonalControllerTest {

    @Mock
    PersonalService personalService;

    @Mock
    JobService jobService;

    @InjectMocks
    PersonalController personalController;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Mock
    MultipartFile multipartFile;

    @Test
    void index() {
        List<Personal> personalList = new ArrayList<>();
        when(personalService.findAll()).thenReturn(personalList);

        personalController.index(model);

        verify(model).addAttribute("personal", personalList);
    }

    @Test
    void newPersonal() {
        personalController.newPersonal(new Personal(), model);

        verify(model).addAttribute("job", jobService.findAll());
    }

    @Test
    void show() {
        int id = 1;
        Personal personal = new Personal();
        personal.setId(id);
        Job job = new Job();
        personal.setJob(job);
        job.setId(id);
        job.setResp("sda");
        when(personalService.findOne(id)).thenReturn(personal);
        when(jobService.findOne(id)).thenReturn(job);

        personalController.show(id, model);

        verify(model).addAttribute("personal", personal);
        verify(jobService).findOne(id);
    }

    @Test
    void edit() {
        int id = 1;
        Personal personal = new Personal();
        when(personalService.findOne(id)).thenReturn(personal);

        personalController.edit(id, model);

        verify(model).addAttribute("personal", personal);
        verify(model).addAttribute("job", jobService.findAll());
    }

    @Test
    void create() throws IOException {
        Personal personal = new Personal();
        when(bindingResult.hasErrors()).thenReturn(false);

        personalController.create(multipartFile, personal, bindingResult, model);

        verify(personalService).save(personal, multipartFile);
    }

    @Test
    void createWithError() throws IOException {
        Personal personal = new Personal();
        when(bindingResult.hasErrors()).thenReturn(true);

        personalController.create(multipartFile, personal, bindingResult, model);

        verify(model).addAttribute("job", jobService.findAll());
    }

    @Test
    void update() throws IOException {
        int id = 1;
        Personal personal = new Personal();
        when(bindingResult.hasErrors()).thenReturn(false);

        personalController.update(id, multipartFile, personal, bindingResult, model);

        verify(personalService).update(id, personal, multipartFile);
    }

    @Test
    void updateWithError() throws IOException {
        int id = 1;
        Personal personal = new Personal();
        when(bindingResult.hasErrors()).thenReturn(true);

        personalController.update(id, multipartFile, personal, bindingResult, model);

        verify(model).addAttribute("job", jobService.findAll());
    }

    @Test
    void delete() {
        int id = 1;

        personalController.delete(id);

        verify(personalService).delete(id);
    }
}
