package unit.controlles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.mpryadihin.diplom.controllers.AdminController;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.servicies.PersonDetailsService;
import ru.mpryadihin.diplom.util.PersonValidator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private PersonValidator personValidator;

    @Mock
    private PersonDetailsService personService;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person();
        testPerson.setId(1);
        testPerson.setUsername("John");
        testPerson.setPassword("john@example.com");
        testPerson.setRole("dsfds");
    }

    @Test
    void index_shouldReturnAdminOptionsPage() {
        String viewName = adminController.index(model);
        verify(model).addAttribute(eq("users"), any());
        assert(viewName).equals("admin/options");
    }

    @Test
    void findOne_shouldReturnAdminShowUserPage() {
        int id = 1;
        when(personService.findOne(id)).thenReturn(testPerson);
        String viewName = adminController.findOne(id, model);
        verify(model).addAttribute(eq("user"), any());
        assert(viewName).equals("admin/show_user");
    }

    @Test
    void edit_shouldReturnAdminEditUserPage() {
        int id = 1;
        when(personService.findOne(id)).thenReturn(testPerson);
        String viewName = adminController.edit(id, model);
        verify(model).addAttribute(eq("user"), any());
        assert(viewName).equals("admin/edit_user");
    }

    @Test
    void update_withValidPerson_shouldRedirectToAdminPage() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = adminController.update(id, testPerson, bindingResult);
        verify(personValidator).validate(testPerson, bindingResult);
        verify(personService).update(id, testPerson);
        assert(viewName).equals("redirect:/admin");
    }

    @Test
    void update_withInvalidPerson_shouldReturnAdminEditUserPage() {
        int id = 1;
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = adminController.update(id, testPerson, bindingResult);
        verify(personValidator).validate(testPerson, bindingResult);
        assert(viewName).equals("admin/edit_user");
    }

    @Test
    void delete_shouldRedirectToAdminPage() {
        int id = 1;
        String viewName = adminController.delete(id);
        verify(personService).delete(id);
        assert(viewName).equals("redirect:/admin");
    }
}
