package com.example.controlwork2carsharing.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "Model", nullable = false, length = 200)
    private String model;

    @Column(name = "Plate_Number", nullable = false, length = 10)
    private String plateNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Category_id", nullable = false)
    private Category category;

    public String getCarType (){
        return getBrand() + " " + getModel() + " " + getPlateNumber();
    }

}