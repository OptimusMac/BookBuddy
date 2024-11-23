package ru.optimius.bookbuddy.dto;

import java.time.Instant;

public record OrderDTO(
    BookDTO bookDTO,
    Instant instant
) {}
