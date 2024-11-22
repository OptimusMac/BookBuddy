package ru.optimius.bookbuddy.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
  String firstName;
  String secondName;
  List<BookDTO> books;
  String email;



}
