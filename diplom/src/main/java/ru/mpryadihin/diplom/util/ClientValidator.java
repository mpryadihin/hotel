package ru.mpryadihin.diplom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mpryadihin.diplom.models.Client;
import ru.mpryadihin.diplom.servicies.SecretKeyService;

@Component
public class ClientValidator implements Validator {


    private final CryptoUtils cryptoUtils;
    private final SecretKeyService service;
    @Autowired
    public ClientValidator(CryptoUtils cryptoUtils, SecretKeyService service) {
        this.cryptoUtils = cryptoUtils;
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;
        if (!client.getPassport().matches("(\\d{4}\\s\\d{6})")){
            errors.rejectValue("passport", "", "**** ******");
        }
    }
}
