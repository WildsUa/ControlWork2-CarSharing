package com.example.controlwork2carsharing.controllers;

import com.example.controlwork2carsharing.calculator.TimeCalculator;
import com.example.controlwork2carsharing.entities.Car;
import com.example.controlwork2carsharing.entities.Client;
import com.example.controlwork2carsharing.entities.Order;
import com.example.controlwork2carsharing.services.CarService;
import com.example.controlwork2carsharing.services.ClientService;
import com.example.controlwork2carsharing.services.OrderService;
import com.example.controlwork2carsharing.validators.EntityValidator;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final CarService carService;
    private final ClientService clientService;
    @Autowired
    public OrderController(OrderService orderService, CarService carService, ClientService clientService) {
        this.orderService = orderService;
        this.carService = carService;
        this.clientService = clientService;
    }

    @GetMapping("/orders")
    public String getOrders (Model model){
        List<Order> orders =orderService.findAllOrders();

        List<Car> availableCars = carService.findFreeCars();
        List<Client> availableClients = clientService.findFreeClients();

        model.addAttribute("orders", orders);
        model.addAttribute("availableCars", availableCars);
        model.addAttribute("availableClients", availableClients);
        return "orders";
    }

    @GetMapping("/order/{id}")
    public String orderPage(@PathVariable("id") int id, Model model) {
        boolean notCompleted;

        Optional<Order> order = orderService.findOrderByID(id);
        if (order.isEmpty()) {
            return "redirect:/orders";
        }

        notCompleted = (order.get().getEndDate() != null) && (order.get().getCost() != null);

        model.addAttribute("order", order.get());
        model.addAttribute("notCompleted", notCompleted);

        return "order";
    }

    @PostMapping("/add_order")
    public String addOrder(@ModelAttribute( value = "orders" ) Order order
            , RedirectAttributes redirectAttributes, @RequestParam(name="action", required=false) String action){
        double price;

        Message message;

        if("calculate_cost".equals(action)){
            if (new EntityValidator().validateOrdersCalc(order)){
                price = new TimeCalculator().timeCost(order.getStartDate(),
                        order.getEndDate(),order.getCar().getCategory().getRentalRatePerHour());
                message = new Message("Recommended price is " + price,"alert alert-success");
            } else {
                message = new Message ("Enter valid Start and End dates to use calculator", "alert alert-warning");
            }
        } else {
            message = orderService.saveOrder(order);
        }

        redirectAttributes.addFlashAttribute("message", message);
        return"redirect:/orders";
    }

    @PostMapping("/update_order")
    public String updateOrder(@ModelAttribute ( value = "orders" ) Order order,
                              RedirectAttributes redirectAttributes, @RequestParam(name="action", required=false) String action){
        double price;
        String link;
        Message message;

        if("calculate_cost".equals(action)){
            if (new EntityValidator().validateOrdersCalc(order)){
                price = new TimeCalculator().timeCost(order.getStartDate(), order.getEndDate(),order.getCar().getCategory().getRentalRatePerHour());
                message = new Message("Recommended price is " + price,"alert alert-success");
            } else {
                message = new Message ("Enter valid Start and End dates to use calculator", "alert alert-warning");
            }
            link = "redirect:/order/"+order.getId();
        } else {
            message = orderService.saveOrder(order);

            if (message.getWebclass().equals("alert alert-success")) {
                link = "redirect:/orders";
            } else
                link = "redirect:/order/" + order.getId();
        }
        redirectAttributes.addFlashAttribute("message", message);
        return link;
    }

}
