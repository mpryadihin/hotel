package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.JobController;
import ru.mpryadihin.diplom.models.Job;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.servicies.JobService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

    @Mock
    private JobService jobService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private JobController jobController;

    @Test
    public void testIndex() {
        List<Job> jobs = new ArrayList<>();
        when(jobService.findAll()).thenReturn(jobs);

        String viewName = jobController.index(model);

        verify(model).addAttribute("job", jobs);
        assert(viewName).equals("job/index");
    }

    @Test
    public void testShow() {
        int id = 1;
        Job job = new Job();
        job.setResp("Sample response");
        when(jobService.findOne(id)).thenReturn(job);

        String viewName = jobController.show(id, model);

        verify(model).addAttribute("job", job);
        verify(model).addAttribute("sentences", job.getResp().split("(?<=[.!?])\\s*"));
        assert(viewName).equals("job/show");
    }

    @Test
    public void testEdit() {
        int id = 1;
        Job job = new Job();
        when(jobService.findOne(id)).thenReturn(job);

        String viewName = jobController.edit(id, model);

        verify(model).addAttribute("job", job);
        assert(viewName).equals("job/edit");
    }

    @Test
    public void testNewJob() {
        String viewName = jobController.newJob(new Job());

        assert(viewName).equals("job/new");
    }

    @Test
    public void testCreate() {
        Job job = new Job();

        String viewName = jobController.create(job, bindingResult);

        assertEquals("redirect:/job", viewName);
        verify(bindingResult).hasErrors();
        verify(jobService).save(job);
    }

    @Test
    public void testUpdate() {
        int id = 1;
        Job job = new Job();

        String viewName = jobController.update(id, job, bindingResult);

        assertEquals("redirect:/job", viewName);
        verify(bindingResult).hasErrors();
        verify(jobService).update(id, job);
    }

    @Test
    public void testDelete() {
        int id = 1;

        String viewName = jobController.delete(id);

        assert(viewName).equals("redirect:/job");
        verify(jobService).delete(id);
    }
}
