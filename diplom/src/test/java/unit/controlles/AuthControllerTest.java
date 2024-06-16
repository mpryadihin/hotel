package unit.controlles;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.AuthController;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.servicies.RegistrationService;
import ru.mpryadihin.diplom.util.PersonValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private PersonValidator personValidator;

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private AuthController authController;

    @Test
    void loginPage_ReturnsCorrectViewName() {
        String viewName = authController.loginPage();
        assertEquals("auth/login", viewName);
    }

    @Test
    void registrationPage_ReturnsCorrectViewName() {
        String viewName = authController.registrationPage(new Person());
        assertEquals("auth/registration", viewName);
    }

    @Test
    void performRegistration_WithValidPerson_RegistersAndRedirects() {
        Person person = new Person();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = authController.performRegistration(person, bindingResult);

        verify(personValidator).validate(person, bindingResult);
        verify(registrationService).register(person);
        assertEquals("redirect:/auth/login", result);
    }

    @Test
    void performRegistration_WithInvalidPerson_ReturnsRegistrationView() {
        Person person = new Person();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = authController.performRegistration(person, bindingResult);

        verify(personValidator).validate(person, bindingResult);
        verifyNoInteractions(registrationService);
        assertEquals("auth/registration", result);
    }
}
