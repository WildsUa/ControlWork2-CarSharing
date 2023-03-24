package com.example.controlwork2carsharing.validators;

import com.example.controlwork2carsharing.entities.*;
import com.example.controlwork2carsharing.webElements.Message;

public class EntityValidator {
    public Message validateClient(Client client){
        Message message;
        message = new Message("Order was added successful", "alert alert-success");

        StringValidator stringValidator = new StringValidator();
        if (client.getFullName() == ""){
            message = new Message("Please enter Full name, that is a mandatory field", "alert alert-danger");
        } else if (client.getAddress() == "") {
            message = new Message("Please enter Address that is a mandatory field", "alert alert-danger");
        } else if (!stringValidator.validateEmail(client.getEmail())) {
            message = new Message("Email format is invalid, please update it", "alert alert-warning");
        } else if (!stringValidator.validatePhone(client.getPhoneNumber())) {
            message = new Message("Phone format is invalid please use (XXX) XXX-XX-XX", "alert alert-warning");
        }
        return message;
    }

    public Message validateCar(Car car){
        Message message;
        message = new Message("Car was added/updated successful", "alert alert-success");

        if (car.getBrand() == ""){
            message = new Message("Please enter Brand, that is a mandatory field", "alert alert-danger");
        } else if (car.getModel() == "") {
            message = new Message("Please enter Model that is a mandatory field", "alert alert-danger");
        } else if (car.getPlateNumber() == "") {
            message = new Message("Please enter Plate number that is a mandatory field", "alert alert-danger");
        }
        return message;
    }

    public Message validateCategory (Category category){
        Message message;
        message = new Message("Category was added/updated successful", "alert alert-success");

        if (category.getName() == ""){
            message = new Message("Please enter Category Name, that is a mandatory field", "alert alert-danger");
        } else if (category.getRentalRatePerHour() == null) {
            message = new Message("Please enter Renatal Rate that is a mandatory field", "alert alert-danger");
        }
        return message;
    }

    public Message validatePayment(Payment payment){
        Message message;
        message = new Message("Payment was added successful", "alert alert-success");

        if (payment.getDate() == null){
            message = new Message("Please enter Date, that is a mandatory field", "alert alert-danger");
        } else if (payment.getChequeId() == "") {
            message = new Message("Please enter Cheque Number that is a mandatory field", "alert alert-danger");
        } else if (payment.getPaymentSum() == null) {
            message = new Message("Please enter Payment Sum number that is a mandatory field", "alert alert-danger");
        }
        return message;
    }

    public Message validateOrder(Order order){
        Message message;
        message = new Message("Order was added successful", "alert alert-success");

        if (order.getStartDate() == null) {
            message = new Message("Please select start date, this is mandatory field", "alert alert-danger");
        } else if (order.getEndDate() != null) {
            if (order.getEndDate().isBefore(order.getStartDate()))
            message = new Message("End Date, can't be before start date", "alert alert-danger");
        } else if (order.getClient().getRating() < order.getCar().getCategory().getRequiredRating()) {
            message = new Message("Client rating is too low to select this car", "alert alert-warning");
        } else if (order.getClient().getDriverCategory().compareTo(order.getCar().getCategory().getRequiredCategory()) < 0) {
            message = new Message("Client Driver License didn't allow to use this car", "alert alert-warning");
        }

        return message;
    }

    public Message validateOrderUpdate(Order order){
        Message message;
        message = new Message("Order was added successful", "alert alert-success");

        if (order.getClient() == null) {
            message = new Message("This order is already completed, no ability too edit completed order", "alert alert-danger");
        } else if (order.getEndDate() != null) {
            if (order.getEndDate().isBefore(order.getStartDate()))
                message = new Message("End Date, can't be before start date", "alert alert-danger");
        }

        return message;
    }

    public boolean validateOrdersCalc(Order order){
        boolean result = (order.getStartDate() != null) && (order.getEndDate() != null);
         if(result) result = order.getEndDate().isAfter(order.getStartDate());
        return result;
    }
}
