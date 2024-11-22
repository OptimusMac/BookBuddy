package ru.optimius.bookbuddy.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO toUserDTO(UserEntity user);

  UserEntity toUser(UserDTO userDTO);

  BookDTO toBookDTO(BookEntity book);

  BookEntity toBook(BookDTO bookDTO);
}
