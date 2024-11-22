package ru.optimius.bookbuddy.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;
import ru.optimius.bookbuddy.repositories.BookRepository;
import ru.optimius.bookbuddy.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService extends MapManager{

  private UserRepository userRepository;
  private BookRepository bookRepository;


  @Transactional
  public UserDTO create(UserEntity userEntity) {
    UserEntity createEntity = userRepository.save(userEntity);
    return toUserDTO(createEntity);
  }

  @Transactional(readOnly = true)
  public Collection<UserDTO> findAll() {
    return userRepository.findAll()
        .stream()
        .map(this::toUserDTO)
        .toList();
  }

  @Transactional
  public ResponseEntity<?> orderBook(Long userID, Long bookID) {
    UserEntity userEntity = userRepository.findUserEntityById(userID)
        .orElseThrow(() -> new EntityNotFoundException("User  not found"));

    BookEntity bookEntity = bookRepository.findBookEntityById(bookID)
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));

    userEntity.getOrderBooks().add(bookEntity);
    userRepository.save(userEntity);

    return ResponseEntity.ok().build();
  }

  @Transactional(readOnly = true)
  public UserDTO findUser(Long id) {
    Optional<UserEntity> findEntity = userRepository.findUserEntityById(id);
    return findEntity.map(this::toUserDTO).orElse(null);
  }

  @Transactional(readOnly = true)
  public UserDTO findUser(String email) {
    Optional<UserEntity> findEntity = userRepository.findUserEntityByEmail(email);
    return findEntity.map(this::toUserDTO).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<BookDTO> getBookOrders(String email) {
    Optional<UserEntity> findEntity = userRepository.findUserEntityByEmail(email);
    return findEntity.map(userEntity -> userEntity.getOrderBooks()
            .stream()
            .map(this::toBookDTO)
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
