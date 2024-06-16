package unit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.repositories.PersonRepository;
import ru.mpryadihin.diplom.servicies.RegistrationService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testRegister() {
        Person person = new Person();
        person.setUsername("testUser");
        person.setPassword("testPassword");

        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        registrationService.register(person);

        verify(passwordEncoder, times(1)).encode("testPassword");
        verify(personRepository, times(1)).save(person);
    }
}
