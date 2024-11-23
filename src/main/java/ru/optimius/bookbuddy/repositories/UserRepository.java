package ru.optimius.bookbuddy.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.optimius.bookbuddy.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findUserEntityById(Long id);
  Optional<UserEntity> findUserEntityByEmail(String email);

}
