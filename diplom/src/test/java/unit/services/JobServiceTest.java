package unit.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.mpryadihin.diplom.models.Job;
import ru.mpryadihin.diplom.repositories.JobRepository;
import ru.mpryadihin.diplom.servicies.JobService;


@SpringBootTest
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindAll() {

        List<Job> jobs = Arrays.asList(new Job(), new Job());
        when(jobRepository.findAll()).thenReturn(jobs);

        List<Job> result = jobService.findAll();

        assertEquals(jobs, result);
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {

        int id = 1;
        Job job = new Job();
        when(jobRepository.findById(id)).thenReturn(Optional.of(job));

        Job result = jobService.findOne(id);

        assertEquals(job, result);
        verify(jobRepository, times(1)).findById(id);
    }

    @Test
    public void testFindOne_NotFound() {

        int id = 1;
        when(jobRepository.findById(id)).thenReturn(Optional.empty());

        Job result = jobService.findOne(id);

        assertNull(result);
        verify(jobRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {

        Job job = new Job();

        jobService.save(job);

        verify(jobRepository, times(1)).save(job);
    }

    @Test
    public void testUpdate() {

        int id = 1;
        Job updatedJob = new Job();

        jobService.update(id, updatedJob);

        verify(jobRepository, times(1)).save(updatedJob);
    }

    @Test
    public void testDelete() {

        int id = 1;

        jobService.delete(id);

        verify(jobRepository, times(1)).deleteById(id);
    }

}