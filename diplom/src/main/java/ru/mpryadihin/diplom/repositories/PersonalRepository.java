package ru.mpryadihin.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mpryadihin.diplom.models.Personal;
@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer> {
}
