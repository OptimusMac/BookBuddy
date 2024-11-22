package ru.optimius.bookbuddy.dto;

import java.time.Instant;
import java.util.List;


public record BookDTO(
    int id,
    String name,
    int price,
    String description,
    List<String> genres,
    String author,
    Instant backInstant,
    float rating
) { }
