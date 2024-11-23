package ru.optimius.bookbuddy.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserEntity extends BaseEntity {

  String firstName;
  String secondName;

  @Column(unique = true)
  String email;
  String password;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  List<OrderEntity> orderBooks = new ArrayList<>();
}
