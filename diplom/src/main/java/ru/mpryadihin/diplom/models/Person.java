package ru.mpryadihin.diplom.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Вы должны указать имя пользователя")
    @Size(min = 2, max = 30, message = "Имя пользователя должно быть от 2 до 30 символов длиной")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "Вы должны указать пароль")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person() {
    }

    public Person(int id, String username, String password, String role) {
        this.role = role;
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public boolean isAdmin(){
        return role.equals("ROLE_ADMIN");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
