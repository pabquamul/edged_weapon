package com.examples.edged_weapon.repo;

import com.examples.edged_weapon.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
