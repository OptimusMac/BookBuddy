package ru.optimius.bookbuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.optimius.bookbuddy.entities.GradeEntity;

public interface  GradeRepository extends JpaRepository<GradeEntity, Long> {

}
