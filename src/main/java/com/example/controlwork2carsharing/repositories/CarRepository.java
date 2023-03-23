package com.example.controlwork2carsharing.repositories;

import com.example.controlwork2carsharing.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    //@Query("select c from Car c where c.id NOT IN (select o.car.id from Order o where o.endDate is null)")
    //@Query("select c from Car c where not (c.id = (select o.car.id from Order o where o.endDate is null))")
    @Query("SELECT c FROM Car c LEFT JOIN Order o ON c.id = o.car.id AND o.endDate IS NULL WHERE o.id IS NULL ")
    List<Car> findFreeCars();
}