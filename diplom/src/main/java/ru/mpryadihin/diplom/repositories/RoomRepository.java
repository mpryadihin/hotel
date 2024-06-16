package ru.mpryadihin.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mpryadihin.diplom.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
