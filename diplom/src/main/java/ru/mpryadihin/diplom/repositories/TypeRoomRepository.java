package ru.mpryadihin.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mpryadihin.diplom.models.TypeRoom;
@Repository
public interface TypeRoomRepository extends JpaRepository<TypeRoom, Integer> {
}
