package com.example.controlwork2carsharing.repositories;

import com.example.controlwork2carsharing.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}