package ru.optimius.bookbuddy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BookDTO {

  @JsonProperty("name")
  String name;

  @JsonProperty("price")
  int price;

  @JsonProperty("description")
  String description;

  @JsonProperty("genres")
  List<String> genres = new ArrayList<>();

  @JsonProperty("author")
  String author;

  @JsonProperty("backInstant")
  Instant backInstant;

  @JsonProperty("rating")
  float rating;
}
