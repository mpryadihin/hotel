package ru.mpryadihin.diplom.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Вы должны указать имя")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Вы должны указать фамилию")
    @Size(min = 2, max = 30,message = "Фамилия должна содержать от 2 до 30 символов")
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;

    @NotEmpty(message = "Вы должны указать серию и номер паспорта")
    @Column(name = "passport")
    private String passport;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthday;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = Objects.requireNonNullElse(info, " ");
    }

    @Column(name = "info")
    private String info;




    public Client() {
    }

    public List<Resides> getResides() {
        return resides;
    }

    public void setResides(List<Resides> resides) {
        this.resides = resides;
    }

    @OneToMany(mappedBy = "client")
    private List<Resides> resides;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassport() {
        return passport;
    }
    public void setPassport(String passport) {
        this.passport = passport;
    }
}
