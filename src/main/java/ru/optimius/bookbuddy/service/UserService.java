package ru.optimius.bookbuddy.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.mapper.UserMapper;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;
import ru.optimius.bookbuddy.repositories.BookRepository;
import ru.optimius.bookbuddy.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private BookRepository bookRepository;
  private UserMapper userMapper;


  @Transactional
  public UserDTO create(UserEntity userEntity) {
    UserEntity createEntity = userRepository.save(userEntity);
    return userMapper.toUserDTO(createEntity);
  }

  @Transactional(readOnly = true)
  public Collection<UserDTO> findAll() {
    return userRepository.findAll()
        .stream()
        .map(userMapper::toUserDTO)
        .toList();
  }

  @Transactional
  public ResponseEntity<?> orderBook(Long userID, Long bookID) {
    UserEntity userEntity = userRepository.findUserEntityById(userID)
        .orElse(null);

    BookEntity bookEntity = bookRepository.findBookEntityById(bookID)
        .orElse(null);

    if(userEntity == null){
      return ResponseEntity.notFound().build();
    }else if(bookEntity == null){
      return ResponseEntity.notFound().build();
    }else if(userRepository.existsBookById(bookID)){
      return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    userEntity.getOrderBooks().add(bookEntity);
    userRepository.save(userEntity);

    return ResponseEntity.ok(Map.of("order_success", "ok"));
  }

  @Transactional(readOnly = true)
  public UserDTO findUser(Long id) {
    Optional<UserEntity> findEntity = userRepository.findUserEntityById(id);
    return findEntity.map(userMapper::toUserDTO).orElse(null);
  }

  @Transactional(readOnly = true)
  public UserDTO findUser(String email) {
    Optional<UserEntity> findEntity = userRepository.findUserEntityByEmail(email);
    return findEntity.map(userMapper::toUserDTO).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<BookDTO> getBookOrders(String email) {
    Optional<UserEntity> findEntity = userRepository.findUserEntityByEmail(email);
    return findEntity.map(userEntity -> userEntity.getOrderBooks()
            .stream()
            .map(userMapper::toBookDTO)
            .toList())
        .orElse(List.of());
  }

  @Transactional(readOnly = true)
  public ResponseEntity<?> deleteUser(String email) {
    Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(email);
    if (userEntity.isPresent()) {
      userRepository.delete(userEntity.get());
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

}
