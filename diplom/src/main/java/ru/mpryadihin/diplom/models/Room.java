package ru.mpryadihin.diplom.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "room")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "id_type_room")
    @NotNull(message = "Вы должны выбрать тип номера")
    private int idTypeRoom;
    @Column(name = "price")
    private int price;
    @Column(name = "id_personal")
    @NotNull(message = "Вы должны выбрать обслуживающий персонал")
    int idPersonal;
    @Column(name = "seats")
    @NotNull(message = "Вы должны указать количество мест в номере")
    @Min(value = 1, message = "Количество мест не может быть меньше 1")
    private int seats;


    public int getSeats() {
        return seats;
    }


    public void setSeats(int seats) {
        this.seats = seats;
    }

    @MapsId("id_type_room")
    @ManyToOne
    @JoinColumn(name = "id_type_room" , referencedColumnName = "id")
    private TypeRoom type;
    @MapsId("id_personal")
    @ManyToOne
    @JoinColumn(name = "id_personal", referencedColumnName = "id")
    private Personal personal;

    public TypeRoom getType() {
        return type;
    }

    public void setType(TypeRoom type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "room")
    private List<Resides> resides;

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public List<Resides> getResides() {
        return resides;
    }

    public void setResides(List<Resides> resides) {
        this.resides = resides;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTypeRoom() {
        return idTypeRoom;
    }
    public void setIdTypeRoom(int idTypeRoom) {
        this.idTypeRoom = idTypeRoom;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getIdPersonal() {
        return idPersonal;
    }
    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

}
