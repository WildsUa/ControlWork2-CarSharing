package com.example.controlwork2carsharing.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Full_Name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "Address", nullable = false, length = 100)
    private String address;

    @Column(name = "Photo")
    private String photo;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Phone_Number", length = 20)
    private String phoneNumber;

    @Column(name = "Rating", nullable = false)
    private Integer rating;

    @Column(name = "Driver_Category", nullable = false, length = 2)
    private String driverCategory;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private Set<Payment> payments = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private Set<Order> orders = new LinkedHashSet<>();
}