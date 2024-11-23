package ru.optimius.bookbuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.optimius.bookbuddy.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
