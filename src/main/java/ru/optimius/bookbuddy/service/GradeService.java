package ru.optimius.bookbuddy.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.optimius.bookbuddy.dto.GradeDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.GradeEntity;
import ru.optimius.bookbuddy.entities.UserEntity;
import ru.optimius.bookbuddy.repositories.BookRepository;
import ru.optimius.bookbuddy.repositories.GradeRepository;
import ru.optimius.bookbuddy.repositories.UserRepository;

@Service
@AllArgsConstructor
public class GradeService {


  private GradeRepository gradeRepository;
  private BookRepository bookRepository;
  private UserRepository userRepository;

  @Transactional
  public ResponseEntity<?> create(@RequestBody GradeDTO grade) {
    long userID = grade.getUserID();
    long bookID = grade.getBookID();
    float value = grade.getGrade();

    if (gradeRepository.existsByUserIdAndBookEntityId(userID, bookID)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Оценка для данной книги уже существует.");
    }

    Optional<BookEntity> bookEntityOpt = bookRepository.findBookEntityById(bookID);
    Optional<UserEntity> userEntityOpt = userRepository.findUserEntityById(userID);

    if (bookEntityOpt.isEmpty() || userEntityOpt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    GradeEntity gradeEntity = new GradeEntity();
    gradeEntity.setGrade(value);
    gradeEntity.setUser (userEntityOpt.get());
    gradeEntity.setBookEntity(bookEntityOpt.get());
    gradeRepository.save(gradeEntity);

    float rating = average(bookEntityOpt.get());
    bookEntityOpt.get().setRating(rating);
    bookRepository.save(bookEntityOpt.get());

    return ResponseEntity.ok(grade);
  }

  @Transactional(readOnly = true)
  public float bookRating(Long id){
   Optional<BookEntity> book = bookRepository.findBookEntityById(id);
   return book.map(this::average).orElse(0.0F);
  }

  @Transactional
  public float average(BookEntity book){
    float sum = (float) gradeRepository.findAll()
        .stream()
        .filter(gradeEntity -> gradeEntity.getBookEntity().getId().equals(book.getId()))
        .mapToDouble(GradeEntity::getGrade).sum();

    return sum > 0 ? (sum / 5.0F) : 0.0F;
  }

  @Transactional(readOnly = true)
  public GradeDTO gradeUser(Long bookID,Long userID){
    Optional<GradeEntity> gradeEntity = gradeRepository.findByUserIdAndBookEntityId(userID, bookID);
    return gradeEntity.map(ge -> new GradeDTO(ge.getBookEntity().getId(), ge.getUser().getId(), ge.getGrade())).orElse(null);
  }



}
