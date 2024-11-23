package ru.optimius.bookbuddy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private BookEntity bookEntity;

  private Instant instant;

  public OrderEntity(BookEntity bookEntity, Integer time) {
    this.bookEntity = bookEntity;
    this.setBackInstant(time);
  }


  public OrderEntity() {

  }

  public void setBackInstant(int backInstant) {
    Instant now = Instant.now();
    this.instant = now.plus(backInstant, ChronoUnit.SECONDS);
  }

}
