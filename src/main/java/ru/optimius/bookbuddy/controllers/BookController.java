package ru.optimius.bookbuddy.controllers;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.service.BookService;
import ru.optimius.bookbuddy.service.GradeService;
import ru.optimius.bookbuddy.utils.searchs.Strategy;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

  private static final String GRADE_KEY_PREFIX = "book:grade:";
  private RedisTemplate<String, Float> redisTemplate;
  private BookService bookService;
  private GradeService gradeService;


  @PostMapping("/create")
  public BookDTO create(@RequestBody BookEntity bookEntity) {
    return bookService.create(bookEntity);
  }

  @GetMapping("/find")
  public BookDTO getById(@RequestParam(name = "id") Long bookID) {
    return bookService.findBook(bookID);
  }

  @GetMapping("/all")
  public Collection<BookDTO> findAll() {
    return bookService.findAll();
  }

  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  public Collection<BookDTO> search(
      @RequestParam(required = false) String genre,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String price,
      @RequestParam(required = false) String rating,
      @RequestParam(required = false, defaultValue = "0x7FFFFFFF") Integer limit) {

    if (genre == null && name == null && price == null && rating == null) {
      throw new IllegalArgumentException("all argument is empty!");
    }

    if (name != null) {
      return bookService.search(name, Strategy.NAME, limit);
    } else if (genre != null) {
      return bookService.search(genre, Strategy.GENRE, limit);
    } else if (price != null) {
      return bookService.search(price, Strategy.PRICE, limit);
    } else {
      return bookService.search(rating, Strategy.RATING, limit);
    }
  }


  @GetMapping("/rating")
  public float ratingBook(@RequestParam Long id) {
    Float value = getGrade(id);
    if(value == null){
      Optional<BookEntity> book = bookService.findById(id);
      value = book.map(bookEntity -> gradeService.average(bookEntity)).orElse(0.0F);
    }
    saveGrade(id, value);
    return value;
  }

  public void saveGrade(Long bookId, Float grade) {
    String key = GRADE_KEY_PREFIX + bookId;
    redisTemplate.opsForValue().set(key, grade, 1, TimeUnit.DAYS);
  }

  public Float getGrade(Long bookId) {
    String key = GRADE_KEY_PREFIX + bookId;
    return redisTemplate.opsForValue().get(key);
  }
}
