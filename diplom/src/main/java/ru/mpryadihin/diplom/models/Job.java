package ru.mpryadihin.diplom.models;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Getter
@Entity
@Table(name = "job")
public class Job implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public Job() {
    }

    public Job(int id, String name, List<Personal> personals) {
        this.id = id;
        this.name = name;
        this.personals = personals;
    }

    @Column(name = "responsibilities")
    @NotEmpty(message = "Вы должны указать обязанности")
    private String resp;

    public void setResp(String resp) {
        this.resp = resp;
    }

    @Column(name = "name")
    @NotEmpty(message = "Вы должны указать профессию")
    private String name;

    public void setPersonals(List<Personal> personals) {
        this.personals = personals;
    }

    @OneToMany(mappedBy = "job")
    private List<Personal> personals;


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
