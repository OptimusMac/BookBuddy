package ru.optimius.bookbuddy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "books")
public class BookEntity extends BaseEntity {

  String name;
  int price;
  @Column(length = 10000)
  String description;
  @ElementCollection
  List<String> genres = new ArrayList<>();
  String author;
  Instant backInstant;
  float rating;

  @OneToMany
  @JoinTable(
      name = "books_grade",
      joinColumns = @JoinColumn(name = "books_id"),
      inverseJoinColumns = @JoinColumn(name = "grade_id")
  )
  List<GradeEntity> grades = new ArrayList<>();


  public void setBackInstant(int backInstant) {
    Instant now = Instant.now();
    this.backInstant = now.plus(backInstant, ChronoUnit.DAYS);
  }

  public float getRating() {
    float scale = (float) Math.pow(10, 1);
    return Math.round(rating * scale) / scale;
  }
}
