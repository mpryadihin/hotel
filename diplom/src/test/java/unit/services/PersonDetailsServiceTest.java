package unit.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.repositories.PersonRepository;
import ru.mpryadihin.diplom.security.PersonDetails;
import ru.mpryadihin.diplom.servicies.PersonDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PersonDetailsServiceTest {

    private PersonDetailsService personDetailsService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personDetailsService = new PersonDetailsService(personRepository);
    }

    @Test
    void loadUserByUsername_UserFound_ReturnsPersonDetails() {

        String username = "testUser";
        Person person = new Person();
        person.setUsername(username);
        Mockito.when(personRepository.findByUsername(username)).thenReturn(Optional.of(person));

        PersonDetails personDetails = (PersonDetails) personDetailsService.loadUserByUsername(username);

        Assertions.assertEquals(username, personDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {

        String username = "nonExistentUser";
        Mockito.when(personRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            personDetailsService.loadUserByUsername(username);
        });
    }

    @Test
    void findAll_ReturnsListOfPersons() {

        List<Person> personList = new ArrayList<>();
        personList.add(new Person());
        personList.add(new Person());
        Mockito.when(personRepository.findAll()).thenReturn(personList);

        List<Person> result = personDetailsService.findAll();

        Assertions.assertEquals(personList.size(), result.size());
    }

    @Test
    void findOne_PersonFound_ReturnsPerson() {
        int id = 1;
        Person person = new Person();
        person.setId(id);
        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person result = personDetailsService.findOne(id);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    void findOne_PersonNotFound_ReturnsNull() {

        int id = 999;
        Mockito.when(personRepository.findById(id)).thenReturn(Optional.empty());

        Person result = personDetailsService.findOne(id);

        Assertions.assertNull(result);
    }

    @Test
    void save_PersonSavedSuccessfully() {

        Person person = new Person();

        personDetailsService.save(person);

        Mockito.verify(personRepository, Mockito.times(1)).save(person);
    }

    @Test
    void update_PersonUpdatedSuccessfully() {

        int id = 1;
        Person updatedPerson = new Person();

        personDetailsService.update(id, updatedPerson);

        Mockito.verify(personRepository, Mockito.times(1)).save(updatedPerson);
    }

    @Test
    void delete_PersonDeletedSuccessfully() {

        int id = 1;

        personDetailsService.delete(id);

        Mockito.verify(personRepository, Mockito.times(1)).deleteById(id);
    }
}
