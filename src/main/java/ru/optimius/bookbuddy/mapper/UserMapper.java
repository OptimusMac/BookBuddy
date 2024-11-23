package ru.optimius.bookbuddy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import ru.optimius.bookbuddy.dto.BookDTO;
import ru.optimius.bookbuddy.dto.OrderDTO;
import ru.optimius.bookbuddy.dto.UserDTO;
import ru.optimius.bookbuddy.entities.BookEntity;
import ru.optimius.bookbuddy.entities.OrderEntity;
import ru.optimius.bookbuddy.entities.UserEntity;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserMapper {

  public abstract UserDTO toUserDTO(UserEntity user);

  public abstract BookDTO toBookDTO(BookEntity book);

  @Mapping(source = "bookEntity", target = ".")
  public abstract BookDTO toBookDTO(OrderEntity orderEntity);

  @Mapping(source = "bookEntity", target = "bookDTO")
  public abstract OrderDTO toOrderDTO(OrderEntity orderEntity);
}
