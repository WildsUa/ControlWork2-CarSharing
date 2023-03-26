package com.example.controlwork2carsharing.services;

import com.example.controlwork2carsharing.entities.Payment;
import com.example.controlwork2carsharing.repositories.PaymentRepository;
import com.example.controlwork2carsharing.validators.EntityValidator;
import com.example.controlwork2carsharing.webElements.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Optional<Payment> findPaymentByID(int id){
        return paymentRepository.findById(id);
    }

    public List<Payment> findAllPayments(){
        return paymentRepository.findAll();
    }

    public Message savePayment(Payment payment){
        Message result = new EntityValidator().validatePayment(payment);

        if (result.getWebclass().equals("alert alert-success")) {
            if (payment.getId() == null) {
                result = new Message("Payment was added successful", "alert alert-success");
                paymentRepository.save(payment);
            } else if (this.findPaymentByID(payment.getId()).isEmpty()) {
                result = new Message("No such payment in the list", "alert alert-warning");
            } else {
                result = new Message("Payment was updated successful", "alert alert-success");
                paymentRepository.save(payment);
            }
        }

        return result;
    }
}
