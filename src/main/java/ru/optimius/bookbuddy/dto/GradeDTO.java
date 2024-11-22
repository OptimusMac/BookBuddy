package ru.optimius.bookbuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeDTO {

  long bookID;
  long userID;
  float grade;
}
