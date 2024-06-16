package ru.mpryadihin.diplom.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "resides")
public class Resides implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "result_price")
    private int resultPrice;
    @Column(name = "id_client")
    @NotNull(message = "Вы должны выбрать клиента")
    private int idClient;
    @Column(name = "id_room")
    @NotNull(message = "Вы должны выбрать комнату")
    private int idRoom;
    @Column(name = "date_in")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @FutureOrPresent(message = "Некорректное значение")
    @Type(type = "org.hibernate.type.LocalDateType")
    private LocalDate dateIn;
    @Column(name = "date_out")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Future(message = "Некорректное значение")
    @Type(type = "org.hibernate.type.LocalDateType")
    private LocalDate dateOut;
    @MapsId("id_client")
    @ManyToOne
    @JoinColumn(name = "id_client" , referencedColumnName = "id")
    private Client client;

    @MapsId("id_room")
    @ManyToOne
    @JoinColumn(name = "id_room", referencedColumnName = "id")
    private Room room;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    public int getResultPrice() {
        return resultPrice;
    }
    public void setResultPrice(int resultPrice) {
        this.resultPrice = resultPrice;
    }
    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public int getIdRoom() {
        return idRoom;
    }
    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }
    public LocalDate getDateIn() {
        return dateIn;
    }
    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }
    public LocalDate getDateOut() {
        return dateOut;
    }
    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }
}
