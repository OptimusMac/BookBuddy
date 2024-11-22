package ru.optimius.bookbuddy.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.optimius.bookbuddy.entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

  Optional<BookEntity> findBookEntityById(Long id);
}
