package ru.optimius.bookbuddy.service;

import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.dto.ObjectMapper;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;

public abstract class MapManager {

  private final ObjectMapper objectMapper = ObjectMapper.INSTANCE;

  public UserDTO toUserDTO(UserEntity user){
    return objectMapper.toUserDTO(user);
  }

  public BookDTO toBookDTO(BookEntity book){
    return objectMapper.toBookDTO(book);
  }



}
