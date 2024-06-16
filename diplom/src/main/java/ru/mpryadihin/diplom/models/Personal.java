package ru.mpryadihin.diplom.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "personal")
public class Personal implements Serializable {

    public Personal(int id, String name, String surname, String patronymic, Date birthday, int idJob, int salary, byte[] image, Job job, List<Room> rooms, List<Schedule> schedules) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.idJob = idJob;
        this.salary = salary;
        this.image = image;
        this.job = job;
        this.rooms = rooms;
        this.schedules = schedules;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Вы должны указать имя")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Вы должны указать фамилию")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date birthday;

    @Column(name = "id_job")
    @NotNull(message = "Вы должны выбрать профессию")
    int idJob;

    @Column(name = "salary")
    @NotNull(message = "Вы должны указать ЗП")
    int salary;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @MapsId("id_job")
    @ManyToOne
    @JoinColumn(name = "id_job" , referencedColumnName = "id")
    private Job job;


    @OneToMany(mappedBy = "personal")
    private List<Room> rooms;

   @OneToMany(mappedBy = "personal")
   private List<Schedule> schedules;

    public Personal() {

    }

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

    public int getIdJob() {
        return idJob;
    }

    public void setIdJob(int idJob) {
        this.idJob = idJob;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
