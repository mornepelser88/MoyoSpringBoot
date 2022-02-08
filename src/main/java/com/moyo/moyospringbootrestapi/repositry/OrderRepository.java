package com.moyo.moyospringbootrestapi.repositry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moyo.moyospringbootrestapi.model.Order;



public interface OrderRepository extends JpaRepository<Order, Long> {
// List<Order> findByDescription(String orderDescription);
  List<Order> findByCustomerNameContaining(String customerName);
}
