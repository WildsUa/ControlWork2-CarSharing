package com.example.controlwork2carsharing.repositories;

import com.example.controlwork2carsharing.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}