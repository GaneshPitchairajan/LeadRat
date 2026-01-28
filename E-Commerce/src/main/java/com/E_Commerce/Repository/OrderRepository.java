package com.E_Commerce.Repository;

import com.E_Commerce.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserUsername(String username);
}
