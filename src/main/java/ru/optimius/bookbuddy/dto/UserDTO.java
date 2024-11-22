package ru.optimius.bookbuddy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class UserDTO {

  @JsonProperty("firstName")
  String firstName;

  @JsonProperty("secondName")
  String secondName;

  @JsonProperty("books")
  List<BookDTO> books;

  @JsonProperty("email")
  String email;


}
