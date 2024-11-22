package ru.optimius.bookbuddy.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface ObjectMapper {

  ObjectMapper INSTANCE = Mappers.getMapper(ObjectMapper.class);

  UserDTO toUserDTO(UserEntity user);

  BookDTO toBookDTO(BookEntity book);

}