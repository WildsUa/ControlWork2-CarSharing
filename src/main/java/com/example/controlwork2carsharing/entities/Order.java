package com.example.controlwork2carsharing.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "Start_Date", nullable = false)
    private LocalDateTime startDate;
    //private Instant startDate; - created by JPA buddy, but was replaced to LocalDateTime, as more useful in current situation

    @Column(name = "End_Date")
    private LocalDateTime endDate;

    @Column(name = "cost", precision = 10, scale = 2)
    private Double cost;

}