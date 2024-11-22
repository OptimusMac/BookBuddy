package ru.optimius.bookbuddy.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.optimius.bookbuddy.entities.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

  Optional<BookEntity> findBookEntityById(Long id);
  List<BookEntity> findByName(String name);
  List<BookEntity> findByPrice(Integer price);
  List<BookEntity> findByRating(Float rating);
  List<BookEntity> findByGenresContaining(String genre);}
