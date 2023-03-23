package com.example.controlwork2carsharing.repositories;

import com.example.controlwork2carsharing.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}