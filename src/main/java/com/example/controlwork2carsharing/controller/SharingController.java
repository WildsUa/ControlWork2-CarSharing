package com.example.controlwork2carsharing.controller;

import com.example.controlwork2carsharing.calculator.TimeCalculator;
import com.example.controlwork2carsharing.entities.Car;
import com.example.controlwork2carsharing.entities.Category;
import com.example.controlwork2carsharing.entities.Client;
import com.example.controlwork2carsharing.entities.Order;
import com.example.controlwork2carsharing.repositories.*;
import com.example.controlwork2carsharing.validators.EntityValidator;
import com.example.controlwork2carsharing.webElements.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SharingController {
    CarRepository carRepository;
    ClientRepository clientRepository;
    OrderRepository orderRepository;
    CategoryRepository categoryRepository;
    PaymentRepository paymentRepository;

    //Main pages
    @GetMapping("/clients")
    public String getClients (Model model){
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/cars")
    public String getCars (Model model){
        List<Car> cars = carRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        Message message = (Message) model.getAttribute("message");
        if (message == null ) message = new Message("","true","");
        //Message message = new Message("", "true", "");

        model.addAttribute("cars", cars);
        model.addAttribute("categories", categories);
        model.addAttribute("message", message);
        return "cars";
    }

    @GetMapping("/categories")
    public String getCategories (Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/orders")
    public String getOrders (Model model){
        List<Order> orders = orderRepository.findAll();

        List<Car> availableCars = carRepository.findFreeCars();
        List<Client> availableClients = clientRepository.findFreeClients();

        //Message message = new Message("111111111","false", "alert alert-warning");

        model.addAttribute("orders", orders);
        model.addAttribute("availableCars", availableCars);
        model.addAttribute("availableClients", availableClients);
        //model.addAttribute("message", message);
        return "orders";
    }

    //Sub Lists pages
    @GetMapping("/client_orders/{id}")
    public String ordersByClients(@PathVariable("id") int id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client.get());
        model.addAttribute("orders", client.get().getOrders());
        return "orders_by_client";
    }
    @GetMapping("/client_payments/{id}")
    public String paymentByClients(@PathVariable("id") int id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client.get());
        model.addAttribute("payments", client.get().getPayments());
        return "payments_by_client";
    }

    //Entity pages
    @GetMapping("/client/{id}")
    public String clientPage(@PathVariable("id") int id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return "redirect:/clients";
        }
        model.addAttribute("client", client.get());
        return "client";
    }

    @GetMapping("/car/{id}")
    public String carPage(@PathVariable("id") int id, Model model) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isEmpty()) {
            return "redirect:/cars";
        }
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("car", car.get());
        model.addAttribute("categories", categories);
        return "car";
    }

    @GetMapping("/order/{id}")
    public String orderPage(@PathVariable("id") int id, Model model) {
        boolean notCompleted;

        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return "redirect:/orders";
        }
        List<Car> availableCars = carRepository.findAll();
        List<Client> availableClients = clientRepository.findAll();

        notCompleted = (order.get().getEndDate() != null) && (order.get().getCost() != null);

        model.addAttribute("availableCars",availableCars);
        model.addAttribute("availableClients",availableClients);
        model.addAttribute("order", order.get());
        model.addAttribute("notCompleted", notCompleted);

        return "order";
    }

    @GetMapping("/category/{id}")
    public String categoryPage(@PathVariable("id") int id, Model model) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category.get());
        return "category";
    }

    @GetMapping("/car_remove/{id}")
    public String deleteCar(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Message message;

        Optional<Car> car = carRepository.findById(id);
        if (car.isEmpty()) {
            message = new Message("No such car in the list", "false", "alert alert-warning");
        } else {
            message = new Message("Car was deleted successful", "false", "alert alert-success");
            carRepository.deleteById(id);
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/cars";
    }


    //Post functionality
    //new records
    @PostMapping("/add_client")
    public String addClient(@ModelAttribute ( value = "clients" ) Client client){

        clientRepository.save(client);
        return"redirect:/clients";
    }
    @PostMapping("/add_car")
    public String addCar(@ModelAttribute ( value = "cars" ) Car car, RedirectAttributes redirectAttributes){
        carRepository.save(car);
        Message message = new Message("Car was added successful", "false", "alert alert-success");
        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/cars";
    }
    @PostMapping("/add_category")
    public String addCategory(@ModelAttribute ( value = "categories" ) Category category){
        categoryRepository.save(category);
        return"redirect:/categories";
    }
    @PostMapping("/add_order")
    public String addCategory(@ModelAttribute ( value = "orders" ) Order order
            , RedirectAttributes redirectAttributes, @RequestParam(name="action", required=false) String action){
        double price;

        Message message;

        if("calculate_cost".equals(action)){
            if (new EntityValidator().validateOrdersCalc(order)){
                price = new TimeCalculator().timeCost(order.getStartDate(),
                        order.getEndDate(),order.getCar().getCategory().getRentalRatePerHour());
                message = new Message("Recommended price is " + price, "false","alert alert-success");
            } else {
                message = new Message ("Enter valid Start and End dates to use calculator","false", "alert alert-warning");
            }
        } else {
            message = new EntityValidator().validateOrder(order);

            if (message.getWebclass().equals("alert alert-success"))
                orderRepository.save(order);
        }

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/orders";
    }


    //update record
    @PostMapping("/update_client")
    public String updateClient(@ModelAttribute ( value = "client" ) Client client){
        clientRepository.save(client);
        return"redirect:/client/" + client.getId();
    }

    @PostMapping("/update_car")
    public String updateCar(@ModelAttribute ( value = "cars" ) Car car){
        carRepository.save(car);
        return"redirect:/cars";
    }

    @PostMapping("/update_category")
    public String updateCategory(@ModelAttribute ( value = "categories" ) Category category){
        categoryRepository.save(category);
        return"redirect:/categories";
    }

    @PostMapping("/update_order")
    public String updateCategory(@ModelAttribute ( value = "orders" ) Order order,
                RedirectAttributes redirectAttributes, @RequestParam(name="action", required=false) String action){
        double price;

        Message message;

        if("calculate_cost".equals(action)){
            if (new EntityValidator().validateOrdersCalc(order)){
                price = new TimeCalculator().timeCost(order.getStartDate(), order.getEndDate(),order.getCar().getCategory().getRentalRatePerHour());
                message = new Message("Recomended price is " + price, "false","alert alert-success");
            } else {
                message = new Message ("Enter valid Start and End dates to use calculator","false", "alert alert-warning");
            }

            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/order/"+order.getId();
        } else {
            message = new EntityValidator().validateOrderUpdate(order);

            if (message.getWebclass().equals("alert alert-success")) {
                orderRepository.save(order);
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/orders";
            } else {
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/order/" + order.getId();
            }
        }
    }

}
