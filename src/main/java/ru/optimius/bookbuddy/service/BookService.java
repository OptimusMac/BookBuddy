package ru.optimius.bookbuddy.service;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.mapper.UserMapper;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.repositories.BookRepository;
import ru.optimius.bookbuddy.utils.searchs.Strategy;

@Service
@AllArgsConstructor
public class BookService {


  private BookRepository bookRepository;
  private UserMapper userMapper;


  @Transactional
  public BookDTO create(BookEntity bookEntity) {
    BookEntity createBook = bookRepository.save(bookEntity);

    return userMapper.toBookDTO(createBook);
  }
  @Transactional(readOnly = true)
  public Optional<BookEntity> findById(Long id){
    return bookRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public BookDTO findBook(Long id) {
    BookEntity bookEntity = bookRepository.getReferenceById(id);
    return userMapper.toBookDTO(bookEntity);
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
        .map(userMapper::toBookDTO)
        .toList();
  }

  @Transactional(readOnly = true)
  public Collection<BookDTO> search(String element, Strategy strategy, Integer limit) {
    return findByStrategy(element, strategy, limit);
  }


  @Transactional
  public List<BookDTO> findByStrategy(String element, Strategy strategy, Integer limit) {
    switch (strategy) {
      case NAME:
        return bookRepository.findByName(element)
            .stream()
            .map(userMapper::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      case PRICE:
        Integer price = parseInt(element);
        return bookRepository.findByPrice(price)
            .stream()
            .map(userMapper::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      case RATING:
        Float rating = parseFloat(element);
        return bookRepository.findByRating(rating)
            .stream()
            .map(userMapper::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      case GENRE:
        return bookRepository.findByGenresContaining(element)
            .stream()
            .map(userMapper::toBookDTO)
            .limit(limit)
            .collect(Collectors.toList());

      default:
        return List.of();
    }
  }


}
