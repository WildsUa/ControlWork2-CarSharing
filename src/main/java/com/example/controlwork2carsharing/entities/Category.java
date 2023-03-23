package com.example.controlwork2carsharing.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "Required_Category", nullable = false, length = 2)
    private String requiredCategory;

    @Column(name = "Required_Rating", nullable = false)
    private Integer requiredRating;

    @Column(name = "Rental_Rate_Per_Hour", nullable = false, precision = 10, scale = 2)
    private Double rentalRatePerHour;

}