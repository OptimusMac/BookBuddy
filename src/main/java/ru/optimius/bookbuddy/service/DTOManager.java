package ru.optimius.bookbuddy.service;

import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.dto.UserMapper;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;

public abstract class DTOManager {

  private final UserMapper userMapper = UserMapper.INSTANCE;

  public UserDTO getUserDTO(UserEntity user) {
    return userMapper.toUserDTO(user);
  }

  public UserEntity getUser(UserDTO userDTO) {
    return userMapper.toUser(userDTO);
  }


  public BookDTO getBookDTO(BookEntity user) {
    return userMapper.toBookDTO(user);
  }

  public BookEntity getBook(BookDTO userDTO) {
    return userMapper.toBook(userDTO);
  }

}
