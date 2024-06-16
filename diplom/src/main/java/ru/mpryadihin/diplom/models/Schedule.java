package ru.mpryadihin.diplom.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull()
    @Column(name = "id_personal")
    private int idPersonal;

    @NotNull()
    @Column(name = "year")
    private int year;

    @NotNull()
    @Min(value = 1)
    @Max(value = 12)
    @Column(name = "month")
    private int month;

    @Column(name = "day1")
    private int day1;
    @Column(name = "day2")
    private int day2;
    @Column(name = "day3")
    private int day3;
    @Column(name = "day4")
    private int day4;
    @Column(name = "day5")
    private int day5;
    @Column(name = "day6")
    private int day6;
    @Column(name = "day7")
    private int day7;
    @Column(name = "day8")
    private int day8;
    @Column(name = "day9")
    private int day9;
    @Column(name = "day10")
    private int day10;
    @Column(name = "day11")
    private int day11;
    @Column(name = "day12")
    private int day12;
    @Column(name = "day13")
    private int day13;
    @Column(name = "day14")
    private int day14;
    @Column(name = "day15")
    private int day15;
    @Column(name = "day16")
    private int day16;
    @Column(name = "day17")
    private int day17;
    @Column(name = "day18")
    private int day18;
    @Column(name = "day19")
    private int day19;
    @Column(name = "day20")
    private int day20;
    @Column(name = "day21")
    private int day21;
    @Column(name = "day22")
    private int day22;
    @Column(name = "day23")
    private int day23;
    @Column(name = "day24")
    private int day24;
    @Column(name = "day25")
    private int day25;
    @Column(name = "day26")
    private int day26;
    @Column(name = "day27")
    private int day27;
    @Column(name = "day28")
    private int day28;
    @Column(name = "day29")
    private int day29;
    @Column(name = "day30")
    private int day30;
    @Column(name = "day31")
    private int day31;

    @MapsId("id_personal")
    @ManyToOne
    @JoinColumn(name = "id_personal", referencedColumnName = "id", insertable = false, updatable = false)
    private Personal personal;


}
