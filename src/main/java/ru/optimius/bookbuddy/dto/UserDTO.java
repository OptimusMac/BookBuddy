package ru.optimius.bookbuddy.dto;

import java.util.List;


public record UserDTO(
    int id,
    String firstName,
    String secondName,
    List<BookDTO> orderBooks,
    String email
) {

}