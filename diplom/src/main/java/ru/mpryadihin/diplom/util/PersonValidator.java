package ru.mpryadihin.diplom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mpryadihin.diplom.models.Person;
import ru.mpryadihin.diplom.servicies.PersonDetailsService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService service;

    @Autowired
    public PersonValidator(PersonDetailsService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (!person.getUsername().isEmpty()){
            Optional<UserDetails> user = Optional.ofNullable(service.loadUserByUsername(person.getUsername()));
            if (user.isPresent()) {
                errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
            }
        }
    }
}
