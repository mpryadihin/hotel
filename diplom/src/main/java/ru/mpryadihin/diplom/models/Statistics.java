package ru.mpryadihin.diplom.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Statistics {

    @NotNull(message = "Вы должны указать год")
    @Min(value = 2020, message = "Год не может быть меньше 2020")
    @Pattern(regexp = "yyyy")
    private int year;
}
