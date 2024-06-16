package ru.mpryadihin.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mpryadihin.diplom.models.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findAllByYearAndMonth(int year, int month);

    void deleteAllByYear(int year);

    List<Schedule> findAllByYear(int year);

}