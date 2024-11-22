package ru.optimius.bookbuddy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "grade")
public class GradeEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  UserEntity user;

  @ManyToOne
  @JoinColumn(name = "book_id", nullable = false)
  BookEntity bookEntity;

  @Column(name = "grade", nullable = false)
  float grade;
}
