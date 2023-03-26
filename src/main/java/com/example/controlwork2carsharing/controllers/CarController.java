package com.example.controlwork2carsharing.controllers;

import com.example.controlwork2carsharing.entities.Car;
import com.example.controlwork2carsharing.entities.Category;
import com.example.controlwork2carsharing.services.CarService;
import com.example.controlwork2carsharing.services.CategoryService;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CarController {
    private final CarService carService;
    private final CategoryService categoryService;
    @Autowired
    public CarController(CarService carService, CategoryService categoryService) {
        this.carService = carService;
        this.categoryService = categoryService;
    }

    @GetMapping("/cars")
    public String getCars (Model model){
        List<Car> cars = carService.findAllCars();
        List<Category> categories = categoryService.findAllCategories();

        model.addAttribute("cars", cars);
        model.addAttribute("categories", categories);
        return "cars";
    }

    @GetMapping("/car/{id}")
    public String carPage(@PathVariable("id") int id, Model model) {
        Optional<Car> car = carService.findCarByID(id);
        if (car.isEmpty()) {
            return "redirect:/cars";
        }

        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("car", car.get());
        model.addAttribute("categories", categories);
        return "car";
    }

    @GetMapping("/car_remove/{id}")
    public String deleteCar(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Message message = carService.deleteCarByID(id);

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/cars";
    }
    @PostMapping("/add_car")
    public String addCar(@ModelAttribute( value = "cars" ) Car car, RedirectAttributes redirectAttributes){
        Message message = carService.saveCar(car);

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/cars";
    }

    @PostMapping("/update_car")
    public String updateCar(@ModelAttribute ( value = "cars" ) Car car, RedirectAttributes redirectAttributes){
        Message message =carService.saveCar(car);
        String link;

        if (message.getWebclass().equals("alert alert-success")) {
            link ="redirect:/cars";
        } else
            link = "redirect:/car/" + car.getId();

        redirectAttributes.addFlashAttribute("message", message);
        return link;
    }
}
