package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Order;
import com.example.controlwork2carsharing.repositories.OrderRepository;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> findOrderByID(int id){
        return orderRepository.findById(id);
    }

    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }

    public Message saveOrder(Order order){
        Message result;
        if (order.getId() == null) {
            result = new Message("Order was added successful", "alert alert-success");
            orderRepository.save(order);
        } else if (this.findOrderByID(order.getId()).isEmpty()){
            result = new Message("No such order in the list", "alert alert-warning");
        } else {
            result = new Message("Order was updated successful", "alert alert-success");
            orderRepository.save(order);
        }
        return result;
    }
}
