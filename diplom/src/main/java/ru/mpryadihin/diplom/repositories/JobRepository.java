package ru.mpryadihin.diplom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mpryadihin.diplom.models.Job;


@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
}
