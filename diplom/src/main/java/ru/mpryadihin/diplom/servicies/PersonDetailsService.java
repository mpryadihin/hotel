package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.repositories.PersonRepository;
import ru.mpryadihin.diplom.security.PersonDetails;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;
    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetails(person.get());
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundedPerson = personRepository.findById(id);
        return foundedPerson.orElse(null);
    }

    @Transactional(readOnly = false)
    public void save(Person person){
        personRepository.save(person);
    }
    @Transactional(readOnly = false)
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }
    @Transactional(readOnly = false)
    public void delete(int id){
        personRepository.deleteById(id);
    }
}
