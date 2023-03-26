package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Car;
import com.example.controlwork2carsharing.repositories.CarRepository;
import com.example.controlwork2carsharing.validators.EntityValidator;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> findCarByID(int id){
        return carRepository.findById(id);
    }

    public List<Car> findAllCars(){
        return carRepository.findAll();
    }
    public List<Car> findFreeCars(){
        return carRepository.findFreeCars();
    }

    public Message saveCar(Car car){
        Message result = new EntityValidator().validateCar(car);

        if (result.getWebclass().equals("alert alert-success")) {
            if (car.getId() == null) {
                result.setText("Car was added successful");
                carRepository.save(car);
            } else if (this.findCarByID(car.getId()).isEmpty()) {
                result = new Message("No such car in the list", "alert alert-warning");
            } else {
                result.setText("Car was updated successful");
                carRepository.save(car);
            }
        }
        return result;
    }

    public Message deleteCarByID (int id){
        Message result;

        if (this.findCarByID(id).isEmpty()){
            result = new Message("No such car in the list", "alert alert-warning");
        } else {
            result = new Message("Car was deleted successful", "alert alert-success");
            carRepository.deleteById(id);
        }
        return result;
    }
}
