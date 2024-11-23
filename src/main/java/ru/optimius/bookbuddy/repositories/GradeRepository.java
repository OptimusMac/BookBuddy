package ru.optimius.bookbuddy.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.optimius.bookbuddy.entities.GradeEntity;

public interface  GradeRepository extends JpaRepository<GradeEntity, Long> {
  Optional<GradeEntity> findByUserIdAndBookEntityId(Long userId, Long bookId);
  boolean existsByUserIdAndBookEntityId(Long userID, Long bookID);
}
