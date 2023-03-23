package com.example.controlwork2carsharing.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client customer;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "Cheque_ID", nullable = false, length = 50)
    private String chequeId;

    @Column(name = "Payment_Sum", nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentSum;

}