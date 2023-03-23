package com.example.controlwork2carsharing.validators;

import com.example.controlwork2carsharing.entities.Client;
import com.example.controlwork2carsharing.entities.Order;
import com.example.controlwork2carsharing.webElements.Message;

public class EntityValidator {
    public int validateClient(Client client){
        int result = 0;
        StringValidator stringValidator = new StringValidator();
        if (!stringValidator.validateEmail(client.getEmail())) result+=1;
        if (!stringValidator.validatePhone(client.getPhoneNumber())) result+=10;
        return result;
    }

    public Message validateOrder(Order order){
        Message message;
        message = new Message("Order was added successful", "false", "alert alert-success");

        if (order.getStartDate() == null) {
            message = new Message("Please select start date, this is mandatory field","false", "alert alert-danger");
        } else if (order.getEndDate() != null) {
            if (order.getEndDate().isBefore(order.getStartDate()))
            message = new Message("End Date, can't be before start date","false", "alert alert-danger");
        } else if (order.getClient().getRating() < order.getCar().getCategory().getRequiredRating()) {
            message = new Message("Client rating is too low to select this car","false", "alert alert-warning");
        } else if (order.getClient().getDriverCategory().compareTo(order.getCar().getCategory().getRequiredCategory()) < 0) {
            message = new Message("Client Driver License didn't allow to use this car","false", "alert alert-warning");
        }

        return message;
    }

    public Message validateOrderUpdate(Order order){
        Message message;
        message = new Message("Order was added successful", "false", "alert alert-success");

        if (order.getClient() == null) {
            message = new Message("This order is already completed, no ability too edit completed order","false", "alert alert-danger");
        } else if (order.getEndDate() != null) {
            if (order.getEndDate().isBefore(order.getStartDate()))
                message = new Message("End Date, can't be before start date","false", "alert alert-danger");
        }

        return message;
    }

    public boolean validateOrdersCalc(Order order){
        boolean result = (order.getStartDate() != null) && (order.getEndDate() != null);
         if(result) result = order.getEndDate().isAfter(order.getStartDate());
        return result;
    }
}
