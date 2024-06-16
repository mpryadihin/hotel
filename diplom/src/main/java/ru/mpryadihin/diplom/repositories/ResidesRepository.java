package ru.mpryadihin.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mpryadihin.diplom.models.Resides;

import java.util.List;

@Repository
public interface ResidesRepository extends JpaRepository<Resides, Integer> {
    @Query("select r from Resides r where r.idRoom=?1")
    List<Resides> findByIdRoom(int id);

}
