package ru.optimius.bookbuddy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.UserEntity;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract UserDTO toUserDTO(UserEntity user);

  public abstract BookDTO toBookDTO(BookEntity book);
}