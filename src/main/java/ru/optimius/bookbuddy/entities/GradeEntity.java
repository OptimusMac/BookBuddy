package ru.optimius.bookbuddy.entities;

import jakarta.persistence.Entity;
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


  UserEntity user;
  BookEntity bookEntity;
  float grade;
}
