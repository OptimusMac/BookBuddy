package ru.optimius.bookbuddy.service;

import java.awt.print.Book;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.repositories.BookRepository;

@Service
@AllArgsConstructor
public class BookService extends DTOManager {


  private BookRepository bookRepository;

  @Transactional
  public BookDTO create(BookEntity bookEntity) {
    BookEntity createBook = bookRepository.save(bookEntity);
    return getBookDTO(createBook);
  }
  @Transactional(readOnly = true)
  public BookDTO findBook(Long id){
    BookEntity bookEntity = bookRepository.getReferenceById(id);
    return getBookDTO(bookEntity);
  }

  @Transactional
  public ResponseEntity<?> delete(Long id){
    Optional<BookEntity> bookEntity = bookRepository.findBookEntityById(id);
    if(bookEntity.isPresent()){
      bookRepository.delete(bookEntity.get());
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

}
