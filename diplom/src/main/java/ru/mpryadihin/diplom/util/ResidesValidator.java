package ru.mpryadihin.diplom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.servicies.ResidesService;

import java.time.LocalDate;

@Component
public class ResidesValidator implements Validator {
    private final ResidesService residesService;
    @Autowired
    public ResidesValidator(ResidesService residesService) {
        this.residesService = residesService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Resides.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Resides resides = (Resides) target;
        LocalDate start = resides.getDateIn();
        LocalDate end = resides.getDateOut();
        if (start == null){
            errors.rejectValue("dateIn", "", "Вы должны указать дату заезда");
        } if(end == null){
            errors.rejectValue("dateOut", "", "Вы должны указать дату выезда");
        } else
        if (residesService.dateValidation(resides) == 0){
            errors.rejectValue("dateOut", "", "Данные даты забронированы");
        } else
        if (start.isAfter(end)){
            errors.rejectValue("dateIn", "", "Дата заезда не может быть позже даты выезда");
        } else
        if (end.isBefore(start)){
            errors.rejectValue("dateOut", "", "Дата выезда не может быть раньше даты заезда");
        } else
        if (end.equals(start)){
            errors.rejectValue("dateOut", "", "Даты заезда и выезда не должны совпадать");
        }



    }
}
