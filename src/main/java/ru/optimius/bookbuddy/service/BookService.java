package ru.optimius.bookbuddy.service;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

import java.awt.print.Book;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.repositories.BookRepository;
import ru.optimius.bookbuddy.utils.searchs.Strategy;

@Service
@AllArgsConstructor
public class BookService extends MapManager{


  private BookRepository bookRepository;


  @Transactional
  public BookDTO create(BookEntity bookEntity) {
    BookEntity createBook = bookRepository.save(bookEntity);

    return toBookDTO(createBook);
  }

  @Transactional(readOnly = true)
  public BookDTO findBook(Long id) {
    BookEntity bookEntity = bookRepository.getReferenceById(id);
    return toBookDTO(bookEntity);
  }

  @Transactional
  public ResponseEntity<?> delete(Long id) {
    Optional<BookEntity> bookEntity = bookRepository.findBookEntityById(id);
    if (bookEntity.isPresent()) {
      bookRepository.delete(bookEntity.get());
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @Transactional(readOnly = true)
  public Collection<BookDTO> findAll() {
    return bookRepository.findAll()
        .stream()
        .map(this::toBookDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public Collection<BookDTO> search(String element, Strategy strategy, Integer limit){
    return findByStrategy(element, strategy, limit);
  }


  @Transactional
  public List<BookDTO> findByStrategy(String element, Strategy strategy, Integer limit) {
    switch (strategy) {
      case NAME:
        return bookRepository.findByName(element)
            .stream()
            .map(this::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      case PRICE:
        Integer price = parseInt(element);
        return bookRepository.findByPrice(price)
            .stream()
            .map(this::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      case RATING:
        Float rating = parseFloat(element);
        return bookRepository.findByRating(rating)
            .stream()
            .map(this::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      case GENRE:
        return bookRepository.findByGenresContaining(element)
            .stream()
            .map(this::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      default:
        return List.of();
    }
  }




}
