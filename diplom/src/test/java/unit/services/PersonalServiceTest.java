package unit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.repositories.PersonalRepository;
import ru.mpryadihin.diplom.servicies.PersonalService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonalServiceTest {

    @Mock
    private PersonalRepository personalRepository;

    @InjectMocks
    private PersonalService personalService;

    @Test
    public void testFindAll() {

        List<Personal> expectedPersonalList = Arrays.asList(new Personal(), new Personal());
        when(personalRepository.findAll()).thenReturn(expectedPersonalList);

        List<Personal> actualPersonalList = personalService.findAll();

        assertEquals(expectedPersonalList.size(), actualPersonalList.size());
        verify(personalRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        int id = 1;
        Personal expectedPersonal = new Personal();
        when(personalRepository.findById(id)).thenReturn(Optional.of(expectedPersonal));

        Personal actualPersonal = personalService.findOne(id);

        assertNotNull(actualPersonal);
        verify(personalRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() throws IOException {

        Personal personal = new Personal();
        MultipartFile file = mock(MultipartFile.class);

        personalService.save(personal, file);

        verify(file, times(1)).getBytes();
        verify(personalRepository, times(1)).save(personal);
    }

    @Test
    public void testUpdate() throws IOException {

        int id = 1;
        Personal updatedPersonal = new Personal();
        MultipartFile file = mock(MultipartFile.class);

        personalService.update(id, updatedPersonal, file);

        verify(personalRepository, times(1)).save(updatedPersonal);
    }

    @Test
    public void testDelete() {

        int id = 1;

        personalService.delete(id);

        verify(personalRepository, times(1)).deleteById(id);
    }
}
